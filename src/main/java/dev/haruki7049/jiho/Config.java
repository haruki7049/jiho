package dev.haruki7049.jiho;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.dirs.ProjectDirectories;

public class Config {
  private static final ProjectDirectories projDirs = ProjectDirectories.from("dev", "haruki7049", "jiho");

  public Path dataDir = Paths.get(projDirs.dataDir);
  public File soundSource;
}
