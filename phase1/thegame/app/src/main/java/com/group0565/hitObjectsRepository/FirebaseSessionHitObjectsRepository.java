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

public class FirebaseSessionHitObjectsRepository implements ISessionHitObjectsRepository {

  /** A reference to the Firebase database */
  private DatabaseReference mDatabase;

  /** A collection of the hit objects */
  private List<SessionHitObjects> userSessions;

  /**
   * Create a new repository for the given user
   *
   * @param currUser The current user
   */
  FirebaseSessionHitObjectsRepository(String currUser) {

    this.mDatabase =
        FirebaseDatabase.getInstance()
            .getReference()
            .child("users/" + currUser + "/gameData/Tsu/sessions");

    userSessions = new ArrayList<>();
  }

  /**
   * Gets the non-observable list of all objects using a callback
   *
   * @param callback The callback to execute on success
   */
  @Override
  public void getAll(AsyncDataListCallBack<SessionHitObjects> callback) {
    this.mDatabase.addListenerForSingleValueEvent(
        new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            userSessions = new ArrayList<>();

            dataSnapshot.getChildren().forEach(session -> loopThroughSession(session));

            callback.onDataReceived(userSessions);
          }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
  }

  /**
   * Loops through the snapshot of a session's HitObjects and adds them to the sessions list
   *
   * @param session The snapshot
   */
  private void loopThroughSession(DataSnapshot session) {
    SessionHitObjects sessionHitObjects = session.getValue(SessionHitObjects.class);

    userSessions.add(sessionHitObjects);
  }

  /**
   * Updates an object in the database for the user
   *
   * @param obj The object to update based on its key
   */
  @Override
  public void put(SessionHitObjects obj) {
    throw new UnsupportedOperationException(
        "This repository does not support updating of a single object");
  }

  /**
   * Add a preference to the database for the user
   *
   * @param obj The object to add
   */
  @Override
  public void push(SessionHitObjects obj) {
    DatabaseReference ref = mDatabase.push();
    ref.setValue(obj);
  }

  /**
   * Remove a preference from the database for the user
   *
   * @param obj The object to remove
   */
  @Override
  public void delete(SessionHitObjects obj) {
    throw new UnsupportedOperationException(
        "This repository does not support deleting of a single object");
  }

  /** Remove all child objects */
  @Override
  public void deleteAll() {
    mDatabase.removeValue();
  }

  /**
   * Pushes a list of HitObjects to the repository Also empties the current list of HitObjects
   *
   * @param sessionLists The list of HitObjects to push to the DB
   */
  @Override
  public void pushList(List<SessionHitObjects> sessionLists) {
    deleteAll();

    for (SessionHitObjects session : sessionLists) {
      DatabaseReference ref = mDatabase.push();
      ref.setValue(session);
    }
  }
}
