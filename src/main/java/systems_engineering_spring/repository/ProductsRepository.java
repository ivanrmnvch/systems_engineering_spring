package systems_engineering_spring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import systems_engineering_spring.entities.ProductsEntity;

import java.util.List;

@Repository
public interface ProductsRepository extends CrudRepository<ProductsEntity, Long> {
  Page<ProductsEntity> findAll(Specification<ProductsEntity> spec, Pageable pageable);
  List<ProductsEntity> findByIdIn(List<Long> productIds);
}
