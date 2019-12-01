package com.group0565.tsu.game;

import com.group0565.engine.assets.AudioAsset;
import com.group0565.engine.gameobjects.Button;
import com.group0565.engine.gameobjects.GameMenu;
import com.group0565.engine.interfaces.Bitmap;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.ObservationEvent;
import com.group0565.engine.render.BitmapDrawer;
import com.group0565.hitObjectsRepository.SessionHitObjects;
import com.group0565.math.Vector;
import com.group0565.tsu.core.TsuPreferences;
import com.group0565.tsu.enums.ButtonBitmap;
import com.group0565.tsu.enums.Grade;
import com.group0565.tsu.enums.Scores;
import com.group0565.tsu.menus.PauseMenu;
import com.group0565.tsu.menus.StatsMenu;
import com.group0565.tsu.render.HitScoreRenderer;
import com.group0565.tsu.render.NumberRenderer;
import com.group0565.tsu.util.ScoreCalculator;

import java.util.List;

import static com.group0565.engine.enums.HorizontalEdge.HCenter;
import static com.group0565.engine.enums.HorizontalEdge.Left;
import static com.group0565.engine.enums.HorizontalEdge.Right;
import static com.group0565.engine.enums.VerticalEdge.Bottom;
import static com.group0565.engine.enums.VerticalEdge.Top;
import static com.group0565.engine.enums.VerticalEdge.VCenter;
import static com.group0565.tsu.enums.Scores.*;

public class TsuEngine extends GameMenu {
    //Event Constants
    public static final String GAME_END = "Game End";
    public static final String TO_STATS = "To Stats";

    //Renderer Constants
    private static final String RendererName = "Renderer";
    private static final float PlayAreaWidth = 1200;
    //Judgement Constants
    private static final String JudgementName = "Judgement";
    public  static final float HIT_AREA = 250;
    //Score Constants
    private static final String ScoreName = "Score";
    private static final Vector ScoreSize = new Vector(80);
    private static final Vector ScoreMargin = new Vector(50);
    //HitScore Constants
    private static final String HitScoreName = "HitScore";
    private static final float HitScoreSize = 100;
    private static final Vector HitScoreMargin = new Vector(0, -250);
    //Score Constants
    private static final String ComboName = "Combo";
    private static final Vector ComboSize = new Vector(80);
    private static final Vector ComboMargin = new Vector(0, 20);
    //Background Constants
    private static final String BackgroundName = "BackgroundName";
    //Engine Constants
    private static final long START_OFFSET = 128;
    //PauseMenu Constants
    private static final String PauseMenuName = "PauseMenu";
    private static final Vector PauseMenuSize = new Vector(550, 250);
    //PauseMenu Constants
    private static final String PauseButtonName = "PauseButton";
    private static final Vector PauseButtonSize = new Vector(100, 100);
    private static final Vector PauseButtonMargin = new Vector(100, 100);

    private TsuRenderer tsuRenderer;
    private Judgementer judgementer;
    private PauseMenu pauseMenu;

    private List<HitObject> hitObjects = null;
    private Beatmap beatmap = null;
    private AudioAsset audio = null;
    private Bitmap background = null;
    private InputIntercepter intercepter = new InputIntercepter();

    private boolean running = false;
    private boolean audioPlaying = false;
    private int passedPointer = 0;
    private long currentTime = 0;
    private long hitWindow = 1000;
    private int combo = 0;
    private int score = 0;
    private Scores lastHit = null;
    private InputGenerator generator = null;

    private String source = null;

    public TsuEngine() {
        super();
    }

