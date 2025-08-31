package de.rieckpil.courses.book;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

public class JsonTest {

  @Test
  void testWithJSONAssert() throws JSONException {
    String result = """
      {
        "name": "duke",
        "age": 42,
        "hobbies": ["soccer", "java"]
      }""";

    String expectedName = """
      {
        "name": "duke"
      }""";
    String expectedHobbies = """
      {
        "hobbies": ["java", "soccer"]
      }""";

    JSONAssert.assertEquals(expectedName, result, false);
    JSONAssert.assertEquals(expectedHobbies, result, false);
  }
}
