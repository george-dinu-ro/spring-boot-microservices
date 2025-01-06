package my.work.service;

import java.math.BigDecimal;
import java.util.Objects;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.work.client.ProductFeignClient;
import my.work.dto.ProductDto;
import my.work.dto.StatusDto;
import my.work.repository.InventoryRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

	private final InventoryRepository inventoryRepository;

	private final ProductFeignClient productFeignClient;

	public StatusDto getStatus(int code, int quantity) {
		var isInStock = inventoryRepository.existsByCodeAndQuantityIsGreaterThanEqual(code, quantity);
		log.info("Product with code {} is in stock: {}", code, isInStock);

		return isInStock ? getProductAndWrap(code, quantity) : null;
	}

	private StatusDto getProductAndWrap(int code, int quantity) {
		var product = getProduct(code);
		return !Objects.isNull(product) ? getStockDto(product, quantity) : null;
	}

	private ProductDto getProduct(int code) {
		var product = productFeignClient.findByCode(code);
		log.info("Product with code {} returned from product service: {}", code, product);

		return product;
	}

	private static StatusDto getStockDto(ProductDto productDto, int quantity) {
		return StatusDto.builder()
				.code(productDto.code())
				.name(productDto.name())
				.description(productDto.description())
				.quantity(quantity)
				.totalPrice(getTotalPrice(productDto, quantity))
				.build();
	}

	private static BigDecimal getTotalPrice(ProductDto productDto, int quantity) {
		return BigDecimal.valueOf(quantity).multiply(productDto.price());
	}
}
