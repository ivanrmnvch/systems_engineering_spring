package systems_engineering_spring.dto;

import lombok.Data;

@Data
public class RegistrationUserDto {
  private String login;
  private String password;
  private String email;
}
