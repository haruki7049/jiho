package dev.haruki7049.jiho;

import dev.dirs.ProjectDirectories;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;
import picocli.CommandLine;

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
    // Generate default configuration file
    if (this.configPath == null) {
      this.setConfigPath();
      this.loadInitialConfig();
    }

    Jiho jiho = new Jiho(this.configPath.toFile());
    jiho.run();

    return 0;
  }

  private void setConfigPath() {
    // Get project directories
    ProjectDirectories projDirs = ProjectDirectories.from("dev", "haruki7049", "jiho");
    this.configPath = Paths.get(projDirs.configDir + "/config.json");
  }

  private void loadInitialConfig() throws Exception {
    boolean is_exists = Files.exists(this.configPath);
    if (!is_exists) {
      // Creates config file
      Files.createDirectories(this.configPath.getParent());
      Files.createFile(this.configPath);

      // Write initial config
      FileWriter filewriter = new FileWriter(this.configPath.toFile());
      filewriter.write("{}");
      filewriter.close();
    }
  }
}
