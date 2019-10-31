package com.menu.statistics;

import com.group0565.statistics.IStatistic;

import java.util.List;

/**
 * An implementation of the Statistics rows presenter
 */
public class StatisticsRowsPresenterImp implements StatisticsMVP.StatisticsRowsPresenter {

    /**
     * A reference to the statistics list
     */
    private List<IStatistic> statistics;

    public StatisticsRowsPresenterImp(List<IStatistic> statistics) {
        this.statistics = statistics;
    }

    /**
     * Sets the title and value of the given row
     *
     * @param position Binding position
     * @param rowView  The row to bind to
     */
    @Override
    public void onBindRepositoryRowViewAtPosition(
            int position, StatisticsMVP.StatisticsRowView rowView) {

        rowView.setValue(statistics.get(position).getStatVal().toString());
        rowView.setTitle(statistics.get(position).getStatKey());
    }

    /**
     * Get the amount of rows that should appear
     *
     * @return The row count
     */
    @Override
    public int getStatsCount() {
        return statistics.size();
    }
}
