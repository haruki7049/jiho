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
   * @throws UnsupportedAudioFileException if the audio file format is not supported.
   * @throws IOException if an I/O error occurs.
   * @throws LineUnavailableException if a line cannot be opened.
   * @throws InterruptedException if the sleeping thread is interrupted.
   */
  void play(int times, Duration duration)
      throws UnsupportedAudioFileException,
          IOException,
          LineUnavailableException,
          InterruptedException;
}
