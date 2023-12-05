package systems_engineering_spring.specifications;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import systems_engineering_spring.entities.Brand;
import systems_engineering_spring.entities.ProductsEntity;
import systems_engineering_spring.entities.SubSection;

public class ProductSpecifications {
  public static Specification<ProductsEntity> hasBrand(String id) {
    return (root, query, criteriaBuilder) -> {
      Join<ProductsEntity, Brand> brandJoin = root.join("brand", JoinType.INNER);
      return criteriaBuilder.equal(brandJoin.get("id"), id);
    };
  }

  public static Specification<ProductsEntity> hasCategory(String id) {
    return (root, query, criteriaBuilder) -> {
      Join<ProductsEntity, SubSection> subSectionJoin = root.join("subSection", JoinType.INNER);
      return criteriaBuilder.equal(subSectionJoin.get("id"), id);
    };
  }

  public static Specification<ProductsEntity> nameLike(String name) {
    return (root, query, criteriaBuilder) -> {
      if (StringUtils.isNotBlank(name)) {
        String pattern = "%" + name.toLowerCase() + "%";
        return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), pattern);
      }
      return null;
    };
  }

  public static Specification<ProductsEntity> priceInRange(Double priceMin, Double priceMax) {
    return (root, query, criteriaBuilder) -> {
      if (priceMin != null && priceMax != null) {
        return criteriaBuilder.between(root.get("price"), priceMin, priceMax);
      } else if (priceMin != null) {
        return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), priceMin);
      } else if (priceMax != null) {
        return criteriaBuilder.lessThanOrEqualTo(root.get("price"), priceMax);
      }
      return null;
    };
  }
}
