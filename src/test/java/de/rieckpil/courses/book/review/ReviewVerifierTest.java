package de.rieckpil.courses.book.review;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static de.rieckpil.courses.book.review.RandomReviewParameterResolverExtension.RandomReview;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(RandomReviewParameterResolverExtension.class)
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
    assertFalse(result, "ReviewVerifier did not detect a swear word.");
  }

  @Test
  @DisplayName("Should fail when review contains Lorem ipsum")
  void testLoremIpsum() {
    String review =
        """
      Lorem ipsum dolor sit amet consectetur adipiscing elit. \
      Quisque faucibus ex sapien vitae pellentesque sem placerat. \
      In id cursus mi pretium tellus duis convallis. \
      Tempus leo eu aenean sed diam urna tempor. \
      Pulvinar vivamus fringilla lacus nec metus bibendum egestas. \
      Iaculis massa nisl malesuada lacinia integer nunc posuere. \
      Ut hendrerit semper vel class aptent taciti sociosqu. \
      Ad litora torquent per conubia nostra inceptos himenaeos.\
      """;
    boolean result = reviewVerifier.doesMeetQualityStandards(review);
    assertFalse(result, "ReviewVerifier did not detect lorem ipsum.");
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/badReview.csv")
  void shouldFailWhenReviewIsOfBadQuality(String review) {
    boolean result = reviewVerifier.doesMeetQualityStandards(review);
    assertFalse(result, "ReviewVerifier did not detect bad review");
  }

  @RepeatedTest(5)
  void shouldFailWhenRandomReviewQualityIsBad(@RandomReview String review)
      throws InterruptedException {
    Thread.sleep(1000);

    System.out.println(review);
    boolean result = reviewVerifier.doesMeetQualityStandards(review);
    assertFalse(result, "ReviewVerifier did not detect random bad review");
  }

  @Test
  void shouldPassWhenReviewIsGood() throws InterruptedException {
    Thread.sleep(1000);

    String review =
        "I can totally recommend this book to anyone interested in learning how to write Java code!";
    boolean result = reviewVerifier.doesMeetQualityStandards(review);
    assertTrue(result, "ReviewVerifier did not detect a good review");
  }

  @Test
  void shouldPassWhenReviewIsGoodHamcrest() {
    String review =
      "I can totally recommend this book to anyone interested in learning how to write Java code!";
    boolean result = reviewVerifier.doesMeetQualityStandards(review);
//    assertTrue(result, "ReviewVerifier did not detect a good review"); Junit 5

    MatcherAssert.assertThat("ReviewVerifier did not detect a good review", result, Matchers.equalTo(true));
  }
}
