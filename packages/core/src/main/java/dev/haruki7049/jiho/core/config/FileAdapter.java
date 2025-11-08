package dev.haruki7049.jiho.core.config;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.File;
import java.io.IOException;

/**
 * TypeAdapter for java.io.File. Serializes File to its string representation using getPath().
 * Deserializes string representation back to a File using new File(path).
 */
public class FileAdapter extends TypeAdapter<File> {

  /**
   * Writes a File object as a JSON string (using its path).
   *
   * @param writer the JSON writer
   * @param value the File object to write
   * @throws IOException if an error occurs writing JSON
   */
  @Override
  public void write(JsonWriter writer, File value) throws IOException {
    if (value == null) {
      writer.nullValue();
    } else {
      writer.value(value.getPath());
    }
  }

  /**
   * Reads a JSON string and converts it to a File object.
   *
   * @param reader the JSON reader
   * @return the deserialized File object
   * @throws IOException if an error occurs reading JSON
   */
  @Override
  public File read(JsonReader reader) throws IOException {
    if (reader.peek() == JsonToken.NULL) {
      // Read and return null if the JSON value is null
      reader.nextNull();
      return null;
    } else {
      // Read the string value
      String pathString = reader.nextString();

      // Convert the string back to a File object
      return new File(pathString);
    }
  }
}
