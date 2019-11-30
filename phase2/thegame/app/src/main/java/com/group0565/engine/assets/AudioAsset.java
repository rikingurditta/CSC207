package com.group0565.engine.assets;

/** An abstract for audio assets */
public abstract class AudioAsset extends Asset {
  /** The current volume for the asset */
  private float volume;

  /**
   * Create a new AudioAsset
   *
   * @param name The asset name
   * @param path The asset location
   * @param volume The starting volume
   */
  public AudioAsset(String name, String path, float volume) {
    super(name, path);
    if (volume < 0 || volume > 100)
      throw new IllegalAssetException("Illegal Volume " + volume + " For Audio " + name);
    this.volume = volume;
  }

  /** Start the audio playback */
  public abstract void play();

  /**
   * Move to millisecond in audio
   *
   * @param msec Milliseconds from audio start
   */
  public abstract void seekTo(int msec);

  /** Pause audio playback */
  public abstract void pause();

  /** Stop audio playback */
  public abstract void stop();

  /**
   * Get the current position in the playback
   *
   * @return current position
   */
  public abstract long progress();

  /**
   * Get the current playing volume
   *
   * @return The current volume
   */
  public float getVolume() {
    return volume;
  }

  /**
   * Set the volume
   *
   * @param volume The target volume
   */
  public void setVolume(float volume) {
    this.volume = volume;
  }

  @Override
  public void close() {}

  /** An exception in audio asset interactions */
  protected class IllegalAudioAssetException extends IllegalAssetException {
    /**
     * Create a new exception
     *
     * @param message The error message
     */
    public IllegalAudioAssetException(String message) {
      super(message);
    }
  }
}
