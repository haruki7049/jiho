package dev.haruki7049.jiho.core;

import static org.testng.Assert.assertEquals;

import java.lang.reflect.Method;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.testng.annotations.Test;

/** Tests for the core Jiho logic class. */
public class JihoTest {

  /**
   * Helper method to invoke the private method calculateTimes using reflection.
   *
   * @param time The ZonedDateTime to pass to the method.
   * @return The calculated number of times (1-12).
   * @throws Exception If reflection fails.
   */
  private int invokeCalculateTimes(ZonedDateTime time) throws Exception {
    // Need an instance to invoke the method on.
    // The constructor is clean, so passing nulls is safe
    // because calculateTimes doesn't use instance fields.
    Jiho instance = new Jiho(null, null);

    // Get the private method
    Method method = Jiho.class.getDeclaredMethod("calculateTimes", ZonedDateTime.class);
    // Make it accessible
    method.setAccessible(true);

    // Invoke and cast the result
    Object result = method.invoke(instance, time);
    return (int) result;
  }

  @Test
  public void testCalculateTimesMorning() throws Exception {
    // 9:00 AM
    ZonedDateTime time = ZonedDateTime.of(2025, 1, 1, 9, 0, 0, 0, ZoneId.systemDefault());
    assertEquals(invokeCalculateTimes(time), 9);
  }

  @Test
  public void testCalculateTimesAfternoon() throws Exception {
    // 14:00 (2:00 PM)
    ZonedDateTime time = ZonedDateTime.of(2025, 1, 1, 14, 0, 0, 0, ZoneId.systemDefault());
    assertEquals(invokeCalculateTimes(time), 2);
  }

  @Test
  public void testCalculateTimesNoon() throws Exception {
    // 12:00 (12:00 PM)
    // 12 % 12 == 0, should be corrected to 12
    ZonedDateTime time = ZonedDateTime.of(2025, 1, 1, 12, 0, 0, 0, ZoneId.systemDefault());
    assertEquals(invokeCalculateTimes(time), 12);
  }

  @Test
  public void testCalculateTimesMidnight() throws Exception {
    // 00:00 (12:00 AM)
    // 0 % 12 == 0, should be corrected to 12
    ZonedDateTime time = ZonedDateTime.of(2025, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault());
    assertEquals(invokeCalculateTimes(time), 12);
  }
}
