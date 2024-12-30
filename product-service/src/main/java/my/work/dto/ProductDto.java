package my.work.dto;

import java.math.BigDecimal;

import lombok.Builder;

@Builder
public record ProductDto(String id, String name, String description, BigDecimal price) {

}
