package de.rieckpil.courses.book.review;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.sql.DataSource;
import java.sql.SQLException;

import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
//  (properties = {
//    "spring.flyway.enabled=false",
//    "spring.jpa.hibernate.ddl-auto=create-drop"
//  }) to disable flyway and let h2 in-memory kick in. not advisable in production
class ReviewRepositoryTest {

  @Autowired
  private EntityManager entityManager;

  @Autowired
  private ReviewRepository cut;

  @Autowired
  private DataSource dataSource;

  @Test
  void notNull() throws SQLException {
    assertNotNull(entityManager);
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

    Review result = cut.save(review);

    System.out.println(result);
    assertNotNull(result.getId());
  }

}
