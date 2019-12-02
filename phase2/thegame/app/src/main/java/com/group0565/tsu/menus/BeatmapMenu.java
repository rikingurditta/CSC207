package com.group0565.tsu.menus;

import com.group0565.engine.assets.GameAssetManager;
import com.group0565.engine.gameobjects.Button;
import com.group0565.engine.gameobjects.GameMenu;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.ObservationEvent;
import com.group0565.engine.render.BitmapDrawer;
import com.group0565.engine.render.LanguageText;
import com.group0565.engine.render.TextRenderer;
import com.group0565.engine.render.ThemedPaintCan;
import com.group0565.math.Vector;
import com.group0565.tsu.enums.ButtonBitmap;
import com.group0565.tsu.game.Beatmap;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.group0565.engine.enums.HorizontalEdge.HCenter;
import static com.group0565.engine.enums.HorizontalEdge.Left;
import static com.group0565.engine.enums.HorizontalEdge.Right;
import static com.group0565.engine.enums.VerticalEdge.Bottom;
import static com.group0565.engine.enums.VerticalEdge.Top;

public class BeatmapMenu extends GameMenu {
  // Event Constants
  public static final String PLAY_EVENT = "Play";
  public static final String STATS_EVENT = "Open Stats";
  public static final String BACK_EVENT = "Go Back";

  private static final String BEATMAP_SET = "Tsu-Beatmap";
  private static final String SET = "Tsu";
  private static final String THEME_FOLDER = "BeatmapMenu.";

  // Misc Constants
  private static final Vector MARGIN = new Vector(75);
  private static final Vector INTERNAL_MARGIN = new Vector(50);
  // Background Constants
  private static final String BackgroundName = "Background";
  // Beatmap Name Constants
  private static final String BeatmapName = "BeatmapName";
  private static final String BeatmapPaintName = THEME_FOLDER + "BeatMapName";
  // Artist Name Constants
  private static final String ArtistName = "ArtistName";
  private static final String ArtistPaintName = THEME_FOLDER + "Artist";
  private static final String ArtistLabelName = "Artist";
  // Creator Name Constants
  private static final String CreatorName = "CreatorName";
  private static final String CreatorPaintName = THEME_FOLDER + "Creator";
  private static final String CreatorLabelName = "Creator";
  // Difficulty Constants
  private static final String DifficultyName = "Difficulty";
  private static final String DifficultyPaintName = THEME_FOLDER + "Difficulty";
  private static final String DifficultyLabelName = "Difficulty";
  // Button Size
  private static final Vector ButtonSize = new Vector(100);
  // Prev Button Constants
  private static final String PrevName = "Prev";
  // Play Button Constants
  private static final String PlayName = "Play";
  // Next Button Constants
  private static final String NextName = "Next";
  // Stats Button Constants
  private static final String StatsName = "Stats";
  // Back Button Constants
  private static final String BackName = "Back";

  private List<Beatmap> beatmapList;
  private int selectedScroll = 0;

  public BeatmapMenu() {}

