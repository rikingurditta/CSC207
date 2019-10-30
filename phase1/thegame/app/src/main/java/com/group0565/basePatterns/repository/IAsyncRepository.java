package com.group0565.basePatterns.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * An interface defining a repository
 */
public interface IAsyncRepository<T> extends IRepository<T> {

    /**
     * Gets the observable LiveData of all the objects
     *
     * @return An observable object wrapping a list
     */
    LiveData<List<T>> getObservable();
}
