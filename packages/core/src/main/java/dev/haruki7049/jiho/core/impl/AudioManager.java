package dev.haruki7049.jiho.core.impl;

import dev.haruki7049.jiho.core.AudioPlayer;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.time.temporal.ChronoUnit; // Import for microseconds
import java.util.logging.Logger; // Import Logger
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl; // Import
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Concrete implementation of AudioPlayer using javax.sound.sampled. This class handles the loading
 * and playback of an audio file.
 */
public class AudioManager implements AudioPlayer {
  private URL sourceUrl;
  private static final Logger logger = Logger.getLogger("jiho.audio"); // Add logger

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
   * @param volume The playback volume (0.0 to 1.0), or null for default.
   * @throws UnsupportedAudioFileException if the audio file format is not supported.
   * @throws IOException if an I/O error occurs.
   * @throws LineUnavailableException if a line cannot be opened.
   * @throws InterruptedException if the thread is interrupted.
   */
  @Override
  public void play(int times, Duration duration, Float volume)
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

        // Set volume if specified
        if (volume != null) {
          setVolume(line, volume);
        }

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
   * Sets the volume of the Clip using FloatControl (Master Gain).
   *
   * @param clip The clip to modify.
   * @param volume The target volume (linear scale, 0.0 to 1.0).
   */
  private void setVolume(Clip clip, float volume) {
    // Check if MASTER_GAIN control is supported
    if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
      FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

      // Clamp volume between 0.0 and 1.0
      float clampedVolume = Math.max(0.0f, Math.min(1.0f, volume));

      // Convert linear volume (0.0-1.0) to decibels (dB)
      // The FloatControl works in dB (logarithmic scale)
      float decibel;
      if (clampedVolume <= 0.0001f) {
        // Handle mute (or near-mute) by setting to minimum decibel
        decibel = gainControl.getMinimum();
      } else {
        // Formula: decibel = 20 * log10(linear_ratio)
        decibel = (float) (20.0 * Math.log10(clampedVolume));
      }

      // Ensure the calculated decibel is within the valid range of the control
      decibel = Math.max(gainControl.getMinimum(), Math.min(decibel, gainControl.getMaximum()));

      logger.fine("Setting volume. Linear: " + clampedVolume + " -> dB: " + decibel);
      gainControl.setValue(decibel);

    } else {
      logger.warning("Volume control (MASTER_GAIN) not supported for this audio line.");
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
