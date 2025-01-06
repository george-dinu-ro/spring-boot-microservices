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
				.id(order.getId())
				.number(order.getNumber())
				.code(order.getCode())
				.quantity(order.getQuantity())
				.totalPrice(order.getTotalPrice())
				.build();
	}

	public static List<Order> toEntities(List<OrderDto> orderDtos) {
		return orderDtos.stream().map(OrderDtoMapper::toEntity).toList();
	}

	public static Order toEntity(OrderDto orderDto) {
		return Order.builder()
				.id(orderDto.id())
				.number(orderDto.number())
				.code(orderDto.code())
				.quantity(orderDto.quantity())
				.totalPrice(orderDto.totalPrice())
				.build();
	}
	
}
