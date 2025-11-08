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

  /**
   * Constructs a new Config instance with specified settings.
   *
   * @param soundSource The sound file to be played.
   */
  public Config(File soundSource) {
    this.soundSource = soundSource;
  }

  /**
   * Gets the sound source file.
   *
   * @return The {@link File} object representing the sound source.
   */
  public File getSoundSource() {
    return this.soundSource;
  }
}
