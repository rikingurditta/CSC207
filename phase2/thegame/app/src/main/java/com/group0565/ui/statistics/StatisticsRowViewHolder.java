package com.group0565.ui.statistics;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thegame.R;

/** The ViewHolder for a statistics row */
public class StatisticsRowViewHolder extends RecyclerView.ViewHolder
    implements StatisticsMVP.StatisticsRowView {

  /** The title textView */
  private TextView titleTextView;

  /** The value textView */
  private TextView valueTextView;

  /** The date textView */
  private TextView dateTextView;

  /**
   * Sets the textView references
   *
   * @param itemView The row view
   */
  StatisticsRowViewHolder(@NonNull View itemView) {
    super(itemView);

    titleTextView = itemView.findViewById(R.id.stat_title);
    valueTextView = itemView.findViewById(R.id.stat_value);
    dateTextView = itemView.findViewById(R.id.stat_date);
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

  /**
   * Sets the row date
   *
   * @param statDate The new value
   */
  @Override
  public void setDate(String statDate) {
    dateTextView.setText(statDate);
  }
}
