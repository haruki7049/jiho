package dev.haruki7049.jiho.core.config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.File;
import java.nio.file.Path;

/**
 * Represents the application configuration, loaded from JSON. This class holds settings required by
 * the application, such as file paths.
 */
public class Config {
  /**
   * The directory path for storing application data. Exposed to Gson for
   * serialization/deserialization.
   */
  @SerializedName("dataDir")
  @Expose
  private final Path dataDir;

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
   * @param dataDir The directory path for storing application data.
   * @param soundSource The sound file to be played.
   */
  public Config(Path dataDir, File soundSource) {
    this.dataDir = dataDir;
    this.soundSource = soundSource;
  }

  /**
   * Gets the application data directory path.
   *
   * @return The {@link Path} to the data directory.
   */
  public Path getDataDir() {
    return this.dataDir;
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
