package de.rieckpil.courses.book.review;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ReviewVerifierTest {

  @Test
  void shouldFailWhenReviewContainsSwearWords() {
    String review = "This book is shit.";

    ReviewVerifier reviewVerifier = new ReviewVerifier();
    boolean result = reviewVerifier.doesMeetQualityStandards(review);
    Assertions.assertFalse(result);
  }
}
