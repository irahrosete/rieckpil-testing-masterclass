package de.rieckpil.courses.book;

import com.jayway.jsonpath.JsonPath;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
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

  @Test
  void testWithJsonPath() {
    String result = """
      {
        "name": "duke",
        "age": 42,
        "hobbies": ["soccer", "java"],
        "tags": ["java", "jdk"],
        "orders": [42, 42, 16]
      }""";

    Assertions.assertEquals(2, JsonPath.parse(result).read("$.tags.length()", Long.class));
    Assertions.assertEquals("duke", JsonPath.parse(result).read("$.name", String.class));
    Assertions.assertEquals(100, JsonPath.parse(result).read("$.orders.sum()", Long.class));
  }
}
