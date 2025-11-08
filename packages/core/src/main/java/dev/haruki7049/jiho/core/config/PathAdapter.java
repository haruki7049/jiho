package dev.haruki7049.jiho.core.config;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * TypeAdapter for java.nio.file.Path. Serializes Path to its string representation using
 * toString(). Deserializes string representation back to Path using Paths.get().
 */
public class PathAdapter extends TypeAdapter<Path> {
  /**
   * Writes a Path object as a JSON string.
   *
   * @param writer the JSON writer
   * @param value the Path object to write
   * @throws IOException if an error occurs writing JSON
   */
  @Override
  public void write(JsonWriter writer, Path value) throws IOException {
    if (value == null) {
      writer.nullValue();
    } else {
      writer.value(value.toString());
    }
  }

  /**
   * Reads a JSON string and converts it to a Path object.
   *
   * @param reader the JSON reader
   * @return the deserialized Path object
   * @throws IOException if an error occurs reading JSON
   */
  @Override
  public Path read(JsonReader reader) throws IOException {
    if (reader.peek() == JsonToken.NULL) {
      // Read and return null if the JSON value is null
      reader.nextNull();
      return null;
    } else {
      // Read the string value
      String pathString = reader.nextString();

      // Convert the string back to a Path object using Paths.get()
      return Paths.get(pathString);
    }
  }
}
