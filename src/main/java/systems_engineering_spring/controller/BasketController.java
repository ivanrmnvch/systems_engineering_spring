package systems_engineering_spring.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import systems_engineering_spring.dto.BasketDto;
import systems_engineering_spring.service.BasketService;

@RestController
@RequiredArgsConstructor
public class BasketController {
  private final BasketService basketService;
  @GetMapping("/basket")
  public ResponseEntity<?> getBasket() throws JsonProcessingException {
    return basketService.getBasket();
  }

  @PostMapping("/basket/{id}")
  public ResponseEntity<?> addProductToBasket(
    @PathVariable("id") Long id,
    @RequestBody BasketDto basketDto
  ) {
    return basketService.addProductToBasket(id, basketDto);
  }

  @PutMapping("/basket/{id}")
  public ResponseEntity<?> updateProductCount(
    @PathVariable("id") Long id,
    @RequestBody BasketDto basketDto
  ) {
    return basketService.updateProductCount(id, basketDto);
  }

  @DeleteMapping("/basket/{id}")
  public ResponseEntity<?> deleteBasket(@PathVariable("id") Long id) {
    return basketService.deleteBasket(id);
  }
}
