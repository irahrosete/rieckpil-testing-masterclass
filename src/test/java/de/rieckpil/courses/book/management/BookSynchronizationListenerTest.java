package de.rieckpil.courses.book.management;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookSynchronizationListenerTest {

  public static final String VALID_ISBN = "1234567890123";
  @Mock
  private BookRepository bookRepository;

  @Mock
  private OpenLibraryApiClient openLibraryApiClient;

  @InjectMocks
  private BookSynchronizationListener cut;

  @Test
  void shouldRejectBookWhenIsbnIsMalformed() {
    BookSynchronization bookSynchronization = new BookSynchronization("42");

    cut.consumeBookUpdates(bookSynchronization);

    verifyNoInteractions(openLibraryApiClient, bookRepository);
  }

  @Test
  void shouldNotOverrideWHenBookAlreadyExists() {
    BookSynchronization bookSynchronization = new BookSynchronization(VALID_ISBN);

    when(bookRepository.findByIsbn(VALID_ISBN)).thenReturn(new Book());
    cut.consumeBookUpdates(bookSynchronization);

    verifyNoInteractions(openLibraryApiClient);
    verify(bookRepository, times(0)).save(any());
  }

  @Test
  void shouldThrowExceptionWhenProcessingFails() {
    BookSynchronization bookSynchronization = new BookSynchronization(VALID_ISBN);

    when(bookRepository.findByIsbn(VALID_ISBN)).thenReturn(null);
    when(openLibraryApiClient.fetchMetadataForBook(VALID_ISBN)).thenThrow(new RuntimeException("Network timeout"));

    assertThrows(RuntimeException.class, () -> cut.consumeBookUpdates(bookSynchronization));
  }

  @Test
  void shouldStoreBookWhenNewAndCorrectIsbn() {

  }

}
