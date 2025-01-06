package my.work.service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.work.client.InventoryFeignClient;
import my.work.dto.OrderDto;
import my.work.dto.StatusDto;
import my.work.mapper.OrderDtoMapper;
import my.work.model.Order;
import my.work.repository.OrderRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

	private final OrderRepository orderRepository;

	private final InventoryFeignClient inventoryFeignClient;

	public OrderDto prepareAndGetOrder(OrderDto orderDto) {
		var statusDto = getProductStatus(orderDto);
		return !Objects.isNull(statusDto) ? createAndGetOrder(orderDto, statusDto) : null;
	}

	public List<OrderDto> getAllOrders() {
		var orders = OrderDtoMapper.toDtos(orderRepository.findAll());
		log.info("Orders found: {}", orders.size());

		return orders;
	}

	private StatusDto getProductStatus(OrderDto orderDto) {
		var statusDto = inventoryFeignClient.getStatus(orderDto.code(), orderDto.quantity());
		log.info("Status with code {} and quantity {} returned from inventory service: {}", orderDto.code(),
				orderDto.quantity(), statusDto);

		return statusDto;
	}

	private OrderDto createAndGetOrder(OrderDto orderDto, StatusDto statusDto) {
		var order = getOrder(orderDto, statusDto);
		orderRepository.save(order);
		log.info("Order created: {}", order);

		return OrderDtoMapper.toDto(order);
	}

	private Order getOrder(OrderDto orderDto, StatusDto statusDto) {
		var order = OrderDtoMapper.toEntity(orderDto);
		order.setNumber(UUID.randomUUID().toString());
		order.setTotalPrice(statusDto.totalPrice());

		return order;
	}

}
