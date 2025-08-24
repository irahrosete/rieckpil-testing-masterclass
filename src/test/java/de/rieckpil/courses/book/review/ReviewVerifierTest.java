package de.rieckpil.courses.book.review;

import org.junit.jupiter.api.*;

class ReviewVerifierTest {

  private ReviewVerifier reviewVerifier;

  public ReviewVerifierTest() {
    System.out.println("Constructor called");
  }

  @BeforeEach
  void setup() {
    System.out.println("Before each");
    reviewVerifier = new ReviewVerifier();
  }

  @AfterEach
  void tearDown() {
    System.out.println("After each");
  }

  @BeforeAll
  static void beforeAll() {
    System.out.println("Before all");
  }

  @AfterAll
  static void afterAll() {
    System.out.println("After all");
  }

  @Test
  void shouldFailWhenReviewContainsSwearWords() {
    String review = "This book is shit.";
    System.out.println("Testing a review");
    boolean result = reviewVerifier.doesMeetQualityStandards(review);
    Assertions.assertFalse(result);
  }

  @Test
  void shouldFailWhenReviewContainsSwearWords2() {
    String review = "This book is shit.";
    System.out.println("Testing a review2");
    boolean result = reviewVerifier.doesMeetQualityStandards(review);
    Assertions.assertFalse(result);
  }
}
