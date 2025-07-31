package dev.haruki7049.jiho;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.concurrent.Callable;
import picocli.CommandLine;
import dev.dirs.ProjectDirectories;

public class App {
  public static void main(String[] args) {
    CommandLine cli = new CommandLine(new CLI());
    System.exit(cli.execute(args));
  }
}

@CommandLine.Command(name = "jiho", version = "0.1.0")
class CLI implements Callable<Integer> {
  @CommandLine.Option(
      names = {"-h", "--help"},
      description = "show this command's help",
      usageHelp = true)
  boolean showHelp;

  @CommandLine.Option(
      names = {"-v", "--version"},
      description = "show this command's version",
      versionHelp = true)
  boolean showVersion;

  @CommandLine.Option(
      names = {"--config-file"},
      description = "configuration file's PATH")
  Path configPath;

  @Override
  public Integer call() throws Exception {
    if (this.configPath == null) {
      // Get project directories
      ProjectDirectories projDirs = ProjectDirectories.from("dev", "haruki7049", "jiho");
      this.configPath = Paths.get(projDirs.configDir + "/config.toml");

      // Creates config file
      boolean is_exists = Files.exists(this.configPath);
      if (!is_exists) {
        Path p = Paths.get(projDirs.configDir); // Converting type
        Files.createDirectories(p);
        Files.createFile(this.configPath);
      }
    }

    System.out.println(this.configPath);

    return 0;
  }
}
