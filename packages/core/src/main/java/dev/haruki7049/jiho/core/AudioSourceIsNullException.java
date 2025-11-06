package dev.haruki7049.jiho.core;

/** Exception thrown when the audio source file is null or does not exist. */
public class AudioSourceIsNullException extends Exception {

  /** Constructs the exception with a default message. */
  public AudioSourceIsNullException() {
    super(
        "Audio source is null, or there is no audio file. Please edit your configuration. In Linux,"
            + " it should be ~/.config/jiho/config.json");
  }
}
