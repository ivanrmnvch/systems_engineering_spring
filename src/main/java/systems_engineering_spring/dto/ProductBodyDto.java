package systems_engineering_spring.dto;

import lombok.Data;

@Data
public class ProductBodyDto {
  private Long mainSectionId;
  private Long subSectionId;
  private Long brandId;
  private int price;
  private String description;
  private String imagePath;
  private String name;
  private boolean active;
}
