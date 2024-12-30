package my.work.mapper;

import java.util.List;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import my.work.dto.OrderDto;
import my.work.model.Order;

@NoArgsConstructor(access = AccessLevel.NONE)
public class OrderDtoMapper {

	public static List<OrderDto> toDtos(List<Order> orders) {
		return orders.stream().map(OrderDtoMapper::toDto).toList();
	}

	public static OrderDto toDto(Order order) {
		return new OrderDto(order.getId(), order.getNumber(), order.getCode(), order.getPrice(), order.getQuantity());
	}

	public static List<Order> toEntities(List<OrderDto> orderDtos) {
		return orderDtos.stream().map(OrderDtoMapper::toEntity).toList();
	}

	public static Order toEntity(OrderDto orderDto) {
		return new Order(orderDto.id(), orderDto.number(), orderDto.code(), orderDto.price(), orderDto.quantity());
	}
}
