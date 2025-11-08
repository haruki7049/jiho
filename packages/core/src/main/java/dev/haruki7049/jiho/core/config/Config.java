package dev.haruki7049.jiho.core.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import dev.dirs.ProjectDirectories;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

/** Represents the application configuration, loaded from JSON. This class is immutable. */
public class Config {
  private static final ProjectDirectories projDirs =
      ProjectDirectories.from("dev", "haruki7049", "jiho");

  public final Path dataDir;
  public final File soundSource;
  public final Duration duration;

  /**
   * Constructor for Jackson deserialization. Uses default values if properties are missing from
   * JSON.
   *
   * @param dataDir Path to data directory (from JSON).
   * @param soundSource Path to the sound file (from JSON).
   * @param duration Duration for playback logic (from JSON).
   */
  @JsonCreator
  public Config(
      @JsonProperty("dataDir") Path dataDir,
      @JsonProperty("soundSource") File soundSource,
      @JsonProperty("duration") Duration duration) {

    this.dataDir = this.getOrDefault(dataDir, Paths.get(projDirs.dataDir));
    this.soundSource = soundSource;
    this.duration = this.getOrDefault(duration, Duration.ofSeconds(1));
  }

  /**
   * Returns the value if not null, otherwise returns the default value.
   *
   * @param <T> The type of the value.
   * @param value The value to check (can be null).
   * @param defaultValue The default value to return if value is null.
   * @return The value or the default value (not null).
   */
  private <T> T getOrDefault(T value, T defaultValue) {
    return (value != null) ? value : defaultValue;
  }
}
