package my.work.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.work.dto.OrderDto;
import my.work.mapper.OrderDtoMapper;
import my.work.repository.OrderRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

	private final OrderRepository orderRepository;

	public void createOrder(OrderDto orderDto) {
		var order = OrderDtoMapper.toEntity(orderDto);
		order.setNumber(UUID.randomUUID().toString());

		orderRepository.save(order);
		log.info("Order created: {}", order);
	}

	public List<OrderDto> getAllOrders() {
		var orders = OrderDtoMapper.toDtos(orderRepository.findAll());
		log.info("Orders found: {}", orders.size());

		return orders;
	}
	
}
