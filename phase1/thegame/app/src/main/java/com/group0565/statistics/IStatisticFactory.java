package com.group0565.statistics;

/**
 * A factory for IStatistic objects
 */
public class IStatisticFactory {
    /**
     * Create a new IStatistic
     *
     * @param statisticName  The name of the statistic
     * @param statisticValue The statistic value
     * @return A new IStatistic object
     */
    public static IStatistic createGameStatistic(String statisticName, Object statisticValue) {
        return new GameStatistic<>(statisticName, statisticValue);
    }
}
