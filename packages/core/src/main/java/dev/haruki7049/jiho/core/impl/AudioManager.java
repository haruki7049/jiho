package dev.haruki7049.jiho.core.impl;

import dev.haruki7049.jiho.core.AudioPlayer;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.time.temporal.ChronoUnit; // Import for microseconds
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Concrete implementation of AudioPlayer using javax.sound.sampled. This class handles the loading
 * and playback of an audio file.
 */
public class AudioManager implements AudioPlayer {
  private URL sourceUrl;

  /**
   * Constructs an AudioManager with the audio source file.
   *
   * @param source The audio file to be played.
   * @throws MalformedURLException if the file path cannot be converted to a URL.
   */
  public AudioManager(File source) throws MalformedURLException {
    this.sourceUrl = source.toURI().toURL();
  }

  /**
   * Constructs an AudioManager with the audio source URL (e.g., from a JAR resource).
   *
   * @param sourceUrl The URL to the audio resource.
   */
  public AudioManager(URL sourceUrl) {
    this.sourceUrl = sourceUrl;
  }

  /**
   * Plays the sound using Java Sound API. Ensures that both AudioInputStream and Clip resources are
   * closed.
   *
   * @param times The number of times to play.
   * @param duration The duration to wait after starting playback.
   * @throws UnsupportedAudioFileException if the audio file format is not supported.
   * @throws IOException if an I/O error occurs.
   * @throws LineUnavailableException if a line cannot be opened.
   * @throws InterruptedException if the thread is interrupted.
   */
  @Override
  public void play(int times, Duration duration)
      throws UnsupportedAudioFileException,
          IOException,
          LineUnavailableException,
          InterruptedException {

    // Use try-with-resources to ensure AudioInputStream is closed
    try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(this.sourceUrl)) {
      Clip line = null;
      try {
        AudioFormat format = audioStream.getFormat();
        DataLine.Info info = new DataLine.Info(Clip.class, format);
        line = (Clip) AudioSystem.getLine(info);

        // Open the clip with the audio stream.
        // The stream is now owned by the clip.
        line.open(audioStream);

        for (int i = 0; i < times; i++) {
          // Reset play position to the beginning
          line.setFramePosition(0);

          line.start();
          // Wait for the specified duration
          Thread.sleep(duration);
        }
      } finally {
        // Ensure clip is closed (which also releases audio stream resources)
        if (line != null) {
          line.close();
        }
      }
    }
  }

  /**
   * Gets the actual playback duration (length) of the loaded audio source. This opens a new stream
   * to calculate the length.
   *
   * @return The Duration of the audio clip.
   * @throws UnsupportedAudioFileException if the audio file format is not supported.
   * @throws IOException if an I/O error occurs when reading the file.
   */
  @Override
  public Duration getAudioDuration() throws UnsupportedAudioFileException, IOException {
    // Open a new stream just to get the format and frame length
    // Use try-with-resources to ensure it's closed immediately after
    try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(this.sourceUrl)) {
      AudioFormat format = audioStream.getFormat();
      long frameLength = audioStream.getFrameLength();

      // Calculate duration in microseconds
      long microseconds = (long) ((frameLength / format.getFrameRate()) * 1000000);

      return Duration.of(microseconds, ChronoUnit.MICROS);
    }
  }
}
