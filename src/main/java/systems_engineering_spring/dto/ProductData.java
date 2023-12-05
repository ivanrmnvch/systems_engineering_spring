package systems_engineering_spring.dto;

import lombok.Data;
import systems_engineering_spring.entities.Brand;
import systems_engineering_spring.entities.SubSection;

@Data
public class ProductData {
  public SubSection subSection;
  public Brand brand;
}
