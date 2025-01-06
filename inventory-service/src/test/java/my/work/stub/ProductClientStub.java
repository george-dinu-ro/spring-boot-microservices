package my.work.stub;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

import java.io.IOException;
import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import my.work.dto.ProductDto;

@Component
@RequiredArgsConstructor
public class ProductClientStub {

	private final ObjectMapper objectMapper;
	
	public void stubSuccessfullyFindByCodeCall(int code) throws IOException {
		var productDto = ProductDto.builder()
				.id("a1")
				.code(code)
				.name("Asus")
				.description("Gaming laptop")
				.price(BigDecimal.valueOf(2000))
				.build();
		
		stubFor(get(urlEqualTo("/api/v1/products/filter?code=" + code))
				.willReturn(aResponse()
						.withStatus(200)
						.withHeader("Content-Type", "application/json")
						.withBody(getString(productDto))));
	}

	public void stubUnSuccessfullyFindByCodeCall(int code) throws IOException {
		stubFor(get(urlEqualTo("/api/v1/products/filter?code=" + code))
				.willReturn(aResponse()
						.withStatus(200)
						.withHeader("Content-Type", "application/json")
						));
	}
	
	private String getString(ProductDto productDto) throws IOException {
		return objectMapper.writeValueAsString(productDto);
	}
}
