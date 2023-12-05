package systems_engineering_spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import systems_engineering_spring.dto.ProductBodyDto;
import systems_engineering_spring.dto.ProductData;
import systems_engineering_spring.entities.Brand;
import systems_engineering_spring.entities.ProductsEntity;
import systems_engineering_spring.entities.SubSection;
import systems_engineering_spring.exceptions.Resp;
import systems_engineering_spring.repository.ProductsRepository;
import systems_engineering_spring.specifications.ProductSpecifications;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductsService {
  private final ProductsRepository productsRepository;
  private final BrandService brandService;
  private final SectionService sectionService;

  public Page<ProductsEntity> getList(
    String name,
    String category,
    String brand,
    Double priceMin,
    Double priceMax,
    int offset,
    int limit
  ) {
    Specification<ProductsEntity> spec;

    spec = ProductSpecifications.nameLike(name);

    if (brand != null && !brand.isEmpty()) {
      spec = spec.and(ProductSpecifications.hasBrand(brand));
    }

    if (category != null && !category.isEmpty()) {
      spec = spec.and(ProductSpecifications.hasCategory(category));
    }

    spec = spec.and(ProductSpecifications.priceInRange(priceMin, priceMax));

    PageRequest pageable = PageRequest.of(offset, limit);
    return productsRepository.findAll(spec, pageable);
  }
  public Optional<ProductsEntity> getProductById(Long id) {
    return productsRepository.findById(id);
  }

  public ResponseEntity<?> updateProduct(Long id, ProductBodyDto productBodyDto) {
    Optional<ProductsEntity> productsEntity = getProductById(id);

    if (productsEntity.isEmpty()) {
      return new ResponseEntity<>(new Resp(HttpStatus.BAD_REQUEST.value(), "Ошибка данных"), HttpStatus.BAD_REQUEST);
    }


    ProductsEntity product = productsEntity.get();
    product.setId(id);
    product.setName(productBodyDto.getName());
    product.setDescription(productBodyDto.getDescription());
    product.setPrice(productBodyDto.getPrice());
    product.setImagePath(productBodyDto.getImagePath());

    ProductData productData = getProductData(productBodyDto.getSubSectionId(), productBodyDto.getBrandId());

    product.setBrand(productData.getBrand());
    product.setSubSection(productData.getSubSection());
    product.setActive(productBodyDto.isActive());

    LocalDateTime now = LocalDateTime.now();

    product.setUpdatedAt(now);

    productsRepository.save(product);
    return new ResponseEntity<>(new Resp(HttpStatus.CREATED.value(), "Продукт успешно изменен"), HttpStatus.CREATED);
  }

  public ResponseEntity<?> createProduct(ProductBodyDto productBodyDto) {
    ProductsEntity product = new ProductsEntity();

    product.setName(productBodyDto.getName());
    product.setDescription(productBodyDto.getDescription());
    product.setPrice(productBodyDto.getPrice());
    product.setImagePath(productBodyDto.getImagePath());

    ProductData productData = getProductData(productBodyDto.getSubSectionId(), productBodyDto.getBrandId());

    product.setBrand(productData.getBrand());
    product.setSubSection(productData.getSubSection());

    product.setActive(true);

    LocalDateTime now = LocalDateTime.now();

    product.setCreatedAt(now);
    product.setUpdatedAt(now);

    productsRepository.save(product);
    return new ResponseEntity<>(new Resp(HttpStatus.CREATED.value(), "Продукт успешно создан"), HttpStatus.CREATED);
  }

  private ProductData getProductData(Long subSectionId, Long brandId) {
    Optional<Brand> brand = brandService.getBrandById(brandId);
    Optional<SubSection> subSection = sectionService.getSubSectionById(subSectionId);

    if (brand.isEmpty() || subSection.isEmpty()) {
      return new ProductData();
    }

    ProductData productData = new ProductData();
    productData.setBrand(brand.get());
    productData.setSubSection(subSection.get());

    return productData;
  }
}
