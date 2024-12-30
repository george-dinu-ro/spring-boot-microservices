package my.work;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;

import io.restassured.RestAssured;
import my.work.dto.OrderDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderServiceApplicationTest {

	@ServiceConnection
	static MySQLContainer<?> mySQLContainer;

	static {
		mySQLContainer = new MySQLContainer("mysql:latest");
		mySQLContainer.start();
	}

	@LocalServerPort
	private int port;

	@BeforeEach
	void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	@Test
	void shouldCreateOrder() {
		var order = OrderDto.builder()
                .number("Order 1")
                .code("Code 1")
                .price(BigDecimal.valueOf(10.0))
                .quantity(1)
				.build();
		
		var response = RestAssured
			.given()
				.contentType("application/json")
				.body(order)
				.post("/api/v1/orders")
			.then()
				.statusCode(201)
				.extract()
				.body().asString();
		
        assertThat(response)
        .isEqualTo("Order created");
	}
}
