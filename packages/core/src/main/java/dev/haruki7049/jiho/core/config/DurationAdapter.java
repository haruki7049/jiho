package dev.haruki7049.jiho.core.config;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.time.Duration;

/**
 * TypeAdapter for java.time.Duration. Serializes Duration to its ISO 8601 string representation
 * (e.g., "PT20.345S"). Deserializes the ISO 8601 string back to a Duration object using
 * Duration.parse().
 */
public class DurationAdapter extends TypeAdapter<Duration> {
  /**
   * Writes a Duration object as an ISO 8601 JSON string.
   *
   * @param writer the JSON writer
   * @param value the Duration object to write
   * @throws IOException if an error occurs writing JSON
   */
  @Override
  public void write(JsonWriter writer, Duration value) throws IOException {
    if (value == null) {
      // Write null if the Duration object is null
      writer.nullValue();
    } else {
      // Write the standard ISO 8601 string representation
      writer.value(value.toString());
    }
  }

  /**
   * Reads an ISO 8601 JSON string and converts it to a Duration object.
   *
   * @param reader the JSON reader
   * @return the deserialized Duration object
   * @throws IOException if an error occurs reading JSON
   */
  @Override
  public Duration read(JsonReader reader) throws IOException {
    if (reader.peek() == JsonToken.NULL) {
      // Read and return null if the JSON value is null
      reader.nextNull();
      return null;
    } else {
      // Read the string value
      String durationString = reader.nextString();

      // Parse the ISO 8601 string back to a Duration object
      try {
        return Duration.parse(durationString);
      } catch (java.time.format.DateTimeParseException e) {
        // Handle potential parsing errors
        throw new IOException("Failed to parse Duration: " + durationString, e);
      }
    }
  }
}
