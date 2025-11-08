package dev.haruki7049.jiho.core;

import dev.haruki7049.jiho.core.config.Config;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.logging.Logger;

/**
 * Main application logic class for Jiho. Handles time calculation and triggers the audio player.
 * Note: This class is tightly coupled to Config loading and AudioManager. Consider using Dependency
 * Injection.
 */
public class Jiho {
  private final Config config;
  private final AudioPlayer audioPlayer;

  /**
   * Constructs a new Jiho instance by loading configuration from a file.
   *
   * @param config The configuration file.
   * @param audioPlayer The audio playback service.
   * @throws Exception if config loading fails or audio source is invalid.
   */
  public Jiho(Config config, AudioPlayer audioPlayer) throws Exception {
    this.config = config;
    this.audioPlayer = audioPlayer;

    if (!this.config.soundSource.exists() || this.config.soundSource == null) {
      throw new InvalidAudioSourceException();
    }
  }

  /**
   * Runs the main loop of the Jiho application. Waits until the next hour, then plays the sound.
   *
   * @throws Exception if an error occurs during execution.
   */
  public void run() throws Exception {
    Logger logger = Logger.getLogger("jiho");

    while (true) {
      Thread.sleep(1000); // 1000 milliseconds <- 1 second

      // Get now time
      final ZonedDateTime now = ZonedDateTime.now();

      final ZonedDateTime nextHour = now.plusHours(1).withMinute(0).withSecond(0).withNano(0);

      final Duration durationUntilNextHour = Duration.between(now, nextHour);
      final long millisecondsToWait = durationUntilNextHour.toMillis();

      logger.info(
          "Current time: "
              + now
              + ". Waiting "
              + durationUntilNextHour.toSeconds()
              + " seconds until "
              + nextHour);

      // Thread sleeping
      try {
        Thread.sleep(millisecondsToWait);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        logger.warning("Thread sleeping interrupted: " + e.getMessage());
        return;
      }

      logger.info("It's the hour. Playing sound...");
      int times = this.calculateTimes(nextHour);
      this.audioPlayer.play(times, this.config.duration);
    }
  }

  /**
   * Calculates the number of times to play the sound based on 12-hour clock.
   *
   * @param nextHour The ZonedDateTime of the next hour.
   * @return The number of times (1-12).
   */
  private int calculateTimes(ZonedDateTime nextHour) {
    // 24-hour clock
    int times = nextHour.getHour();

    // To 12-hour clock
    times = times % 12;

    if (times == 0) {
      times = 12;
    }

    return times;
  }
}
