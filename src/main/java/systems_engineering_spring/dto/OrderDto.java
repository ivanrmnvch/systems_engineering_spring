package systems_engineering_spring.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderDto {
  private List<Long> basketIds;
}
