package dev.haruki7049.jiho;

import com.moandjiezana.toml.Toml;

public class Jiho {
  Config config;

  Jiho(Toml config) {
    this.config = config.to(Config.class);
  }

  void run() {
    while (true) {}
  }
}
