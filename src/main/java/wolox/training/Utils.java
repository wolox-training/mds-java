package wolox.training;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class Utils {

  public static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
