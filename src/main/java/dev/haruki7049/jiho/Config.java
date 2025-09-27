package dev.haruki7049.jiho;

import java.nio.file.Path;
import java.nio.file.Paths;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.dirs.ProjectDirectories;

public class Config {
  private static final ProjectDirectories projDirs = ProjectDirectories.from("dev", "haruki7049", "jiho");

  Path logDir = Paths.get(projDirs.cacheDir);
}
