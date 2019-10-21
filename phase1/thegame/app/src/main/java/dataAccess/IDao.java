package dataAccess;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import java.util.List;

/** Interface for a Database Access Object */
public interface IDao<T> {

  /**
   * Creates a new record in the DB and puts the given value inside
   *
   * @param value The object's value
   */
  void post(T value);

  /**
   * Updates an existing record with the given key by the given value
   *
   * @param key The existing data record ID
   * @param value The object's new value
   */
  void put(String key, T value);

  /**
   * Deletes the data record with the given key
   *
   * @param key The data record key
   */
  void delete(String key);

  /**
   * Query the single item with given key
   *
   * @param key The data record key
   * @return The data record's value
   */
  @Nullable
  T get(String key);

  /**
   * Query all items of this type
   *
   * @return The list of all data records
   */
  List<T> getAll();

  /**
   * Get the observable list of the items
   * @return LiveData wrapper of the objects
   */
  LiveData<List<T>> getObservableList();
}
