package dev.haruki7049.jiho;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator; // For recursive directory deletion
import java.util.stream.Stream; // For recursive directory deletion
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/** Tests for the CLI logic, specifically config initialization. */
public class CliTest {

  // Temporary directory for config file tests
  private Path tempDir;
  private Cli cliInstance;

  /**
   * Sets up the test environment before each test method. Creates a temporary directory and a new
   * Cli instance.
   *
   * @throws Exception if setup fails.
   */
  @BeforeMethod
  public void setUp() throws Exception {
    // Create a temporary directory before each test
    tempDir = Files.createTempDirectory("jiho-test-");
    cliInstance = new Cli();
  }

  /**
   * Cleans up the test environment after each test method. Recursively deletes the temporary
   * directory.
   *
   * @throws IOException if cleanup fails.
   */
  @AfterMethod
  public void tearDown() throws IOException {
    // Recursively delete the temporary directory after each test
    try (Stream<Path> walk = Files.walk(tempDir)) {
      walk.sorted(Comparator.reverseOrder()).forEach(Path::toFile);
    }
  }

  /**
   * Helper method to set the private field 'configPath' on a Cli instance.
   *
   * @param cli The instance to modify.
   * @param path The Path to set.
   * @throws Exception If reflection fails.
   */
  private void setPrivateConfigPath(Cli cli, Path path) throws Exception {
    Field field = Cli.class.getDeclaredField("configPath");
    field.setAccessible(true);
    field.set(cli, path);
  }

  /**
   * Helper method to invoke the private method 'loadInitialConfig'.
   *
   * @param cli The instance to invoke on.
   * @throws Exception If reflection fails.
   */
  private void invokeLoadInitialConfig(Cli cli) throws Exception {
    Method method = Cli.class.getDeclaredMethod("loadInitialConfig");
    method.setAccessible(true);
    method.invoke(cli);
  }

  @Test
  public void testLoadInitialConfigCreatesFile() throws Exception {
    // 1. Set the config path to a non-existent file in our temp dir
    Path testConfigPath = tempDir.resolve("config.json");
    setPrivateConfigPath(cliInstance, testConfigPath);

    // Verify it doesn't exist yet
    assertFalse(Files.exists(testConfigPath));

    // 2. Invoke the private method
    invokeLoadInitialConfig(cliInstance);

    // 3. Verify the file and its parent directories were created
    assertTrue(Files.exists(testConfigPath));

    // 4. Verify the content is exactly "{}"
    String content = Files.readString(testConfigPath);
    assertEquals(content, "{}");
  }

  @Test
  public void testLoadInitialConfigDoesNotOverwrite() throws Exception {
    // 1. Create an existing config file with different content
    Path testConfigPath = tempDir.resolve("config.json");
    String originalContent = "{\"duration\": \"PT10S\"}";
    Files.writeString(testConfigPath, originalContent);

    // Set the path on the CLI instance
    setPrivateConfigPath(cliInstance, testConfigPath);

    // 2. Invoke the private method
    invokeLoadInitialConfig(cliInstance);

    // 3. Verify the file was NOT overwritten
    String content = Files.readString(testConfigPath);
    assertEquals(content, originalContent);
  }
}
