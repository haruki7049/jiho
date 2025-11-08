package dev.haruki7049.jiho.core;

import java.io.IOException;
import java.time.Duration;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Interface defining the contract for an audio playback service. This abstracts the actual
 * implementation of audio playback.
 */
public interface AudioPlayer {

  /**
   * Plays the audio source a specified number of times.
   *
   * @param times The number of times to play the sound.
   * @param duration The duration to wait or play for each repetition.
   * @param volume The playback volume (0.0 to 1.0), or null for default.
   * @throws UnsupportedAudioFileException if the audio file format is not supported.
   * @throws IOException if an I/O error occurs.
   * @throws LineUnavailableException if a line cannot be opened.
   * @throws InterruptedException if the sleeping thread is interrupted.
   */
  void play(int times, Duration duration, Float volume)
      throws UnsupportedAudioFileException,
          IOException,
          LineUnavailableException,
          InterruptedException;

  /**
   * Gets the actual playback duration (length) of the loaded audio source.
   *
   * @return The Duration of the audio clip.
   * @throws UnsupportedAudioFileException if the audio file format is not supported.
   * @throws IOException if an I/O error occurs when reading the file.
   */
  Duration getAudioDuration() throws UnsupportedAudioFileException, IOException;
}
