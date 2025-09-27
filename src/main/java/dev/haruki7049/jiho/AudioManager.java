package dev.haruki7049.jiho;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Clip;
import java.io.File;

public class AudioManager {
  private File source;

  public AudioManager(File source) {
    this.source = source;
  }

  public void play() throws Exception {
    AudioInputStream audioStream = AudioSystem
      .getAudioInputStream(this.source);

    AudioFormat format = audioStream.getFormat();
    DataLine.Info info = new DataLine.Info(Clip.class, format);
    Clip line = (Clip) AudioSystem.getLine(info);

    // Play
    line.open(audioStream);
    line.start();

    // Drain & stop
    line.drain();
    line.close();
  }
}
