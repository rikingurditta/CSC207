package com.group0565.menuUI.statistics;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thegame.R;

/**
 * The ViewHolder for a statistics row
 */
public class StatisticsRowViewHolder extends RecyclerView.ViewHolder
        implements StatisticsMVP.StatisticsRowView {

    /**
     * The title textView
     */
    private TextView titleTextView;

    /**
     * The value textView
     */
    private TextView valueTextView;

    /**
     * Sets the textView references
     *
     * @param itemView The row view
     */
    public StatisticsRowViewHolder(@NonNull View itemView) {
        super(itemView);

        titleTextView = itemView.findViewById(R.id.stat_title);
        valueTextView = itemView.findViewById(R.id.stat_value);
    }

    /**
     * Sets the row title
     *
     * @param title The new title
     */
    @Override
    public void setTitle(String title) {
        titleTextView.setText(title);
    }

    /**
     * Sets the row value
     *
     * @param value The new value
     */
    @Override
    public void setValue(String value) {
        valueTextView.setText(value);
    }
}
