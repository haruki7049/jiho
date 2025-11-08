package dev.haruki7049.jiho.core.config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import dev.dirs.ProjectDirectories;
import java.io.File;
import java.nio.file.Path;
import java.time.Duration;

/** Represents the application configuration, loaded from JSON. */
public class Config {
  private static final ProjectDirectories projDirs =
      ProjectDirectories.from("dev", "haruki7049", "jiho");

  @SerializedName("dataDir")
  @Expose
  public Path dataDir;

  @SerializedName("soundSource")
  @Expose
  public File soundSource;

  @SerializedName("duration")
  @Expose
  public Duration duration;
}
