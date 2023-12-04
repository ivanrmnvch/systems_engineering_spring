package systems_engineering_spring.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "basket")
public class Basket {
  @Id
  @Column(nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne
  @JoinColumn(name = "productId", referencedColumnName = "id")
  private ProductsEntity product;

  @ManyToOne
  @JoinColumn(name = "userId", referencedColumnName = "id")
  private User user;

  @Column(columnDefinition = "boolean DEFAULT TRUE", nullable = false)
  private boolean active;

  @Column(nullable = false)
  private LocalDateTime updatedAt;

  @Column(nullable = false)
  private LocalDateTime createdAt;

  @Column()
  private int count;
}
