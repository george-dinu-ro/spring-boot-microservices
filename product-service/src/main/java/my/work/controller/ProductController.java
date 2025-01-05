package my.work.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import my.work.dto.ProductDto;
import my.work.service.ProductService;

@RequestMapping("/api/v1/products")
@RestController
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	ProductDto createProduct(@RequestBody ProductDto productDto) {
		return productService.createProduct(productDto);
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	List<ProductDto> getAllProducts() {
		return productService.getAllProducts();
	}

	@GetMapping("/filter")
	ResponseEntity<ProductDto> findByCode(@RequestParam Integer code) {
		var product = productService.findByCode(code);
		return product
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

}
