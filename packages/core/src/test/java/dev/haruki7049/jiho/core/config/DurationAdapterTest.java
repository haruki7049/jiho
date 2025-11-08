package dev.haruki7049.jiho.core.config;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import java.time.Duration;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/** Tests for the Duration TypeAdapter. */
public class DurationAdapterTest {

  private Gson gson;

  /**
   * Sets up the test environment before each test method. Initializes Gson with the custom
   * DurationAdapter.
   */
  @BeforeMethod
  public void setUp() {
    // Initialize Gson with the custom adapter
    this.gson =
        new GsonBuilder().registerTypeAdapter(Duration.class, new DurationAdapter()).create();
  }

  @Test
  public void testDurationSerialization() {
    // Test writing Duration -> JSON String
    Duration duration = Duration.ofSeconds(10);
    String json = gson.toJson(duration);
    // ISO 8601 format
    assertEquals(json, "\"PT10S\"");
  }

  @Test
  public void testDurationDeserialization() {
    // Test reading JSON String -> Duration
    String json = "\"PT5M30S\""; // 5 minutes, 30 seconds
    Duration duration = gson.fromJson(json, Duration.class);
    assertEquals(duration, Duration.ofMinutes(5).plusSeconds(30));
  }

  @Test
  public void testNullSerialization() {
    // Test writing null Duration
    String json = gson.toJson(null, Duration.class);
    assertEquals(json, "null");
  }

  @Test
  public void testNullDeserialization() {
    // Test reading "null"
    Duration duration = gson.fromJson("null", Duration.class);
    assertNull(duration);
  }

  @Test(expectedExceptions = JsonSyntaxException.class)
  public void testInvalidFormatDeserialization() {
    // Test that invalid strings throw an exception
    // Gson wraps the adapter's IOException in a JsonSyntaxException
    gson.fromJson("\"invalid-duration-string\"", Duration.class);
  }
}
