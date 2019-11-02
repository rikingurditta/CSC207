package com.group0565.basePatterns.repository;

import java.util.List;

public interface IRepository<T> {

    /**
     * Gets the non-observable list of all objects using a callback
     *
     * @param callback The callback to execute on success
     */
    void getAll(AsyncDataListCallBack<T> callback);

    /**
     * Updates an object in the database for the user
     *
     * @param obj The object to update based on its key
     */
    void put(T obj);

    /**
     * Add an object to the database for the user
     *
     * @param obj The object to add
     */
    void push(T obj);

    /**
     * Remove an object from the database for the user
     *
     * @param obj The object to remove
     */
    void delete(T obj);

    /**
     * Remove all child objects
     */
    void deleteAll();

    /**
     * A callback to receive information from single calls to DB
     *
     * @param <T> The type of the received object
     */
    interface AsyncDataListCallBack<T> {
        /**
         * Action to perform on data received
         *
         * @param data The received data
         */
        void onDataReceived(List<T> data);
    }
}
