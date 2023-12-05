package systems_engineering_spring.dto;

import lombok.Data;

@Data
public class AuthorizedUserDto {
  private String token;
  private String login;

  public AuthorizedUserDto(String token, String login) {
    this.token = token;
    this.login = login;
  }
}