  @Override
  public void init() {
    super.init();
    beatmapList = new ArrayList<>();
    GameAssetManager manager = getEngine().getGameAssetManager();
    Set<String> beatmapNames = manager.getJsonFileNames(BEATMAP_SET);
    for (String beatmapName : beatmapNames) {
      beatmapList.add(new Beatmap(BEATMAP_SET, beatmapName, manager));
    }
    ButtonBitmap.init(getEngine().getGameAssetManager());
    setSelectedBeatmap(beatmapList.get(0));

    ThemedPaintCan namePaint =
        new ThemedPaintCan(SET, BeatmapPaintName)
            .init(getGlobalPreferences(), getEngine().getGameAssetManager());
    ThemedPaintCan artistPaint =
        new ThemedPaintCan(SET, ArtistPaintName)
            .init(getGlobalPreferences(), getEngine().getGameAssetManager());
    ThemedPaintCan creatorPaint =
        new ThemedPaintCan(SET, CreatorPaintName)
            .init(getGlobalPreferences(), getEngine().getGameAssetManager());
    ThemedPaintCan difficultyPaint =
        new ThemedPaintCan(SET, DifficultyPaintName)
            .init(getGlobalPreferences(), getEngine().getGameAssetManager());

    LanguageText artistLabel =
        new LanguageText(
            getGlobalPreferences(), getEngine().getGameAssetManager(), SET, ArtistLabelName);
    LanguageText creatorLabel =
        new LanguageText(
            getGlobalPreferences(), getEngine().getGameAssetManager(), SET, CreatorLabelName);
    LanguageText difficultyLabel =
        new LanguageText(
            getGlobalPreferences(), getEngine().getGameAssetManager(), SET, DifficultyLabelName);

    // @formatter:off
    this.build()
        .add(
            BackgroundName,
            new BitmapDrawer(
                    new Vector(),
                    () ->
                        (getSelectedBeatmap() == null
                            ? null
                            : getSelectedBeatmap().getBackground()),
                    true)
                .setZ(-1))
        .addAlignment(Left, THIS, Left)
        .addAlignment(Right, THIS, Right)
        .addAlignment(Top, THIS, Top)
        .addAlignment(Bottom, THIS, Bottom)
        .add(
            BackName,
            new Button(ButtonSize, ButtonBitmap.BackButton.getBitmap())
                .build()
                .registerObserver(this::observeBackButton)
                .close())
        .addAlignment(Left, THIS, Left, MARGIN.getX())
        .addAlignment(Top, THIS, Top, MARGIN.getY())
        .add(
            BeatmapName,
            new TextRenderer(
                () ->
                    getSelectedBeatmap() == null
                        ? "Beatmap not found"
                        : getSelectedBeatmap().getName(),
                namePaint))
        .addAlignment(Left, THIS, Left, MARGIN.getX())
        .addAlignment(Top, BackName, Bottom, MARGIN.getY())
        .add(
            ArtistName,
            new TextRenderer(
                () ->
                    artistLabel.getValue()
                        + ": "
                        + (getSelectedBeatmap() == null ? "" : getSelectedBeatmap().getArtist()),
                artistPaint))
        .addAlignment(Left, THIS, Left, MARGIN.getX())
        .addAlignment(Top, BeatmapName, Bottom, INTERNAL_MARGIN.getY())
        .add(
            CreatorName,
            new TextRenderer(
                () ->
                    creatorLabel.getValue()
                        + ": "
                        + (getSelectedBeatmap() == null ? "" : getSelectedBeatmap().getCreator()),
                creatorPaint))
        .addAlignment(Left, THIS, Left, MARGIN.getX())
        .addAlignment(Top, ArtistName, Bottom, INTERNAL_MARGIN.getY())
        .add(
            DifficultyName,
            new TextRenderer(
                () ->
                    difficultyLabel.getValue()
                        + ": "
                        + (getSelectedBeatmap() == null
                            ? ""
                            : String.valueOf(getSelectedBeatmap().getDifficulty())),
                difficultyPaint))
        .addAlignment(Left, THIS, Left, MARGIN.getX())
        .addAlignment(Top, CreatorName, Bottom, INTERNAL_MARGIN.getY())
        .add(
            PrevName,
            new Button(ButtonSize, ButtonBitmap.PrevButton.getBitmap(), -1)
                .build()
                .registerObserver(this::observeScrollButton)
                .close())
        .addAlignment(Left, THIS, Left, MARGIN.getX())
        .addAlignment(Bottom, THIS, Bottom, -MARGIN.getY())
        .add(
            NextName,
            new Button(ButtonSize, ButtonBitmap.NextButton.getBitmap(), 1)
                .build()
                .registerObserver(this::observeScrollButton)
                .close())
        .addAlignment(Right, THIS, Right, -MARGIN.getX())
        .addAlignment(Bottom, THIS, Bottom, -MARGIN.getY())
        .add(
            PlayName,
            new Button(ButtonSize, ButtonBitmap.PlayButton.getBitmap())
                .build()
                .registerObserver(this::observePlayButton)
                .close())
        .addAlignment(Right, THIS, HCenter, -ButtonSize.getX())
        .addAlignment(Bottom, THIS, Bottom, -MARGIN.getY())
        .add(
            StatsName,
            new Button(ButtonSize, ButtonBitmap.StatsButton.getBitmap())
                .build()
                .registerObserver(this::observeStatsButton)
                .close())
        .addAlignment(Left, THIS, HCenter, ButtonSize.getX())
        .addAlignment(Bottom, THIS, Bottom, -MARGIN.getY())
        .close();
    // @formatter:on
  }

  private void observeScrollButton(Observable observable, ObservationEvent<Integer> event) {
    if (event.isEvent(Button.EVENT_DOWN)) {
      selectedScroll += event.getPayload();
      selectedScroll = Math.max(0, Math.min(beatmapList.size() - 1, selectedScroll));
    }
  }

  private void observeStatsButton(Observable observable, ObservationEvent<Integer> event) {
    if (event.isEvent(Button.EVENT_DOWN)) {
      this.notifyObservers(new ObservationEvent<>(STATS_EVENT, getSelectedBeatmap()));
    }
  }

  private void observePlayButton(Observable observable, ObservationEvent<Integer> event) {
    if (event.isEvent(Button.EVENT_DOWN)) {
      this.notifyObservers(new ObservationEvent<>(PLAY_EVENT, getSelectedBeatmap()));
    }
  }

  private void observeBackButton(Observable observable, ObservationEvent<Integer> event) {
    if (event.isEvent(Button.EVENT_DOWN)) {
      this.notifyObservers(new ObservationEvent<>(BACK_EVENT));
    }
  }

  /**
   * Getter for selectedBeatmap
   *
   * @return selectedBeatmap
   */
  public Beatmap getSelectedBeatmap() {
    return beatmapList.get(selectedScroll);
  }

  /**
   * Setter for selectedBeatmap
   *
   * @param selectedBeatmap The new value for selectedBeatmap
   */
  public void setSelectedBeatmap(Beatmap selectedBeatmap) {
    this.selectedScroll = beatmapList.indexOf(selectedBeatmap);
  }
}
