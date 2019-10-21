package preferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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

  /** A list containing all preferences in runtime */
  private List<UserPreference> currentPreferences;

  /** A LiveData object wrapper for the currentPreferences */
  private MutableLiveData<List<UserPreference>> currentPreferencesLiveData;

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
    currentPreferencesLiveData = new MutableLiveData<>();

    this.mDatabase.addChildEventListener(
        new ChildEventListener() {
          @Override
          public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            preferenceAdded(dataSnapshot);
          }

          @Override
          public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            preferenceChanged(dataSnapshot);
          }

          @Override
          public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            preferenceRemoved(dataSnapshot);
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
    mDatabase.child(value.getPrefName()).setValue(value.getPrefVal());
    //    DatabaseReference preferenceRef = mDatabase.push();
    //    value.setKey(preferenceRef.getKey());
    //    mDatabase.child(preferenceRef.getKey()).setValue(value);
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

    ref.setValue(value.getPrefVal());
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
      if (pref.getPrefName().equals(key)) {
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

  /**
   * get the observable data of all user preferences
   *
   * @return The list of all data records wrapped in an observable LiveData
   */
  @Override
  public LiveData<List<UserPreference>> getObservable() {
    return currentPreferencesLiveData;
  }

  /**
   * Remove preference from list and update LiveData
   *
   * @param dataSnapshot The snapshot of the db change
   */
  private void preferenceRemoved(@NonNull DataSnapshot dataSnapshot) {
    String preferenceName = dataSnapshot.getKey();

    Iterator<UserPreference> iter = currentPreferences.iterator();
    while (iter.hasNext()) {
      if (iter.next().getPrefName().equals(preferenceName)) {
        iter.remove();
        break;
      }
    }

    currentPreferencesLiveData.setValue(currentPreferences);
  }

  /**
   * Change preference in list and update LiveData
   *
   * @param dataSnapshot The snapshot of the db change
   */
  private void preferenceChanged(@NonNull DataSnapshot dataSnapshot) {
    String preferenceName = dataSnapshot.getKey();
    String preferenceValue = dataSnapshot.getValue(String.class);

    for (UserPreference iterPref : currentPreferences) {
      if (iterPref.getPrefName().equals(preferenceName)) {
        iterPref.setValue(preferenceValue);
        break;
      }
    }

    currentPreferencesLiveData.setValue(currentPreferences);
  }

  /**
   * Add preference to list and update LiveData
   *
   * @param dataSnapshot The snapshot of the db change
   */
  private void preferenceAdded(@NonNull DataSnapshot dataSnapshot) {
    String name = dataSnapshot.getKey();
    String value = dataSnapshot.getValue(String.class);
    UserPreference preference = new UserPreference(name, value);
    currentPreferences.add(preference);
    currentPreferencesLiveData.setValue(currentPreferences);
  }
}
