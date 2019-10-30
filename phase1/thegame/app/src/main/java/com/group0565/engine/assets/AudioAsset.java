package com.group0565.engine.assets;

public abstract class AudioAsset extends Asset {
    private float volume;

    public AudioAsset(String name, String path, float volume) {
        super(name, path);
        if (volume < 0 || volume > 100)
            throw new IllegalAssetException("Illegal Volume " + volume + " For Audio " + name);
        this.volume = volume;
    }

    public abstract void play();
    public abstract void seekTo(int msec);
    public abstract void pause();
    public abstract void stop();

    public abstract long progress();

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    @Override
    public void close(){

    }
}


class IllegalAudioAssetException extends IllegalAssetException{
    IllegalAudioAssetException(String message) {
        super(message);
    }
}