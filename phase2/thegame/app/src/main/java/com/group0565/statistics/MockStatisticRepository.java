package com.group0565.statistics;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class MockStatisticRepository implements IAsyncStatisticsRepository {

    /**
     * A collection of the user preferences
     */
    private List<IStatistic> userStatistics;

    /**
     * An observable live collection of the user preferences
     */
    private MutableLiveData<List<IStatistic>> liveStatistics;

    MockStatisticRepository() {
        userStatistics = new ArrayList<>();
        liveStatistics = new MutableLiveData<>();
    }

    /**
     * Gets the observable LiveData of all the objects
     *
     * @return An observable object wrapping a list
     */
    @Override
    public LiveData<List<IStatistic>> getObservable() {
        return liveStatistics;
    }

    /**
     * Gets the non-observable list of all objects using a callback
     *
     * @param callback The callback to execute on success
     */
    @Override
    public void getAll(AsyncDataListCallBack<IStatistic> callback) {
        callback.onDataReceived(userStatistics);
    }

    /**
     * Updates an object in the database for the user
     *
     * @param obj The object to update based on its key
     */
    @Override
    public void put(IStatistic obj) {
        userStatistics.add(obj);

        liveStatistics.setValue(userStatistics);

        Log.d(
                "MockStatisticRepository", "put: " + obj.getStatKey() + " with value " + obj.getStatVal());
    }

    /**
     * Add a preference to the database for the user
     *
     * @param obj The object to add
     */
    @Override
    public void push(IStatistic obj) {
        userStatistics.add(obj);

        liveStatistics.setValue(userStatistics);

        Log.d(
                "MockStatisticRepository", "push: " + obj.getStatKey() + " with value " + obj.getStatVal());
    }

    /**
     * Remove a preference from the database for the user
     *
     * @param obj The object to remove
     */
    @Override
    public void delete(IStatistic obj) {
        userStatistics.add(obj);

        liveStatistics.setValue(userStatistics);

        Log.d(
                "MockStatisticRepository",
                "delete: " + obj.getStatKey() + " with value " + obj.getStatVal());
    }

    /**
     * Remove all child objects
     */
    @Override
    public void deleteAll() {
        userStatistics = new ArrayList<>();
        liveStatistics.setValue(userStatistics);
    }
}