package de.rieckpil.courses.book.review;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertFalse;

class ReviewVerifierTest {

  private ReviewVerifier reviewVerifier;

  @BeforeEach
  void setup() {
    reviewVerifier = new ReviewVerifier();
  }

  @Test
  void shouldFailWhenReviewContainsSwearWords() {
    String review = "This book is shit.";
    System.out.println("Testing a review");

    boolean result = reviewVerifier.doesMeetQualityStandards(review);
    assertFalse(result, "ReviewVerifier detected a swear word.");
  }

  @Test
  @DisplayName("Should fail when review contains Lorem ipsum")
  void testLoremIpsum() {
    String review = """
      Lorem ipsum dolor sit amet consectetur adipiscing elit. \
      Quisque faucibus ex sapien vitae pellentesque sem placerat. \
      In id cursus mi pretium tellus duis convallis. \
      Tempus leo eu aenean sed diam urna tempor. \
      Pulvinar vivamus fringilla lacus nec metus bibendum egestas. \
      Iaculis massa nisl malesuada lacinia integer nunc posuere. \
      Ut hendrerit semper vel class aptent taciti sociosqu. \
      Ad litora torquent per conubia nostra inceptos himenaeos.\
      """;
    System.out.println(review);
    boolean result = reviewVerifier.doesMeetQualityStandards(review);
    assertFalse(result, "ReviewVerifier detected lorem ipsum.");
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/badReview.csv")
  void shouldFailWhenReviewIsOfBadQuality(String review) {
    boolean result = reviewVerifier.doesMeetQualityStandards(review);
    assertFalse(result, "ReviewVerifier detected bad review");
  }
}
