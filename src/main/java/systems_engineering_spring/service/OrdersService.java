package systems_engineering_spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import systems_engineering_spring.dto.OrderDto;
import systems_engineering_spring.dto.StatusDto;
import systems_engineering_spring.entities.Basket;
import systems_engineering_spring.entities.Order;
import systems_engineering_spring.entities.ProductsEntity;
import systems_engineering_spring.entities.User;
import systems_engineering_spring.exceptions.Resp;
import systems_engineering_spring.repository.BasketRepository;
import systems_engineering_spring.repository.OrdersRepository;
import systems_engineering_spring.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrdersService {
  private final UserRepository userRepository;
  private final OrdersRepository ordersRepository;
  private final BasketRepository basketRepository;
  public ResponseEntity<?> createOrder(OrderDto orderDto) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String login = authentication.getName();

    Optional<User> user = userRepository.findByLogin(login);

    if (user.isEmpty()) {
      return new ResponseEntity<>(new Resp(HttpStatus.BAD_REQUEST.value(), "Пользователь не найден"), HttpStatus.BAD_REQUEST);
    }

    Long orderId = ordersRepository.countDistinctOrderId() + 1;

    List<Basket> basketList = basketRepository.findByIdIn(orderDto.getBasketIds());

    basketRepository.deleteAll(basketList);

    List<Order> orders = new ArrayList<>();

    LocalDateTime now = LocalDateTime.now();

    for (Basket basket : basketList) {
      ProductsEntity product = basket.getProduct();
      int count = basket.getCount();

      Order order = new Order();
      order.setOrderId(orderId);
      order.setUser(user.get());
      order.setProduct(product);
      order.setCount(count);
      order.setStatus("created");
      order.setCreatedAt(now);
      order.setUpdatedAt(now);
      orders.add(order);
    }

    ordersRepository.saveAll(orders);
    return new ResponseEntity<>(new Resp(HttpStatus.OK.value(), "Заказ успешно создан"), HttpStatus.OK);
  }

  public ResponseEntity<?> getOrders() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String login = authentication.getName();

    Optional<User> user = userRepository.findByLogin(login);

    if (user.isEmpty()) {
      return new ResponseEntity<>(new Resp(HttpStatus.BAD_REQUEST.value(), "Пользователь не найден"), HttpStatus.BAD_REQUEST);
    }

    List<Order> orders = ordersRepository.findByUserId(user.get().getId());

    return new ResponseEntity<>(orders, HttpStatus.OK);
  }

  public ResponseEntity<?> checkout(String idArray) {
    if (Objects.equals(idArray, "")) {
      return new ResponseEntity<>(new String[0], HttpStatus.OK);
    }

    String[] basketIds = idArray.split(", ");

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String login = authentication.getName();

    Optional<User> user = userRepository.findByLogin(login);

    if (user.isEmpty()) {
      return new ResponseEntity<>(new Resp(HttpStatus.BAD_REQUEST.value(), "Пользователь не найден"), HttpStatus.BAD_REQUEST);
    }

    List<Long> ids = Arrays.stream(basketIds).map(Long::parseLong).toList();
    List<Basket> basketList = basketRepository.findByIdIn(ids);

    return new ResponseEntity<>(basketList, HttpStatus.OK);
  }

  public ResponseEntity<?> getOrderById(Long id) {
    Optional<List<Order>> order = ordersRepository.findByOrderId(id);
    if (order.isEmpty()) {
      return new ResponseEntity<>(new Resp(HttpStatus.BAD_REQUEST.value(), "Заказ не найден"), HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(order.get(), HttpStatus.OK);
  }

  public ResponseEntity<?> updateOrderStatus(Long id, StatusDto statusDto) {
    Optional<List<Order>> order = ordersRepository.findByOrderId(id);
    if (order.isEmpty()) {
      return new ResponseEntity<>(new Resp(HttpStatus.BAD_REQUEST.value(), "Заказ не найден"), HttpStatus.BAD_REQUEST);
    }
    List<Order> orderList = order.get();

    orderList.forEach(item -> item.setStatus(statusDto.getStatus()));

    ordersRepository.saveAll(orderList);

    return new ResponseEntity<>(new Resp(HttpStatus.OK.value(), "Статус успешно изменен"), HttpStatus.OK);
  }
}
