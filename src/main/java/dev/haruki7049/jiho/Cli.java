package dev.haruki7049.jiho;

import dev.dirs.ProjectDirectories;
import dev.haruki7049.jiho.core.Jiho;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;
import picocli.CommandLine;

/**
 * CLI (Command Line Interface) handler class for Jiho. This class uses picocli to parse command
 * line arguments.
 */
@CommandLine.Command(name = "jiho", version = "0.1.0")
public class Cli implements Callable<Integer> {

  /** Option to display the help message. */
  @CommandLine.Option(
      names = {"-h", "--help"},
      description = "show this command's help",
      usageHelp = true)
  boolean showHelp;

  /** Option to display the version information. */
  @CommandLine.Option(
      names = {"-v", "--version"},
      description = "show this command's version",
      versionHelp = true)
  boolean showVersion;

  /** Option to specify a custom configuration file path. */
  @CommandLine.Option(
      names = {"--config-file"},
      description = "configuration file's PATH")
  Path configPath;

  /**
   * Main logic of the CLI command, executed by picocli.
   *
   * @return The exit code (0 for success).
   * @throws Exception if an error occurs during execution.
   */
  @Override
  public Integer call() throws Exception {
    // Generate default configuration file
    if (this.configPath == null) {
      this.setConfigPath();
      this.loadInitialConfig();
    }

    // Note: This is a tightly coupled instantiation.
    // This part should be modified to implement Dependency Injection.
    Jiho jiho = new Jiho(this.configPath.toFile());
    jiho.run();

    return 0;
  }

  /**
   * Sets the default configuration file path based on OS standards if no path is provided via
   * command line.
   */
  private void setConfigPath() {
    // Get project directories
    ProjectDirectories projDirs = ProjectDirectories.from("dev", "haruki7049", "jiho");
    this.configPath = Paths.get(projDirs.configDir + "/config.json");
  }

  /**
   * Creates an initial empty configuration file ("{}") if one does not already exist at the
   * determined config path.
   *
   * @throws Exception if file I/O operations fail.
   */
  private void loadInitialConfig() throws Exception {
    boolean isExists = Files.exists(this.configPath);
    if (!isExists) {
      // Creates config file
      Files.createDirectories(this.configPath.getParent());
      Files.createFile(this.configPath);

      // Write initial config using try-with-resources
      try (FileWriter filewriter = new FileWriter(this.configPath.toFile())) {
        filewriter.write("{}");
      }
    }
  }
}
