package dev.haruki7049.jiho.core;

public class AudioSourceIsNullException extends Exception {
  public AudioSourceIsNullException() {
    super(
        "Audio source is null, or there is no audio file. Please edit your configuration. In Linux,"
            + " it should be ~/.config/jiho/config.json");
  }
}
