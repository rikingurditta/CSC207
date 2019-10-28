package com.group0565.engine.android.assets;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.util.Log;

import com.group0565.engine.assets.AudioAsset;

import java.io.IOException;

public class AndroidAudioAsset extends AudioAsset {
    private static final String TAG = "AndroidAudioAsset";
    public static final String AUDIO_FOLDER = "media/";
    private AssetManager assetManager;
    private MediaPlayer player;
    private AssetFileDescriptor fd;

    public AndroidAudioAsset(String name, String path, int volume, AssetManager assetManager) {
        super(name, path, volume);
        this.assetManager = assetManager;
    }

    @Override
    public void init() {
        super.init();
        try {
            fd = assetManager.openFd( AUDIO_FOLDER + "audio.mp3");
            player = new MediaPlayer();
            player.setDataSource(fd);
            player.prepare();
            player.setVolume(this.getVolume(), this.getVolume());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void play() {
        player.start();
    }

    @Override
    public void seekTo(int msec) {
        player.seekTo(msec);
    }

    @Override
    public void pause() {
        player.pause();
    }

    @Override
    public void stop() {
        player.stop();
    }

    @Override
    public void setVolume(int volume) {
        super.setVolume(volume);
        player.setVolume(volume, volume);
    }

    @Override
    public void close() {
        player.release();
        try {
            fd.close();
        } catch (IOException e) {
            Log.e(TAG, "Audio Asset " + getName() + " Failed to close", e);
        }
    }
}
