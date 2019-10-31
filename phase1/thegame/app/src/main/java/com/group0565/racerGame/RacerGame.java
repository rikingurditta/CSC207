package com.group0565.racerGame;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.group0565.engine.gameobjects.Button;
import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.gameobjects.GlobalPreferences;
import com.group0565.engine.gameobjects.InputEvent;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.Observer;
import com.group0565.math.Vector;
import com.group0565.statistics.IAsyncStatisticsRepository;
import com.group0565.statistics.IStatisticFactory;
import com.group0565.statistics.StatisticRepositoryInjector;
import com.group0565.theme.Themes;

import java.util.Date;

public class RacerGame extends GameObject implements Observer {

    private static final String TAG = "RacerGame";
    private long startTime;
    private long totalTime = 0;
    private long spawnTime = 0;
    private boolean live = true;
    private Button leftButton;
    private Button middleButton;
    private Button rightButton;
    private ObstacleManager obsManager;
    private Racer racer;
    private IAsyncStatisticsRepository myStatRepo;
    StatisticRepositoryInjector.RepositoryInjectionListener listener;

    RacerGame(Vector position) {
        super(position);
    }

    public void init(){
        startTime = System.currentTimeMillis();
        leftButton = new Button(new Vector(100, 1750), new Vector(150, 150), getEngine().getGameAssetManager().getTileSheet("RacerButton", "RacerButton").getTile(0, 0), getEngine().getGameAssetManager().getTileSheet("RacerButton", "RacerButton").getTile(0, 0));
        middleButton = new Button(new Vector(475, 1750), new Vector(150, 150), getEngine().getGameAssetManager().getTileSheet("RacerButton", "RacerButton").getTile(0, 0), getEngine().getGameAssetManager().getTileSheet("RacerButton", "RacerButton").getTile(0, 0));
        rightButton = new Button(new Vector(850, 1750), new Vector(150, 150), getEngine().getGameAssetManager().getTileSheet("RacerButton", "RacerButton").getTile(0, 0), getEngine().getGameAssetManager().getTileSheet("RacerButton", "RacerButton").getTile(0, 0));
        this.adopt(leftButton);
        this.adopt(middleButton);
        this.adopt(rightButton);
        leftButton.registerObserver(this);
        middleButton.registerObserver(this);
        rightButton.registerObserver(this);
        racer = new Racer(new Vector(500, 1000), 0);
        this.adopt(racer);
        obsManager = new ObstacleManager(this);
        this.adopt(obsManager);
        super.init();
        listener =
                repository -> {
                    myStatRepo = repository;
                };
        StatisticRepositoryInjector.inject(TAG, listener);
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        Paint time = new Paint();
        if (getGlobalPreferences().theme == Themes.LIGHT) {
            // Set background to white
            canvas.drawRGB(255, 255, 255);
            // Set text colour to black
            time.setARGB(255,0,0,0);
        } else {
            // Set background to black
            canvas.drawRGB(0, 0, 0);
            // Set text colour to white
            time.setARGB(255,255,255,255);
        }
        time.setTextSize(128);
        // Set the colour of the lines
        Paint colour = new Paint();
        colour.setARGB(255, 255, 0, 0);
        // Draw the red lines that separate the lanes
        canvas.drawRect(canvas.getWidth() / 3 - 15, 0, canvas.getWidth() / 3 + 15, 2500, colour);
        canvas.drawRect(2 * canvas.getWidth() / 3 - 15, 0, 2 * canvas.getWidth() / 3 + 15, 2500, colour);
        canvas.drawText(Long.toString(totalTime / 1000), 600, 200, time);
    }

    public void observe(Observable observable) {
        if (observable == leftButton) {
            racer.setAbsolutePosition(new Vector(100, 1750));
            racer.setLane(1);
        } else if (observable == middleButton) {
            racer.setAbsolutePosition(new Vector(475, 1750));
            racer.setLane(2);
        } else if (observable == rightButton) {
            racer.setAbsolutePosition(new Vector(850, 1750));
            racer.setLane(3);
        }
    }

    /**
     * Getter method that returns this game's Racer object
     * @return Racer object
     */

    Racer getRacer() {
        return racer;
    }

    void updateDB(long totalTime) {
        if (myStatRepo != null) {
            // You can always use put (also for new objects) because of the way that Firebase DB works
            myStatRepo.put(IStatisticFactory.createGameStatistic("TimeSurvived" + startTime, totalTime));
        }
    }

    /**
     * Getter method that returns totalTime attribute
     * @return totalTime
     */
    long getTotalTime() {
        return totalTime;
    }

    /**
     * Getter method that returns spawnTime attribute
     * @return spawnTime
     */
    long getSpawnTime() {
        return spawnTime;
    }

    void setLive() {
        live = !live;
    }

    void disableAll() {
        racer.setEnable(false);
        obsManager.setEnable(false);
    }

    @Override
    public void update(long ms) {
        if (live) {
        this.spawnTime += ms;
        this.totalTime += ms;
        if (this.spawnTime >= 2000) {
            obsManager.spawnObstacle();
            this.spawnTime = 0;
            if (myStatRepo != null) {
                // You can always use put (also for new objects) because of the way that Firebase DB works
                myStatRepo.put(IStatisticFactory.createGameStatistic("TimeSurvived" + startTime, totalTime));
            }
        }
        }
    }
}
