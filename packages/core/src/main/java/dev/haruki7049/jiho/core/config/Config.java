package dev.haruki7049.jiho.core.config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.File;

/**
 * Represents the application configuration, loaded from JSON. This class holds settings required by
 * the application, such as file paths.
 */
public class Config {
  /**
   * The sound file (e.g., MP3, WAV) to be played. Exposed to Gson for
   * serialization/deserialization.
   */
  @SerializedName("soundSource")
  @Expose
  private final File soundSource;

  /** The playback volume (0.0 to 1.0). Exposed to Gson for serialization/deserialization. */
  @SerializedName("volume")
  @Expose
  private final Float volume; // Use Float (wrapper) to allow null

  /**
   * Constructs a new Config instance with specified settings.
   *
   * @param soundSource The sound file to be played.
   * @param volume The playback volume (0.0 to 1.0), or null if not specified.
   */
  public Config(File soundSource, Float volume) {
    this.soundSource = soundSource;
    this.volume = volume;
  }

  /**
   * Gets the sound source file.
   *
   * @return The {@link File} object representing the sound source.
   */
  public File getSoundSource() {
    return this.soundSource;
  }

  /**
   * Gets the playback volume.
   *
   * @return The volume (0.0 to 1.0), or null if not specified.
   */
  public Float getVolume() {
    return this.volume;
  }
}
