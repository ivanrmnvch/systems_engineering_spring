package systems_engineering_spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import systems_engineering_spring.entities.Brand;
import systems_engineering_spring.service.BrandService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BrandController {
  private final BrandService brandService;

  @GetMapping("/brands")
  public List<Brand> getBrandList() {
    return brandService.getBrandList();
  }
}
