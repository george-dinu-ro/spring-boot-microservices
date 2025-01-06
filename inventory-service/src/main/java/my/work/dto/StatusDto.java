package my.work.dto;

import java.math.BigDecimal;

import lombok.Builder;

@Builder
public record StatusDto(int code, String name, String description, int quantity, BigDecimal totalPrice) {

}
