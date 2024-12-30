package my.work.dto;

import java.math.BigDecimal;

import lombok.Builder;

@Builder
public record OrderDto(Long id, String number, String code, BigDecimal price, Integer quantity) {

}
