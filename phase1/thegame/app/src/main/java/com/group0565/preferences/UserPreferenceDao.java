package com.group0565.preferences;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import java.util.List;

import com.group0565.dataAccess.IDao;

/** Implementation of IDao for UserPreference */
public class UserPreferenceDao implements IDao<UserPreference> {

  /** An instance of UserPreferencesRepository */
  private IUserPreferencesRepository repo;

  /**
   * Create a new UserPreferenceDao for the given user
   *
   * @param currUser The given user
   */
  public UserPreferenceDao(String currUser) {
    this.repo = new UserPreferencesFirebaseRepository(currUser);
  }

  /**
   * Creates a new record in the DB and puts the given value inside
   *
   * @param value The object's value
   */
  @Override
  public void post(UserPreference value) {
    repo.add(value);
  }

  /**
   * Updates an existing record with the given key by the given value
   *
   * @param key The existing data record ID
   * @param value The object's new value
   */
  @Override
  public void put(String key, UserPreference value) {
    repo.update(key, value);
  }

  /**
   * Updates an existing record with the preference's key by the given value
   *
   * @param value The object's new value
   */
  public void put(UserPreference value) {
    repo.update(value.getPrefName(), value);
  }

  /**
   * Deletes the data record with the given key
   *
   * @param key The data record key
   */
  @Override
  public void delete(String key) {
    repo.delete(key);
  }

  /**
   * Query the single item with given key
   *
   * @param key The data record key
   * @return The data record's value
   */
  @Nullable
  @Override
  public UserPreference get(String key) {
    return repo.get(key);
  }

  /**
   * Query all items of this type
   *
   * @return The list of all data records
   */
  @Override
  public List<UserPreference> getAll() {
    return repo.getAll();
  }

  /**
   * Get the observable list of the items
   *
   * @return LiveData wrapper of the objects
   */
  @Override
  public LiveData<List<UserPreference>> getObservableList() {
    return this.repo.getObservable();
  }
}
