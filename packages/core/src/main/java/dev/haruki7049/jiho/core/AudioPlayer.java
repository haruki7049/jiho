package dev.haruki7049.jiho.core;

import java.io.IOException;
import java.time.Duration;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

interface AudioPlayer {
  public void play(int times, Duration duration)
      throws UnsupportedAudioFileException,
          IOException,
          LineUnavailableException,
          InterruptedException;
}
