package systems_engineering_spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import systems_engineering_spring.dto.RegistrationUserDto;
import systems_engineering_spring.entities.User;
import systems_engineering_spring.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
  private final UserRepository userRepository;
  private final RoleService roleService;
  private final PasswordEncoder passwordEncoder;

  public Optional<User> fingByLogin(String login) {
    return userRepository.findByLogin(login);
  }

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
    User user = fingByLogin(login).orElseThrow(() -> new UsernameNotFoundException(
      String.format("Пользователь '%s' не найден", login)
    ));

    return new org.springframework.security.core.userdetails.User(
      user.getLogin(),
      user.getPassword(),
      user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList()
    );
  }

  public User createNewUser(RegistrationUserDto registrationUserDto) {
    User user = new User();
    user.setLogin(registrationUserDto.getLogin());
    user.setEmail(registrationUserDto.getEmail());
    user.setPassword(passwordEncoder.encode(registrationUserDto.getPassword()));
    user.setRoles(List.of(roleService.getUserRole()));
    user.setBlocked(false);
    LocalDateTime now = LocalDateTime.now();
    user.setCreatedAt(now);
    user.setUpdatedAt(now);
    return userRepository.save(user);
  }
}
