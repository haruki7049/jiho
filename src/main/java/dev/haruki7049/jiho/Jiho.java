package dev.haruki7049.jiho;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.ZonedDateTime;
import java.time.Duration;
import java.io.File;
import java.util.logging.Logger;
import java.time.Clock;

public class Jiho {
  Config config;

  public Jiho(File configFile) throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    Config c = objectMapper.readValue(configFile, Config.class);

    this.config = c;

    if (this.config.source == null)
      throw new AudioSourceIsNullException();
  }

  void run() throws Exception {
    Logger logger = Logger.getLogger("jiho");
    AudioManager audioManager = new AudioManager(this.config.source);

    while (true) {
      Thread.sleep(1000);

      // Get now time
      final ZonedDateTime now = ZonedDateTime.now();

      final ZonedDateTime nextHour = now
        .plusHours(1)
        .withMinute(0)
        .withSecond(0)
        .withNano(0);

      final Duration durationUntilNextHour = Duration.between(now, nextHour);
      final long millisecondsToWait = durationUntilNextHour.toMillis();

      logger.info("Current time: " + now + ". Waiting " + durationUntilNextHour.toSeconds() + " seconds until " + nextHour);

      // Thread sleeping
      try {
        Thread.sleep(millisecondsToWait);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        logger.warning("Thread sleeping interrupted: " + e.getMessage());
        return;
      }

      logger.info("It's the hour. Playing sound...");
      audioManager.play();
    }
  }
}
