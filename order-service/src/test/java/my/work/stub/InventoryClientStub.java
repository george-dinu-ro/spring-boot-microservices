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
import my.work.dto.StatusDto;

@Component
@RequiredArgsConstructor
public class InventoryClientStub {

	private final ObjectMapper objectMapper;
	
	public void stubSuccessfullyGetStatusCall(int code, int quantity) throws IOException {
		var statusDto = StatusDto.builder()
				.code(code)
				.name("Asus")
				.description("Gaming laptop")
				.quantity(quantity)
				.totalPrice(BigDecimal.valueOf(2000))
				.build();
		
		stubFor(get(urlEqualTo("/api/v1/inventories/status?code=" + code + "&quantity=" + quantity))
				.willReturn(aResponse()
						.withStatus(200)
						.withHeader("Content-Type", "application/json")
						.withBody(getJson(statusDto))));
	}
	
	public void stubUnsuccessfullyGetStatusCall(int code, int quantity) {		
		stubFor(get(urlEqualTo("/api/v1/inventories/status?code=" + code + "&quantity=" + quantity))
				.willReturn(aResponse()
						.withStatus(200)
						.withHeader("Content-Type", "application/json")));
	}
	
	private String getJson(StatusDto statusDto) throws IOException {
		return objectMapper.writeValueAsString(statusDto);
	}
	
}
