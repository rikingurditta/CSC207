package com.group0565.menuUI.achievements;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thegame.R;

/** The ViewHolder for an achievements row */
public class AchievementsRowViewHolder extends RecyclerView.ViewHolder
    implements AchievementsMVP.AchievementsRowView {

  /** The title textView */
  private TextView nameTextView;

  /** The value textView */
  private TextView descTextView;

  /**
   * Sets the textView references
   *
   * @param itemView The row view
   */
  AchievementsRowViewHolder(@NonNull View itemView) {
    super(itemView);

    // todo
    //    nameTextView = itemView.findViewById(R.id.achievement_name);
    //    descTextView = itemView.findViewById(R.id.achievement_desc);
    //    descTextView = itemView.findViewById(R.id.achievement_image);
  }

  /**
   * Sets the row description
   *
   * @param desc The new description
   */
  @Override
  public void setDesc(String desc) {
    nameTextView.setText(desc);
  }

  /**
   * Sets the row name
   *
   * @param name The new name
   */
  @Override
  public void setName(String name) {
    descTextView.setText(name);
  }

  /**
   * Sets the row image by the key
   *
   * @param key The achievement's key
   */
  @Override
  public void setImage(String key) {
    //        descTextView.setText(name);
  }
}
