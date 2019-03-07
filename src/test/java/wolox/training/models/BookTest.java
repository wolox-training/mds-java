package wolox.training.models;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import wolox.training.repositories.BookRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BookTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private BookRepository bookRepository;

  @Test
  public void whenAuthorIsNull_thenExceptionThrown() {

    Book book = new Book();

    assertThatThrownBy(() -> {
      book.setAuthor(null);
    }).isInstanceOf(IllegalArgumentException.class);

  }

  @Test
  public void whenTitleIsNull_thenExceptionThrown() {

    Book book = new Book();

    assertThatThrownBy(() -> {
      book.setTitle(null);
    }).isInstanceOf(IllegalArgumentException.class);

  }

  @Test
  public void whenImageIsNull_thenExceptionThrown() {

    Book book = new Book();

    assertThatThrownBy(() -> {
      book.setImage(null);
    }).isInstanceOf(IllegalArgumentException.class);

  }

  @Test
  public void whenSubtitleIsNull_thenExceptionThrown() {

    Book book = new Book();

    assertThatThrownBy(() -> {
      book.setSubtitle(null);
    }).isInstanceOf(IllegalArgumentException.class);

  }

  @Test
  public void whenPublisherIsNull_thenExceptionThrown() {

    Book book = new Book();

    assertThatThrownBy(() -> {
      book.setPublisher(null);
    }).isInstanceOf(IllegalArgumentException.class);

  }

  @Test
  public void whenYearIsNull_thenExceptionThrown() {

    Book book = new Book();

    assertThatThrownBy(() -> {
      book.setYear(null);
    }).isInstanceOf(IllegalArgumentException.class);

  }

  @Test
  public void whenPagesIsNull_thenExceptionThrown() {

    Book book = new Book();

    assertThatThrownBy(() -> {
      book.setPages(null);
    }).isInstanceOf(NullPointerException.class);

  }

  @Test
  public void whenISBNIsNull_thenExceptionThrown() {

    Book book = new Book();

    assertThatThrownBy(() -> {
      book.setIsbn(null);
    }).isInstanceOf(IllegalArgumentException.class);

  }

}
