package dev.haruki7049.jiho.core;

import java.io.File;
import java.time.Duration;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class AudioManager {
  private File source;

  public AudioManager(File source) {
    this.source = source;
  }

  public void play(int times, Duration duration) throws Exception {
    AudioInputStream audioStream = AudioSystem.getAudioInputStream(this.source);

    AudioFormat format = audioStream.getFormat();
    DataLine.Info info = new DataLine.Info(Clip.class, format);
    Clip line = (Clip) AudioSystem.getLine(info);

    try {
      line.open(audioStream);
    } finally {
      audioStream.close();
    }

    for (int i = 0; i < times; i++) {
      // Reset play position
      line.setFramePosition(0);

      line.start();
      Thread.sleep(duration);
    }

    line.close();
  }
}
