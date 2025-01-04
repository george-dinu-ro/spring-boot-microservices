package my.work.dto;

import java.math.BigDecimal;

import lombok.Builder;

@Builder
public record ProductDto(String id, int code, String name, String description, BigDecimal price) {

}
