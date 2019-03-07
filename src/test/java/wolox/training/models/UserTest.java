package wolox.training.models;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import wolox.training.repositories.UserRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private UserRepository userRepository;

  @Test
  public void whenUsernameIsNull_thenExceptionThrown() {

    User user = new User();

    assertThatThrownBy(() -> {
      user.setUsername(null);
    }).isInstanceOf(NullPointerException.class);

  }

  @Test
  public void whenNameIsNull_thenExceptionThrown() {

    User user = new User();

    assertThatThrownBy(() -> {
      user.setName(null);
    }).isInstanceOf(IllegalArgumentException.class);

  }

  @Test
  public void whenBirthdateIsNull_thenExceptionThrown() {

    User user = new User();

    assertThatThrownBy(() -> {
      user.setBirthdate(null);
    }).isInstanceOf(NullPointerException.class);

  }

  @Test
  public void whenBookIsNull_thenExceptionThrown() {

    User user = new User();

    assertThatThrownBy(() -> {
      user.addBook(null);
    }).isInstanceOf(IllegalArgumentException.class);

  }

}
