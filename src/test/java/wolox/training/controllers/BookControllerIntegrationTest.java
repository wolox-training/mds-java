package wolox.training.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.github.javafaker.Faker;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import wolox.training.Utils;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerIntegrationTest {

  private static String BASE_PATH = "/api/books/";


  @MockBean
  private BookRepository bookRepository;

  @Autowired
  private MockMvc mvc;

  Faker faker = new Faker();

  @Test
  public void findAll_whenNoBooks_returnEmptyArray() throws Exception {

    ResultActions actions = mvc.perform(get(BASE_PATH)
        .contentType(MediaType.APPLICATION_JSON));

    actions.andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(0)));
  }

  @Test
  public void findAll_whenBooks_returnArrayWithBooks() throws Exception {

    List<Book> books = createBooks(5);

    given(bookRepository.findAll()).willReturn(books);

    ResultActions actions = mvc.perform(get(BASE_PATH)
        .contentType(MediaType.APPLICATION_JSON));

    actions.andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(books.size())));
  }

  @Test
  public void findOne_whenBookNotFound_return404() throws Exception {
    Book book = createBook();

    given(bookRepository.findById(book.getId())).willReturn(Optional.of(book));

    mvc.perform(get(BASE_PATH + (book.getId() + 1))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  public void findOne_whenBookFound_returnsBook() throws Exception {
    Book book = createBook();

    given(bookRepository.findById(book.getId())).willReturn(Optional.of(book));

    mvc.perform(get(BASE_PATH + book.getId())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(book.getId())))
        .andExpect(jsonPath("$.title", is(book.getTitle())))
        .andExpect(jsonPath("$.author", is(book.getAuthor())));
  }

  @Test
  public void create() throws Exception {
    Book book = createBook();

    String json = Utils.asJsonString(book);

    mvc.perform(post(BASE_PATH)
        .characterEncoding("utf-8")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isCreated());
  }

  @Test
  public void createMissingRequiredField() throws Exception {
    Book book = new Book();

    book.setId(faker.random().nextLong(Long.MAX_VALUE));
    book.setIsbn(faker.lorem().characters(15));
    book.setPages(faker.random().nextInt(50));
    book.setYear(faker.random().nextInt(1900, 2018).toString());
    // missing publisher
    book.setTitle(faker.lorem().characters(100));
    book.setSubtitle(faker.lorem().characters(100));
    book.setImage(faker.lorem().characters(100));
    book.setAuthor(faker.name().fullName());
    book.setGenre(faker.lorem().characters(10));

    String json = Utils.asJsonString(book);

    mvc.perform(post(BASE_PATH)
        .characterEncoding("utf-8")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void update() throws Exception {
    Book book = createBook();

    given(bookRepository.findById(book.getId())).willReturn(Optional.of(book));

    book.setAuthor("Modified Author");

    String json = Utils.asJsonString(book);

    mvc.perform(put(BASE_PATH + book.getId())
        .characterEncoding("utf-8")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isOk());
  }

  @Test
  public void updateBadId() throws Exception {
    Book book = createBook();

    given(bookRepository.findById(book.getId())).willReturn(Optional.of(book));

    book.setAuthor("Modified Author");

    String json = Utils.asJsonString(book);

    mvc.perform(put(BASE_PATH + (book.getId() + 1))
        .characterEncoding("utf-8")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isBadRequest());
  }


  private List<Book> createBooks(int n) {
    List<Book> books = new LinkedList<>();

    for (int j = 0; j < n; j ++) {
      books.add(createBook());
    }

    return books;
  }

  private Book createBook() {
    Book book = new Book();

    book.setId(faker.random().nextLong(Long.MAX_VALUE));
    book.setIsbn(faker.lorem().characters(15));
    book.setPages(faker.random().nextInt(50));
    book.setYear(faker.random().nextInt(1900, 2018).toString());
    book.setPublisher(faker.name().fullName());
    book.setTitle(faker.lorem().characters(100));
    book.setSubtitle(faker.lorem().characters(100));
    book.setImage(faker.lorem().characters(100));
    book.setAuthor(faker.name().fullName());
    book.setGenre(faker.lorem().characters(10));

    return book;

  }

}
