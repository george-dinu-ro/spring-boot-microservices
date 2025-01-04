package my.work.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import my.work.dto.OrderDto;
import my.work.service.OrderService;

@RequestMapping("/api/v1/orders")
@RestController
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	String createOrder(@RequestBody OrderDto orderDto) {
		orderService.createOrder(orderDto);
		return "Order created";
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	List<OrderDto> getAllOrders() {
		return orderService.getAllOrders();
	}

}
