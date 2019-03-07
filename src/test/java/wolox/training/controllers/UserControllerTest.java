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
import java.time.ZoneId;
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
import wolox.training.models.User;
import wolox.training.repositories.UserRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

  private static String BASE_PATH = "/api/users/";

  @MockBean
  private UserRepository userRepository;

  @Autowired
  private MockMvc mvc;

  Faker faker = new Faker();

  @Test
  public void findAll_whenNoUsers_returnEmptyArray() throws Exception {

    ResultActions actions = mvc.perform(get(BASE_PATH)
        .contentType(MediaType.APPLICATION_JSON));

    actions.andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(0)));
  }

  @Test
  public void findAll_whenUsers_returnArrayWithUsers() throws Exception {

    List<User> users = createUsers(5);

    given(userRepository.findAll()).willReturn(users);

    ResultActions actions = mvc.perform(get(BASE_PATH)
        .contentType(MediaType.APPLICATION_JSON));

    actions.andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(users.size())));
  }

  @Test
  public void findOne_whenUserNotFound_return404() throws Exception {
    User user = createUser();

    given(userRepository.findById(user.getId())).willReturn(Optional.of(user));

    mvc.perform(get(BASE_PATH + (user.getId() + 1))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  public void findOne_whenUserFound_returnsUser() throws Exception {
    User user = createUser();

    given(userRepository.findById(user.getId())).willReturn(Optional.of(user));

    mvc.perform(get(BASE_PATH + user.getId())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(user.getId())))
        .andExpect(jsonPath("$.name", is(user.getName())))
        .andExpect(jsonPath("$.username", is(user.getUsername())));
  }

  @Test
  public void create() throws Exception {
    User user = createUser();

    String json = Utils.asJsonString(user);

    mvc.perform(post(BASE_PATH)
        .characterEncoding("utf-8")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isCreated());
  }

  @Test
  public void createMissingRequiredField() throws Exception {
    User user = new User();

    user.setId(faker.random().nextLong(Long.MAX_VALUE));
    user.setUsername(faker.name().username());
    user.setName(faker.name().fullName());

    String json = Utils.asJsonString(user);

    mvc.perform(post(BASE_PATH)
        .characterEncoding("utf-8")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void update() throws Exception {
    User user = createUser();

    given(userRepository.findById(user.getId())).willReturn(Optional.of(user));

    user.setUsername("Modified Username");

    String json = Utils.asJsonString(user);

    mvc.perform(put(BASE_PATH + user.getId())
        .characterEncoding("utf-8")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isOk());
  }

  @Test
  public void updateBadId() throws Exception {
    User user = createUser();

    given(userRepository.findById(user.getId())).willReturn(Optional.of(user));

    user.setUsername("Modified Username");

    String json = Utils.asJsonString(user);

    mvc.perform(put(BASE_PATH + (user.getId() + 1))
        .characterEncoding("utf-8")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isBadRequest());
  }


  private List<User> createUsers(int n) {
    List<User> users = new LinkedList<>();

    for (int j = 0; j < n; j ++) {
      users.add(createUser());
    }

    return users;
  }

  private User createUser() {
    User user = new User();

    user.setId(faker.random().nextLong(Long.MAX_VALUE));
    user.setUsername(faker.name().username());
    user.setName(faker.name().fullName());
    user.setBirthdate(faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

    return user;

  }

}
