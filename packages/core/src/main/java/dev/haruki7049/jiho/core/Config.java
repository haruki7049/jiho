package dev.haruki7049.jiho.core;

import dev.dirs.ProjectDirectories;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

public class Config {
  private static final ProjectDirectories projDirs =
      ProjectDirectories.from("dev", "haruki7049", "jiho");

  public Path dataDir = Paths.get(projDirs.dataDir);
  public File soundSource;
  public Duration duration = Duration.ofSeconds(1);
}
