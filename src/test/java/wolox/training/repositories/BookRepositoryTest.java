package wolox.training.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import wolox.training.models.Book;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BookRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private BookRepository bookRepository;

  @Test
  public void whenFindByAuthor_thenReturnBook() {

    Book book = new Book();
    book.setAuthor("MDS");
    book.setGenre("Fiction");
    book.setImage("http://some.url/image/path");
    book.setIsbn("123123123");
    book.setPages(1);
    book.setPublisher("Wolox");
    book.setSubtitle("Some Subtitle");
    book.setTitle("Title");
    book.setYear("1990");

    entityManager.persist(book);
    entityManager.flush();

    Book found = bookRepository.findByAuthor("MDS");

    assertThat(found.getAuthor()).isEqualTo("MDS");

  }

}
