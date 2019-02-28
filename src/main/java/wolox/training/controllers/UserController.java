package wolox.training.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wolox.training.models.User;
import wolox.training.repositories.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController extends AbstractCrudController<User, Long> {

  @Autowired
  private UserRepository userRepository;

  protected UserRepository getRepository() {
    return userRepository;
  }

}
