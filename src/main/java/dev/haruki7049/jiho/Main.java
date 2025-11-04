package dev.haruki7049.jiho;

import picocli.CommandLine;

/**
 * Main class which includes a entry point.
 */
public class Main {

  /**
   * A main function.
   */
  public static void main(String[] args) {
    CommandLine cli = new CommandLine(new Cli());
    System.exit(cli.execute(args));
  }
}
