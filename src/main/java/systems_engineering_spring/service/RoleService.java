package systems_engineering_spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import systems_engineering_spring.entities.Role;
import systems_engineering_spring.repository.RoleRepository;

@Service
@RequiredArgsConstructor
public class RoleService {
  private final RoleRepository roleRepository;

  public Role getUserRole() {
    return roleRepository.findByName("ROLE_USER").get();
  }
}
