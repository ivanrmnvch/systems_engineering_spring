package systems_engineering_spring.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Entity
@Table(name = "products")
public class ProductsEntity{
  @Id
  @Column(nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String description;

  @Column(nullable = false)
  private int price;

  @Column(nullable = false)
  private String imagePath;

  @ManyToOne
  @JoinColumn(name = "brand", referencedColumnName = "id")
  private Brand brand;

  @ManyToOne
  @JoinColumn(name = "category", referencedColumnName = "id")
  private SubSection subSection;

  @Column(columnDefinition = "boolean DEFAULT TRUE", nullable = false)
  private boolean active;

  @Column(nullable = false)
  private LocalDateTime updatedAt;

  @Column(nullable = false)
  private LocalDateTime createdAt;
}