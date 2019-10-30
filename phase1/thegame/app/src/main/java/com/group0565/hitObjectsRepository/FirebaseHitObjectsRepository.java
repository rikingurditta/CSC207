package com.group0565.hitObjectsRepository;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.group0565.tsu.gameObjects.HitObject;

import java.util.ArrayList;
import java.util.List;

public class FirebaseHitObjectsRepository implements IHitObjectsRepository {

    /**
     * A reference to the Firebase database
     */
    private DatabaseReference mDatabase;

    /**
     * A collection of the hit objects
     */
    private List<HitObject> userObjects;

    /**
     * Create a new repository for the given user
     *
     * @param currUser The current user
     */
    FirebaseHitObjectsRepository(String currUser) {

        this.mDatabase =
                FirebaseDatabase.getInstance().getReference().child("users/" + currUser + "/tsu/hitObjects");

        userObjects = new ArrayList<>();
    }

    /**
     * Gets the non-observable list of all objects using a callback
     *
     * @param callback The callback to execute on success
     */
    @Override
    public void getAll(AsyncDataListCallBack<HitObject> callback) {
        this.mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userObjects = new ArrayList<>();

                dataSnapshot.getChildren().forEach(child -> {
                    HitObject hitObject = child.getValue(HitObject.class);
                    userObjects.add(hitObject);
                });

                callback.onDataReceived(userObjects);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    /**
     * Updates an object in the database for the user
     *
     * @param obj The object to update based on its key
     */
    @Override
    public void put(HitObject obj) {
        throw new UnsupportedOperationException("This repository does not support updating of a single object");
    }

    /**
     * Add a preference to the database for the user
     *
     * @param obj The object to add
     */
    @Override
    public void push(HitObject obj) {
        mDatabase.push().setValue(obj);
    }

    /**
     * Remove a preference from the database for the user
     *
     * @param obj The object to remove
     */
    @Override
    public void delete(HitObject obj) {
        throw new UnsupportedOperationException("This repository does not support deleting of a single object");
    }

    /**
     * Remove all child objects
     */
    @Override
    public void deleteAll() {
        mDatabase.removeValue();
    }

    /**
     * Pushes a list of HitObjects to the repository
     * Also empties the current list of HitObjects
     *
     * @param hitObjects The list of HitObjects to push to the DB
     */
    @Override
    public void pushList(List<HitObject> hitObjects) {
        deleteAll();

        for (HitObject obj : hitObjects) {
            push(obj);
        }
    }
}
