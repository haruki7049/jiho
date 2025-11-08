package dev.haruki7049.jiho.core;

/** Exception thrown when the audio source file is null or does not exist. */
public class InvalidAudioSourceException extends Exception {
  /**
   * Constructs the exception with a specific message.
   *
   * @param message The detail message.
   */
  public InvalidAudioSourceException(String message) {
    super(message);
  }
}
