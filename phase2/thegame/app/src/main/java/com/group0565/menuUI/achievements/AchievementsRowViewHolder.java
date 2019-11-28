package com.group0565.menuUI.achievements;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thegame.R;

/** The ViewHolder for an achievements row */
public class AchievementsRowViewHolder extends RecyclerView.ViewHolder
    implements AchievementsMVP.AchievementsRowView {

  /** The title textView */
  private TextView nameTextView;

  /** The value textView */
  private TextView descTextView;

  /** The achievement image */
  private ImageView imageView;

  /**
   * Sets the textView references
   *
   * @param itemView The row view
   */
  AchievementsRowViewHolder(@NonNull View itemView) {
    super(itemView);

    nameTextView = itemView.findViewById(R.id.achievement_name);
    descTextView = itemView.findViewById(R.id.achievement_desc);
    imageView = itemView.findViewById(R.id.achievement_image);
  }

  /**
   * Sets the row description
   *
   * @param key The achievement's key
   */
  @Override
  public void setDesc(String key) {
    CharSequence text = getTextFromResources(key, "_name");

    nameTextView.setText(text);
  }

  /**
   * Sets the row name
   *
   * @param key The achievement's key
   */
  @Override
  public void setName(String key) {
    CharSequence text = getTextFromResources(key, "_desc");

    descTextView.setText(text);
  }

  /**
   * Gets the appropriate text from the Resources
   *
   * @param key The Achievement key
   * @param textType The text type
   * @return The string of the resource
   */
  private CharSequence getTextFromResources(String key, String textType) {
    Resources resources = itemView.getResources();

    int identifier = getResourceID(key, "string", textType);
    return resources.getText(identifier);
  }

  /**
   * Get the resource ID
   *
   * @param key The achievement key
   * @param type The resource type
   * @param suffix The suffix of resource name
   * @return The ID of the resource
   */
  private int getResourceID(String key, String type, String suffix) {
    Resources resources = itemView.getResources();

    String packageName = itemView.getContext().getPackageName();
    String nameId = key + suffix;
    return resources.getIdentifier(nameId, type, packageName);
  }

  /**
   * Sets the row image by the key
   *
   * @param key The achievement's key
   * @param achieved Did the user achieve it yet
   */
  @Override
  public void setImage(String key, boolean achieved) {
    int drawableId = getResourceID(key, "drawable", "_image");

    Drawable image = ResourcesCompat.getDrawable(itemView.getResources(), drawableId, null);

    imageView.setImageDrawable(image);

    if (achieved) {
      imageView.setImageAlpha(255);
    } else {
      imageView.setImageAlpha(50);
    }
  }
}
