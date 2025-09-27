package dev.haruki7049.jiho;

public class AudioSourceIsNullException extends Exception {
  public AudioSourceIsNullException() {
    super("Audio source must not be null. Please edit your configuration. In Linux, it should be ~/.config/jiho/config.json");
  }
}
