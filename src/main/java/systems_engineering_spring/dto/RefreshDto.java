package systems_engineering_spring.dto;

import lombok.Data;

@Data
public class RefreshDto {
  private AuthorizedUserDto data;
  private String message;
  public RefreshDto(AuthorizedUserDto data, String message) {
    this.data = data;
    this.message = message;
  }
}
