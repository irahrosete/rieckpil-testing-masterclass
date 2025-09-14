package de.rieckpil.courses.book.management;

import java.util.List;

import de.rieckpil.courses.config.WebSecurityConfig;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.apache.http.HttpHeaders.ACCEPT;
import static org.junit.jupiter.api.parallel.ExecutionMode.SAME_THREAD;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_XML;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
@Import(WebSecurityConfig.class)
@Execution(SAME_THREAD)
class BookControllerTest {

  @MockitoBean private BookManagementService bookManagementService;

  @Autowired private MockMvc mockMvc;

  @Test
  void shouldGetEmptyArrayWhenNoBooksExist() throws Exception {
    MvcResult mvcResult =
        mockMvc
            .perform(get("/api/books").header(ACCEPT, APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.size()", Matchers.is(0)))
            .andDo(print())
            .andReturn();
  }

  @Test
  void shouldNotReturnXml() throws Exception {
    mockMvc
        .perform(get("/api/books").header(ACCEPT, APPLICATION_XML))
        .andExpect(status().isNotAcceptable());
  }

  @Test
  void shouldReturnBooksWhenServiceReturnsBooks() throws Exception {
    Book bookOne =
        createBook(
            1L,
            "42",
            "Java 14",
            "Mike",
            "Good book",
            "Software Engineering",
            200L,
            "Oracle",
            "ftp://localhost:42");
    Book bookTwo =
        createBook(
            2L,
            "82",
            "Java 15",
            "Duke",
            "Nice book",
            "Software Architecture",
            300L,
            "O'reilly",
            "ftp://localhost:82");

    when(bookManagementService.getAllBooks()).thenReturn(List.of(bookOne, bookTwo));

    mockMvc
        .perform(get("/api/books").header(ACCEPT, APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.size()", Matchers.is(2)))
        .andExpect(jsonPath("$[0].id").doesNotExist())
        .andExpect(jsonPath("$[0].isbn", Matchers.is("42")))
        .andExpect(jsonPath("$[0].title", Matchers.is("Java 14")))
        .andExpect(jsonPath("$[1].id").doesNotExist())
        .andExpect(jsonPath("$[1].isbn", Matchers.is("82")))
        .andExpect(jsonPath("$[1].title", Matchers.is("Java 15")));
  }

  private Book createBook(
      Long id,
      String isbn,
      String title,
      String author,
      String description,
      String genre,
      Long pages,
      String publisher,
      String thumbnailUrl) {
    Book result = new Book();
    result.setId(id);
    result.setIsbn(isbn);
    result.setTitle(title);
    result.setAuthor(author);
    result.setDescription(description);
    result.setGenre(genre);
    result.setPages(pages);
    result.setPublisher(publisher);
    result.setThumbnailUrl(thumbnailUrl);
    return result;
  }
}
