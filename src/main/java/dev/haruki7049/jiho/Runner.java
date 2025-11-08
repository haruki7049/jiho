package dev.haruki7049.jiho;

import com.google.gson.Gson;
import dev.haruki7049.jiho.core.AudioPlayer;
import dev.haruki7049.jiho.core.Jiho;
import dev.haruki7049.jiho.core.config.Config;
import dev.haruki7049.jiho.core.impl.AudioManager;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Handles the initialization and execution of the Jiho application. This class sets up the
 * necessary components based on a configuration file.
 */
public class Runner {

  /**
   * Loads configuration, initializes services, and runs the main Jiho application logic.
   *
   * @param configPath The path to the configuration file (e.g., config.json).
   * @throws Exception if configuration loading fails, audio initialization fails, or an error
   *     occurs during runtime.
   */
  public static void run(Path configPath) throws Exception {
    // Get a BufferedReader
    BufferedReader reader = Files.newBufferedReader(configPath);

    // Load Config#config
    Gson gson = new Gson();
    Config config = gson.fromJson(reader, Config.class);

    // Create AudioPlayer
    AudioPlayer audioPlayer = new AudioManager(config.soundSource);

    // Create Jiho
    Jiho jiho = new Jiho(config, audioPlayer);
    jiho.run();
  }
}