    @Override
    public void init() {
        super.init();
        ButtonBitmap.init(getEngine().getGameAssetManager());

        this.build()
            .add(RendererName, (tsuRenderer = new TsuRenderer(new Vector(PlayAreaWidth, 0), this::getPassedPointer, this::getCurrentTime, this::getHitWindow, this::getHitObjects, this::getBeatmap)))
            .addAlignment(HCenter, THIS, HCenter)
            .addAlignment(Top, THIS, Top)
            .addAlignment(Bottom, THIS, Bottom)

            .add(JudgementName, (judgementer = new Judgementer(new Vector(PlayAreaWidth, HIT_AREA), this::getPassedPointer, this::getBeatmap, this::getHitObjects, this::getCurrentTime)).build()
                .setZ(1)
                .registerObserver(this::observeJudgement)
                .close())
            .addAlignment(HCenter, THIS, HCenter)
            .addAlignment(Bottom, THIS, Bottom)

            .add(ScoreName, new NumberRenderer(() -> score, ScoreSize).setZ(2))
            .addAlignment(Right, THIS, Right, -ScoreMargin.getX())
            .addAlignment(Top, THIS, Top, ScoreMargin.getY())

            .add(HitScoreName, new HitScoreRenderer(HitScoreSize, ()->lastHit).setZ(2))
            .addAlignment(HCenter, THIS, HCenter, HitScoreMargin.getX())
            .addAlignment(VCenter, THIS, VCenter, HitScoreMargin.getY())

            .add(ComboName, new NumberRenderer(() -> combo, ComboSize))
            .addAlignment(HCenter, THIS, HCenter, ComboMargin.getX())
            .addAlignment(Top, HitScoreName, Bottom, ComboMargin.getY())

            .add(BackgroundName, new BitmapDrawer(new Vector(), () -> background, true).setZ(-1))
            .addAlignment(Left, THIS, Left)
            .addAlignment(Right, THIS, Right)
            .addAlignment(Top, THIS, Top)
            .addAlignment(Bottom, THIS, Bottom)

            .add(PauseMenuName, (pauseMenu = new PauseMenu(PauseMenuSize)).build()
                .setZ(4)
                .registerObserver(this::observePauseMenu)
                .close())
            .addAlignment(HCenter, THIS, HCenter)
            .addAlignment(VCenter, THIS, VCenter)

            .add(PauseButtonName, new Button(PauseButtonSize, ButtonBitmap.PauseButton.getBitmap()).setZ(5).build()
                .registerObserver(this::observePauseButton)
                .close())
            .addAlignment(Left, THIS, Left, PauseButtonMargin.getX())
            .addAlignment(Top, THIS, Top, PauseButtonMargin.getY())
        .close();
        this.adopt(intercepter);
        intercepter.adopt(judgementer);
    }

    @Override
    public void update(long ms) {
        super.update(ms);
        if (running){
            //Increment our timer
            currentTime += ms;

            //If our timer and the progress on the audio differs too much, update our timer
            if (currentTime >= 0 && Math.abs(currentTime - audio.progress()) > 50)
                currentTime = Math.max(0, audio.progress());

            //When our lead in almost ends, start the audio, accounting for the delay with starting the audio
            if (currentTime + ms >= -START_OFFSET && !audioPlaying) {
                audioPlaying = true;
                audio.play();
            }

            //Update our pointer to the most recent active object
            while (passedPointer < hitObjects.size() && hitObjects.get(passedPointer).getMsEnd() < tsuRenderer.getScreenTime()) {
                //If we havent reached the end and the most recent hitObject has gone off screen
                HitObject object = hitObjects.get(passedPointer);
                //If this object never got hit
                if (object.getHitTime() < 0)
                    //We grade it a Miss
                    observeJudgement(this, new ObservationEvent<>(Judgementer.NOTE_HIT, object));
                //Increment our pointer to remove the object from our active section
                passedPointer++;
            }

            if (passedPointer >= hitObjects.size()){
                endGame();
            }
        }
    }

    private void observeJudgement(Observable observable, ObservationEvent<HitObject> event){
        if (event.isEvent(Judgementer.NOTE_HIT)) {
            lastHit = ScoreCalculator.computeScore(event.getPayload(), ScoreCalculator.calculateDistribution(beatmap.getDifficulty()), true);
            if (lastHit == S0)
                combo = 0;
            else{
                combo += 1;
                score += lastHit.getScore() * combo;
            }
        }
    }

    private void observePauseButton(Observable observable, ObservationEvent event){
        if (event.isEvent(Button.EVENT_DOWN)){
            pauseGame();
        }
    }

    private void observePauseMenu(Observable observable, ObservationEvent<String> event){
        if (event.isEvent(Button.EVENT_DOWN)){
            if (event.getPayload().equals(PauseMenu.RESUME))
                resumeGame();
            else if(event.getPayload().equals(PauseMenu.HOME))
                exitGame();
        }
    }


