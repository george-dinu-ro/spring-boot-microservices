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
		return ProductDto.builder()
				.id(product.getId())
				.code(product.getCode())
				.name(product.getName())
				.description(product.getDescription())
				.price(product.getPrice())
				.build();
	}

	public static List<Product> toEntities(List<ProductDto> productDtos) {
		return productDtos.stream().map(ProductDtoMapper::toEntity).toList();
	}

	public static Product toEntity(ProductDto productDto) {
		return Product.builder()
				.id(productDto.id())
				.code(productDto.code())
				.name(productDto.name())
				.description(productDto.description())
				.price(productDto.price())
				.build();
	}
	
}
