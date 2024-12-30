package my.work.mapper;

import java.util.List;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import my.work.dto.ProductDto;
import my.work.model.Product;

@NoArgsConstructor(access = AccessLevel.NONE)
public class ProductDtoMapper {

	public static List<ProductDto> toDtos(List<Product> products) {
		return products.stream().map(ProductDtoMapper::toDto).toList();
	}

	public static ProductDto toDto(Product product) {
		return new ProductDto(product.getId(), product.getName(), product.getDescription(), product.getPrice());
	}

	public static List<Product> toEntities(List<ProductDto> productDtos) {
		return productDtos.stream().map(ProductDtoMapper::toEntity).toList();
	}

	public static Product toEntity(ProductDto productDto) {
		return new Product(productDto.id(), productDto.name(), productDto.description(), productDto.price());
	}
}
