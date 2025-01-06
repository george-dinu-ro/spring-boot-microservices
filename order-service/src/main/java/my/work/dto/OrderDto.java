package my.work.dto;

import java.math.BigDecimal;

import lombok.Builder;

@Builder
public record OrderDto(Long id, String number, int code, int quantity, BigDecimal totalPrice) {

}
