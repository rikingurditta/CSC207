package com.group0565.tsu;

import com.group0565.engine.android.GameActivity;
import com.group0565.engine.gameobjects.GlobalPreferences;
import com.group0565.hitObjectsRepository.HitObjectsRepositoryInjector;
import com.group0565.hitObjectsRepository.SessionHitObjects;
import com.group0565.preferences.IPreferenceInteractor;
import com.group0565.preferences.PreferencesInjector;
import com.group0565.theme.Themes;
import com.group0565.tsu.gameObjects.StatsMenu;
import com.group0565.tsu.gameObjects.TsuGame;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TsuActivity extends GameActivity {
    private BlockingQueue<StatsMenu> menus = new LinkedBlockingQueue<>(10);
    private BlockingQueue<SessionHitObjects> newObjs = new LinkedBlockingQueue<>(10);
    private boolean running = true;
    public TsuActivity() {
        super(new TsuGame());
        ((TsuGame) this.getGame()).setActivity(this);
        IPreferenceInteractor prefInter = PreferencesInjector.inject();
        this.getGame().setGlobalPreferences(
                new GlobalPreferences(Themes.valueOf(prefInter.getTheme()),
                        prefInter.getLanguage(),
                        prefInter.getVolume()/100D));
    }

    @Override
    protected void onStart() {
        super.onStart();
//        waitStats();
    }

    public void waitStats() {
        while (running) {
            synchronized (this) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    break;
                }
            }
            while (!menus.isEmpty()) {
                HitObjectsRepositoryInjector.inject(
                        repository -> repository.getAll(menus.poll()::setHistory)
                );
            }
            while (!newObjs.isEmpty()) {
                HitObjectsRepositoryInjector.inject(
                        repository -> repository.getAll(data -> {
                            data.add(newObjs.poll());
                            repository.pushList(data);
                        })
                );
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        running = false;
    }

    public void getStats(StatsMenu stats) {
//        menus.add(stats);
    }

    public void updateStats(SessionHitObjects newObject) {
//        newObjs.add(newObject);
    }
}
