package dev.haruki7049.jiho;

import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class Jiho {
  Config config;

  public Jiho(File configFile) throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    Config c = objectMapper.readValue(configFile, Config.class);

    this.config = c;

    System.out.println(this.config.logDir);

    this.run();
  }

  void run() {
    Logger logger = Logger.getLogger("jiho");

    while (true) {}
  }
}
