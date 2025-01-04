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
		return OrderDto.builder()
				.number(order.getNumber())
				.code(order.getCode())
				.quantity(order.getQuantity())
				.build();
	}

	public static List<Order> toEntities(List<OrderDto> orderDtos) {
		return orderDtos.stream().map(OrderDtoMapper::toEntity).toList();
	}

	public static Order toEntity(OrderDto orderDto) {
		return Order.builder()
				.number(orderDto.number())
				.code(orderDto.code())
				.quantity(orderDto.quantity())
				.build();
	}
	
}
