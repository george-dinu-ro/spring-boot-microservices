package my.work.dto;

import lombok.Builder;

@Builder
public record OrderDto(Long id, String number, Integer code, Integer quantity) {

}
