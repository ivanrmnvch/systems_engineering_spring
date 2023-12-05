package systems_engineering_spring.controller;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import systems_engineering_spring.dto.JwtRequest;
import systems_engineering_spring.dto.RegistrationUserDto;
import systems_engineering_spring.dto.TokenDto;
import systems_engineering_spring.service.AuthService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {
  private final AuthService authService;

  @PostMapping("/auth")
  public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
    return authService.createAuthToken(authRequest);
  }

  @PostMapping("/registration")
  public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
    return authService.createNewUser(registrationUserDto);
  }

  @RequestMapping(value = "/refresh", method = RequestMethod.POST, produces = "application/json")
  public ResponseEntity<?> refreshToken(@RequestBody TokenDto tokenDto) {
    return authService.refreshToken(tokenDto);
  }
}
