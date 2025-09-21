package de.rieckpil.courses.book.review;

import de.rieckpil.courses.book.management.BookRepository;
import de.rieckpil.courses.book.management.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

  @Mock private ReviewVerifier reviewVerifier;

  @Mock private UserService userService;

  @Mock private BookRepository bookRepository;

  @Mock private ReviewRepository reviewRepository;

  @InjectMocks private ReviewService cut;

  private static final String ISBN = "42";
  private static final String USERNAME = "duke";
  private static final String EMAIL = "duke@email.com";

  @Test
  void shouldNotBeNull() {
    assertNotNull(reviewVerifier);
    assertNotNull(userService);
    assertNotNull(bookRepository);
    assertNotNull(reviewRepository);
    assertNotNull(cut);
  }

  @Test
  void shouldThrowExceptionWhenReviewIsNotExisting() {
    when(bookRepository.findByIsbn(ISBN)).thenReturn(null);

    assertThrows(
        IllegalArgumentException.class, () -> cut.createBookReview(ISBN, null, USERNAME, EMAIL));
  }
}
