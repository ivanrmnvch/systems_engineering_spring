package systems_engineering_spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import systems_engineering_spring.entities.Brand;
import systems_engineering_spring.repository.BrandRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BrandService {
  private final BrandRepository brandRepository;

  public List<Brand> getBrandList() {
    return brandRepository.findAll();
  }

  public Optional<Brand> getBrandById(Long id) {
    return brandRepository.findById(id);
  }
}
