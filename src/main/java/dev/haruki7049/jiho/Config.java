package dev.haruki7049.jiho;

import dev.dirs.ProjectDirectories;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Config {
  private static final ProjectDirectories projDirs =
      ProjectDirectories.from("dev", "haruki7049", "jiho");

  public Path dataDir = Paths.get(projDirs.dataDir);
  public File soundSource;
}
