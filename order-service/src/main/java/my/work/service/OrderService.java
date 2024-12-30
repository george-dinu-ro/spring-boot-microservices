package my.work.service;

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

		orderRepository.save(order);
		log.info("Order created: {}", order);
	}
}
