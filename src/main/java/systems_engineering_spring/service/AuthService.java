package systems_engineering_spring.service;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import systems_engineering_spring.dto.*;
import systems_engineering_spring.entities.User;
import systems_engineering_spring.exceptions.Resp;
import systems_engineering_spring.utils.JwtTokenUtils;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserService userService;
  private final JwtTokenUtils jwtTokenUtils;
  private final AuthenticationManager authenticationManager;

  public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getLogin(), authRequest.getPassword()));
    } catch (BadCredentialsException e) {
      return new ResponseEntity<>(new Resp(HttpStatus.UNAUTHORIZED.value(), "Неверный логин или пароль"), HttpStatus.UNAUTHORIZED);
    }

    UserDetails userDetails = userService.loadUserByUsername(authRequest.getLogin());
    String token = jwtTokenUtils.generateToken(userDetails);
    return ResponseEntity.ok(new AuthorizedUserDto(token, authRequest.getLogin()));
  }

  public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
    if (userService.fingByLogin(registrationUserDto.getLogin()).isPresent()) {
      return new ResponseEntity<>(new Resp(HttpStatus.BAD_REQUEST.value(), "Пользователь с указаным логином уже зарегистрирован"), HttpStatus.BAD_REQUEST);
    }
    User user = userService.createNewUser(registrationUserDto);
    return ResponseEntity.ok(new UserDto(user.getId(), user.getLogin(), user.getEmail()));
  }

  public ResponseEntity<?> refreshToken(TokenDto tokenDto) {
    try {
      String login = jwtTokenUtils.getLogin(tokenDto.getToken());
      UserDetails userDetails = userService.loadUserByUsername(login);
      String token = jwtTokenUtils.generateToken(userDetails);
      AuthorizedUserDto data = new AuthorizedUserDto(token, login);
      return new ResponseEntity<>(new RefreshDto(data, "Токен успешно обновлен"), HttpStatus.OK);
    } catch (ExpiredJwtException e) {
      return new ResponseEntity<>(new RefreshDto(null, "Время жизни токена истекло. Пройдите авторизацию повторно"), HttpStatus.OK);
    }
  }
}
