package systems_engineering_spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import systems_engineering_spring.dto.OrderDto;
import systems_engineering_spring.dto.StatusDto;
import systems_engineering_spring.service.OrdersService;

@RestController
@RequiredArgsConstructor
public class OrdersController {
  private final OrdersService ordersService;

  @PostMapping("/order")
  public ResponseEntity<?> createOrder(@RequestBody OrderDto orderDto) {
    return ordersService.createOrder(orderDto);
  }

  @GetMapping("/order")
  public ResponseEntity<?> getOrders() {
   return ordersService.getOrders();
  }

  @GetMapping("/order/{id}")
  public ResponseEntity<?> getOrderById(@PathVariable("id") Long id) {
    return ordersService.getOrderById(id);
  }

  @GetMapping(path = "/checkout",  produces = "application/json; charset=UTF-8")
  public ResponseEntity<?> checkout(@RequestParam(name = "ids") String idArray) {
    return ordersService.checkout(idArray);
  }

  @PutMapping("/order/{id}")
  public ResponseEntity<?> updateOrderStatus(
    @PathVariable("id") Long id,
    @RequestBody() StatusDto statusDto
  ) {
    return ordersService.updateOrderStatus(id, statusDto);
  }
}
