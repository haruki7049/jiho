package dev.haruki7049.jiho.core.config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import dev.dirs.ProjectDirectories;
import java.io.File;
import java.nio.file.Path;
import java.time.Duration;

/**
 * Represents the application configuration, loaded from JSON. This class holds settings required by
 * the application, such as file paths and durations.
 */
public class Config {

  /** Project-specific directories utility, used for determining default paths. */
  private static final ProjectDirectories projDirs =
      ProjectDirectories.from("dev", "haruki7049", "jiho");

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
   * The duration for an operation (e.g., how long a sound plays or a task runs). Exposed to Gson
   * for serialization/deserialization.
   */
  @SerializedName("duration")
  @Expose
  private final Duration duration;

  /**
   * Constructs a new Config instance with specified settings.
   *
   * @param dataDir The directory path for storing application data.
   * @param soundSource The sound file to be played.
   * @param duration The duration for the relevant operation.
   */
  public Config(Path dataDir, File soundSource, Duration duration) {
    this.dataDir = dataDir;
    this.soundSource = soundSource;
    this.duration = duration;
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

  /**
   * Gets the configured duration.
   *
   * @return The {@link Duration} value.
   */
  public Duration getDuration() {
    return this.duration;
  }
}
