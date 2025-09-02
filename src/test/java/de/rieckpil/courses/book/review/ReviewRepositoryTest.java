package de.rieckpil.courses.book.review;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.sql.DataSource;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

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

}
