package dev.haruki7049.jiho.core;

import dev.haruki7049.jiho.core.config.Config;
import java.io.IOException;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.logging.Logger;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Main application logic class for Jiho. Handles time calculation and triggers the audio player.
 * Note: This class is tightly coupled to Config loading and AudioManager. Consider using Dependency
 * Injection.
 */
public class Jiho {
  private final Config config;
  private final AudioPlayer audioPlayer;
  private final Duration playbackDuration; // Holds the resolved duration

  // Define a default buffer to add to the audio length
  // This ensures playback finishes before the sleep ends.
  private static final Duration DEFAULT_INTERVAL_BUFFER = Duration.ofMillis(500); // 0.5s buffer

  /**
   * Constructs a new Jiho instance by loading configuration from a file.
   *
   * @param config The configuration file.
   * @param audioPlayer The audio playback service.
   * @throws Exception if config loading fails or audio source is invalid.
   */
  public Jiho(Config config, AudioPlayer audioPlayer)
      throws UnsupportedAudioFileException, IOException {
    this.config = config;
    this.audioPlayer = audioPlayer;

    // Resolve the playback duration once during construction
    this.playbackDuration = this.resolvePlaybackDuration(config, audioPlayer);
  }

  /**
   * Determines the correct duration to use. Uses config value if present, otherwise falls back to
   * the audio file's length plus a buffer.
   */
  private Duration resolvePlaybackDuration(Config config, AudioPlayer player)
      throws UnsupportedAudioFileException, IOException {
    // 2. If null, fall back to the audio file's actual length
    if (player != null) {
      // Get the exact audio length
      Duration audioLength = player.getAudioDuration();
      // Add the buffer to prevent cutting off the sound
      return audioLength.plus(DEFAULT_INTERVAL_BUFFER);
    }

    // Fallback in case player is also null (e.g., during tests)
    // This should ideally not be hit in production
    return Duration.ofSeconds(1);
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

      final int times = this.calculateTimes(nextHour);

      logger.info(
          "Current time: "
              + now
              + ". Waiting "
              + durationUntilNextHour.toSeconds()
              + " seconds until "
              + nextHour);
      logger.fine("Next time it will plays the sound " + times + " times...");

      // Thread sleeping
      try {
        Thread.sleep(millisecondsToWait);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        logger.warning("Thread sleeping interrupted: " + e.getMessage());
        return;
      }

      logger.info("It's the hour. Playing sound...");
      // Use the resolved playbackDuration and config volume
      this.audioPlayer.play(times, this.playbackDuration, this.config.getVolume());
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
