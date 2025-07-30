package dev.haruki7049.jiho;

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
      description = "show this help",
      usageHelp = true)
  boolean showHelp;

  @CommandLine.Option(
      names = {"-v", "--version"},
      description = "show this command's version",
      versionHelp = true)
  boolean showVersion;

  @Override
  public Integer call() throws Exception {
    return 0;
  }
}
