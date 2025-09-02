package de.rieckpil.courses.book.review;

import java.sql.SQLException;

import javax.sql.DataSource;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest(
    properties = {
      "spring.flyway.enabled=false", // to disable flyway and let h2 in-memory kick in. not
      // advisable in production
      "spring.jpa.hibernate.ddl-auto=create-drop",
      "spring.datasource.driver-class-name=com.p6spy.engine.spy.P6SpyDriver", // P6Spy
      "spring.datasource.url=jdbc:p6spy:h2:mem:testing;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false" // P6Spy
    })
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReviewRepositoryTest {

  @Autowired private EntityManager entityManager;

  @Autowired private TestEntityManager testEntityManager;

  @Autowired private ReviewRepository cut;

  @Autowired private DataSource dataSource;

  @BeforeEach
  void beforeEach() {
    assertEquals(0, cut.count());
  }

  @Test
  void notNull() throws SQLException {
    assertNotNull(entityManager);
    assertNotNull(testEntityManager);
    assertNotNull(cut);
    assertNotNull(dataSource);

    System.out.println(dataSource.getConnection().getMetaData().getDatabaseProductName());
  }

  @Test
  void testSave() {
    Review review = new Review();
    review.setTitle("Review 101");
    review.setContent("Great book!");
    review.setCreatedAt(now());
    review.setRating(5);
    review.setBook(null);
    review.setUser(null);

    //    Review result = cut.save(review);
    Review result = testEntityManager.persistAndFlush(review);

    System.out.println(result);
    assertNotNull(result.getId());
  }

  @Test
  void transactionSupportTest() {
    Review review = new Review();
    review.setTitle("Review 101");
    review.setContent("Great book!");
    review.setCreatedAt(now());
    review.setRating(5);
    review.setBook(null);
    review.setUser(null);

    Review result = cut.save(review);

    System.out.println(result);
    assertNotNull(result.getId());
  }
}
