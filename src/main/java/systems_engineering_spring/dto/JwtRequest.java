package systems_engineering_spring.dto;

import lombok.Data;

@Data
public class JwtRequest {
  private String login;
  private String password;
}
