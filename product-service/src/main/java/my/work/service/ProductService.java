package my.work.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.work.dto.ProductDto;
import my.work.mapper.ProductDtoMapper;
import my.work.repository.ProductRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

	private final ProductRepository productRepository;

	public ProductDto createProduct(ProductDto productDto) {
		var product = ProductDtoMapper.toEntity(productDto);

		productRepository.save(product);
		log.info("Product created: {}", product);

		return ProductDtoMapper.toDto(product);
	}

	public List<ProductDto> getAllProducts() {
		var products = ProductDtoMapper.toDtos(productRepository.findAll());
		log.info("Products found: {}", products.size());

		return products;
	}
}
