package preferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/** An implementation of IUserPreferencesRepository using Firebase */
class UserPreferencesFirebaseRepository implements IUserPreferencesRepository {

  /** A reference to the Firebase database */
  private DatabaseReference mDatabase;

  // todo: make LiveData
  /** A list containing all preferences in runtime */
  private List<UserPreference> currentPreferences;

  /**
   * Create a new UserPreferencesFirebaseRepository for the given user and set up all database event
   * listeners
   *
   * @param currUser The required user name for the database
   */
  UserPreferencesFirebaseRepository(String currUser) {
    this.mDatabase =
        FirebaseDatabase.getInstance().getReference().child("/users/" + currUser + "/preferences");
    currentPreferences = new ArrayList<>();

    this.mDatabase.addChildEventListener(
        new ChildEventListener() {
          @Override
          public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            UserPreference preference = dataSnapshot.getValue(UserPreference.class);
            currentPreferences.add(preference);
          }

          @Override
          public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            UserPreference preference = dataSnapshot.getValue(UserPreference.class);

            for (UserPreference iterPref : currentPreferences) {
              if (iterPref.getPrefKey().equals(preference.getPrefKey())) {
                iterPref.setValue(preference.getPrefVal());
                break;
              }
            }
          }

          @Override
          public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            UserPreference preference = dataSnapshot.getValue(UserPreference.class);

            Iterator<UserPreference> iter = currentPreferences.iterator();
            while (iter.hasNext()) {
              if (iter.next().getPrefKey().equals(preference.getPrefKey())) {
                iter.remove();
                break;
              }
            }
          }

          @Override
          public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
  }

  /**
   * Creates a new record in the DB and puts the given value inside
   *
   * @param value The object's value
   */
  @Override
  public void add(UserPreference value) {
    DatabaseReference preferenceRef = mDatabase.push();
    value.setKey(preferenceRef.getKey());
    mDatabase.child(preferenceRef.getKey()).setValue(value);
  }

  /**
   * Updates an existing record with the given key by the given value
   *
   * @param key The existing data record ID
   * @param value The object's new value
   */
  @Override
  public void update(String key, UserPreference value) {
    DatabaseReference ref = mDatabase.child(key);

    ref.setValue(value);
  }

  /**
   * Deletes the data record with the given key
   *
   * @param key The data record key
   */
  @Override
  public void delete(String key) {
    DatabaseReference ref = mDatabase.child(key);

    ref.removeValue();
  }

  /**
   * Query the single item with given key
   *
   * @param key The data record key
   * @return The data record's value
   */
  @Override
  @Nullable
  public UserPreference get(String key) {
    for (UserPreference pref : currentPreferences) {
      if (pref.getPrefKey().equals(key)) {
        return pref;
      }
    }
    return null;
  }

  /**
   * Query all items of this type
   *
   * @return The list of all data records
   */
  @Override
  public List<UserPreference> getAll() {
    return currentPreferences;
  }
}