    public void startGame(){
        if (beatmap != null && hitObjects != null && audio != null){
            audio.seekTo(0);
            currentTime = -beatmap.getLeadin() - hitWindow;
            running = true;
            if (generator == null && getGlobalPreferences() instanceof TsuPreferences){
                TsuPreferences preferences = (TsuPreferences)getGlobalPreferences();
                if (preferences.getAuto()){
                    generator = new AutoGenerator(this, judgementer.getAbsolutePosition(), judgementer.getSize());
                    intercepter.setGenerator(generator);
                }
            }

            if (generator != null)
                generator.init();
            getEngine().getAchievementManager().unlockAchievement("Tsu", "Tsu_FirstGame");
        }
    }

    @Override
    public void pause() {
        super.pause();
        this.pauseGame();
    }

    public void pauseGame(){
        if (audioPlaying)
            audio.pause();
        running = false;
        audioPlaying = false;
        pauseMenu.setEnable(true);
    }

    public void resumeGame(){
        running = true;
        pauseMenu.setEnable(false);
    }

    private void endGame(){
        audio.pause();
        audioPlaying = false;
        generator = null;
        SessionHitObjects sessionHitObjects = ScoreCalculator.constructSessionHitObjects(beatmap, judgementer.getArchive());
        if (getGlobalPreferences() instanceof TsuPreferences)
            sessionHitObjects.setCheats(((TsuPreferences) getGlobalPreferences()).getAuto());
        checkAchivements(sessionHitObjects.getGrade());
        this.notifyObservers(new ObservationEvent<>(StatsMenu.TO_REPLAY.equals(source) ? TO_STATS : GAME_END, sessionHitObjects));
        this.source = null;
    }

    private void exitGame(){
        audio.pause();
        audioPlaying = false;
        generator = null;
        this.notifyObservers(new ObservationEvent(StatsMenu.TO_REPLAY.equals(source) ? TO_STATS : GAME_END));
        source = null;
    }

    /**
     * Resets the Tsu Engine
     */
    public void resetGame(){
        this.currentTime = 0;
        this.passedPointer = 0;
        this.score = 0;
        this.combo = 0;
        this.lastHit = null;
        this.running = false;
        pauseMenu.setEnable(false);
        judgementer.reset();
    }

    private void checkAchivements(int grade){
        Grade gradeEnum = Grade.num2Grade(grade);
        String gradeString = gradeEnum.getString();
        getEngine().getAchievementManager().unlockAchievement("Tsu", "Tsu_" + gradeString);
    }

    /**
     * Getter for passedPointer
     *
     * @return passedPointer
     */
    public Integer getPassedPointer() {
        return passedPointer;
    }

    /**
     * Getter for currentTime
     *
     * @return currentTime
     */
    public Long getCurrentTime() {
        return currentTime;
    }

    /**
     * Setter for currentTime
     *
     * @param currentTime The new value for currentTime
     */
    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    /**
     * Getter for hitWindow
     *
     * @return hitWindow
     */
    public Long getHitWindow() {
        return hitWindow;
    }

    /**
     * Setter for hitWindow
     *
     * @param hitWindow The new value for hitWindow
     */
    public void setHitWindow(long hitWindow) {
        this.hitWindow = hitWindow;
    }

    /**
     * Getter for hitObjects
     *
     * @return hitObjects
     */
    public List<HitObject> getHitObjects() {
        return hitObjects;
    }

    /**
     * Getter for beatmap
     *
     * @return beatmap
     */
    public Beatmap getBeatmap() {
        return beatmap;
    }

    /**
     * Setter for beatmap
     *
     * @param beatmap The new value for beatmap
     */
    public void setBeatmap(Beatmap beatmap) {
        this.beatmap = beatmap;
        this.beatmap.initBeatmap();
        this.hitObjects = beatmap.getHitObjects();
        this.audio = beatmap.getAudio();
        this.background = beatmap.getBackground();
    }

    /**
     * Getter for source.
     *
     * @return source
     */
    public String getSource() {
        return source;
    }

    /**
     * Setter for source.
     *
     * @param source The new value for source
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * Setter for generator.
     *
     * @param generator The new value for generator
     */
    public void setGenerator(InputGenerator generator) {
        this.generator = generator;
        this.intercepter.setGenerator(generator);
    }

    public void setReplayGenerator(List<ArchiveInputEvent> arhive) {
        this.generator = new ReplayGenerator(this, arhive, judgementer.getAbsolutePosition(), judgementer.getSize());
        this.intercepter.setGenerator(generator);
    }
}
