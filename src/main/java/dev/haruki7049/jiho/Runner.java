package dev.haruki7049.jiho;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.haruki7049.jiho.core.AudioPlayer;
import dev.haruki7049.jiho.core.InvalidAudioSourceException;
import dev.haruki7049.jiho.core.Jiho;
import dev.haruki7049.jiho.core.config.Config;
import dev.haruki7049.jiho.core.config.DurationAdapter;
import dev.haruki7049.jiho.core.config.FileAdapter;
import dev.haruki7049.jiho.core.config.PathAdapter;
import dev.haruki7049.jiho.core.impl.AudioManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.logging.Logger;

/**
 * Handles the initialization and execution of the Jiho application. This class sets up the
 * necessary components based on a configuration file.
 */
public class Runner {

  // Logger and default resource path defined as constants
  private static final Logger logger = Logger.getLogger("jiho");
  private static final String DEFAULT_SOUND_RESOURCE = "/default_sound.wav";

  /**
   * Loads configuration, initializes services, and runs the main Jiho application logic.
   *
   * @param configPath The path to the configuration file (e.g., config.json).
   * @throws Exception if configuration loading fails, audio initialization fails, or an error
   *     occurs during runtime.
   */
  public static void run(Path configPath) throws Exception {
    // 1. Load configuration
    Config config = loadConfig(configPath);

    // 2. Initialize AudioPlayer (with fallback)
    AudioPlayer audioPlayer = createAudioPlayer(config);

    // 3. Create and run Jiho
    Jiho jiho = new Jiho(config, audioPlayer);
    jiho.run();
  }

  /**
   * Loads configuration from the specified JSON file.
   *
   * @param configPath The path to the configuration file.
   * @return The loaded Config object.
   * @throws IOException if an I/O error occurs reading the file.
   */
  private static Config loadConfig(Path configPath) throws IOException {
    // Get a BufferedReader
    BufferedReader reader = Files.newBufferedReader(configPath);

    // Create Gson by GsonBuilder, with a few TypeAdapter
    Gson gson =
        new GsonBuilder()
            .registerTypeAdapter(Path.class, new PathAdapter())
            .registerTypeAdapter(File.class, new FileAdapter())
            .registerTypeAdapter(Duration.class, new DurationAdapter())
            .setPrettyPrinting()
            .create();

    // Load config
    Config config = gson.fromJson(reader, Config.class);
    return config;
  }

  /**
   * Creates an AudioPlayer instance.
   *
   * <p>It attempts to use the user-defined sound source from the config first. If it's null or
   * doesn't exist, it falls back to the default sound resource bundled in the JAR.
   *
   * @param config The application configuration.
   * @return An initialized AudioPlayer.
   * @throws InvalidAudioSourceException if both user and default sources are unavailable.
   * @throws MalformedURLException if the user-defined file path is invalid.
   */
  private static AudioPlayer createAudioPlayer(Config config)
      throws InvalidAudioSourceException, MalformedURLException {

    File userSoundSource = config.getSoundSource();

    // 1. Attempt to use user-defined sound source
    if (userSoundSource != null && userSoundSource.exists()) {
      logger.info("Using user-defined sound source: " + userSoundSource.getPath());
      return new AudioManager(userSoundSource);
    }

    // 2. Log a warning if user source was specified but not found
    if (userSoundSource != null) {
      logger.warning(
          "User-specified soundSource not found: "
              + userSoundSource.getPath()
              + ". Falling back to default sound.");
    }

    // 3. Attempt to use default resource from JAR
    URL defaultSoundUrl = Main.class.getResource(DEFAULT_SOUND_RESOURCE);

    if (defaultSoundUrl != null) {
      logger.info("Using default sound source from JAR.");
      return new AudioManager(defaultSoundUrl);
    }

    // 4. Fatal error: No audio source is available
    throw new InvalidAudioSourceException(
        "User sound source not found or specified, "
            + "and default sound resource ("
            + DEFAULT_SOUND_RESOURCE
            + ") is missing from the JAR.");
  }
}
