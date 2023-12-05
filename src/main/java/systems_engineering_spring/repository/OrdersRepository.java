package systems_engineering_spring.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import systems_engineering_spring.entities.Order;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdersRepository extends CrudRepository<Order, Long> {
  List<Order> findByUserId(Long userId);
  @Query("select count(distinct o.orderId) as count from Order o")
  Long countDistinctOrderId();
  Optional<List<Order>> findByOrderId(Long id);
}
