package de.rieckpil.courses.book.management;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@WebMvcTest(BookController.class)
class BookControllerTest {

  @MockitoBean
  private BookManagementService bookManagementService;

  @Test
  void shouldStart() {

  }

}
