package dev.haruki7049.jiho;

import com.moandjiezana.toml.Toml;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class Jiho {
  Config config;

  Jiho(Toml toml) {
    this.config = toml.to(Config.class);
    System.out.println(this.config.logDir);
  }

  void run() {
    Logger logger = Logger.getLogger("jiho");
    String logPath = String.format("%s/log.txt", this.config.logDir);

    try {
      Handler handler = new FileHandler(logPath);
    } catch (IOException exception) {
      System.err.println("Cannot create logging handler: " + exception.getMessage());
      return;
    }

    while (true) {}
  }
}
