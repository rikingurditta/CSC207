package com.group0565.menuUI.achievements;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thegame.R;

/**
 * An extension of RecyclerView.Adapter for the StatisticsRecycler
 */
public class AchievementsRecyclerAdapter extends RecyclerView.Adapter<AchievementsRowViewHolder> {

    /**
     * A reference to the RowsPresenter
     */
    private final AchievementsMVP.AchievementsRowsPresenter achievementsRowsPresenter;

    /**
     * Instantiate a new StatisticsRecyclerAdapter
     *
     * @param achievementsRowsPresenter The RowsPresenter to use
     */
    AchievementsRecyclerAdapter(AchievementsMVP.AchievementsRowsPresenter achievementsRowsPresenter) {
        this.achievementsRowsPresenter = achievementsRowsPresenter;
    }

    /**
     * Called when RecyclerView needs a new {@link RecyclerView.ViewHolder} of the given type to
     * represent an item.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to an
     *                 adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public AchievementsRowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AchievementsRowViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.achievement_row_layout, parent, false));
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should update
     * the contents of the {@link RecyclerView.ViewHolder#itemView} to reflect the item at the given
     * position.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the item at
     *                 the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull AchievementsRowViewHolder holder, int position) {
        achievementsRowsPresenter.onBindRepositoryRowViewAtPosition(position, holder);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return achievementsRowsPresenter.getAchievementsCount();
  }
}
