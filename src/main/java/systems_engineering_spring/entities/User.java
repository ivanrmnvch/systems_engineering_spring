package systems_engineering_spring.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collection;

@Data
@Entity
@Table(name = "users")
public class User {
  @Id
  @Column(nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false)
  private String login;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String email;

  @Column(columnDefinition = "boolean DEFAULT FALSE", nullable = false)
  private boolean blocked;

  @Column(nullable = false)
  private LocalDateTime updatedAt;

  @Column(nullable = false)
  private LocalDateTime createdAt;

  @ManyToMany
  @JoinTable(
    name = "users_roles",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "roles_id")
  )
  private Collection<Role> roles;
}
