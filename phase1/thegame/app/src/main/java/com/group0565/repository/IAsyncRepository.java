package com.group0565.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

/** An interface defining a repository */
public interface IAsyncRepository<T> {

  /**
   * Gets the observable LiveData of all the objects
   *
   * @return An observable object wrapping a list
   */
  LiveData<List<T>> getObservable();

  /**
   * Updates an object in the database for the user
   *
   * @param obj The object to update based on its key
   */
  void put(T obj);

  /**
   * Add a preference to the database for the user
   *
   * @param obj The object to add
   */
  void push(T obj);

  /**
   * Remove a preference from the database for the user
   *
   * @param obj The object to remove
   */
  void delete(T obj);
}
