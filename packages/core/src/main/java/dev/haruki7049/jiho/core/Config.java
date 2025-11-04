package dev.haruki7049.jiho.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import dev.dirs.ProjectDirectories;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

public class Config {
  private static final ProjectDirectories projDirs =
      ProjectDirectories.from("dev", "haruki7049", "jiho");

  public final Path dataDir;
  public final File soundSource;
  public final Duration duration;

  @JsonCreator
  public Config(
      @JsonProperty("dataDir") Path dataDir,
      @JsonProperty("soundSource") File soundSource,
      @JsonProperty("duration") Duration duration)
      throws RuntimeException {

    this.dataDir = this.getOrDefault(dataDir, Paths.get(projDirs.dataDir));
    this.soundSource = soundSource;
    this.duration = this.getOrDefault(duration, Duration.ofSeconds(1));
  }

  private <T> T getOrDefault(T value, T defaultValue) {
    return (value != null) ? value : defaultValue;
  }
}
