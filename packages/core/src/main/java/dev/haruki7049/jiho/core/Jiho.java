package dev.haruki7049.jiho.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.io.File;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.logging.Logger;

public class Jiho {
  Config config;

  public Jiho(File configFile) throws Exception {
    ObjectMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    Config c = objectMapper.readValue(configFile, Config.class);

    this.config = c;

    if (!this.config.soundSource.exists() || this.config.soundSource == null)
      throw new AudioSourceIsNullException();
  }

  public void run() throws Exception {
    Logger logger = Logger.getLogger("jiho");
    AudioManager audioManager = new AudioManager(this.config.soundSource);

    while (true) {
      Thread.sleep(1000);

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
      audioManager.play(times, this.config.duration);
    }
  }

  private int calculateTimes(ZonedDateTime nextHour) {
    // 24-hour clock
    int times = nextHour.getHour();

    // To 12-hour clock
    times = times % 12;
    if (times == 0) times = 12;

    return times;
  }
}
