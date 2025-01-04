package my.work.dto;

import lombok.Builder;

@Builder
public record OrderDto(Long id, String number, String code, Integer quantity) {

}
