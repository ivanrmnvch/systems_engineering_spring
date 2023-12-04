package systems_engineering_spring.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "orders")
public class Order {
  @Id
  @Column(nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column()
  private long orderId;

  @ManyToOne
  @JoinColumn(name = "productId", referencedColumnName = "id")
  private ProductsEntity product;

  @ManyToOne
  @JoinColumn(name = "userId", referencedColumnName = "id")
  private User user;

  @Column(columnDefinition = "VARCHAR(255) DEFAULT 'created'")
  private String status;

  @Column()
  private int count;

  @Column(nullable = false)
  private LocalDateTime updatedAt;

  @Column(nullable = false)
  private LocalDateTime createdAt;
}
