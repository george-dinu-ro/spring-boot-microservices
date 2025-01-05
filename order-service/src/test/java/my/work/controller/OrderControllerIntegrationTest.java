package my.work.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;

import io.restassured.RestAssured;
import my.work.dto.OrderDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderControllerIntegrationTest {

	@ServiceConnection
	static MySQLContainer<?> mySQLContainer;

	@LocalServerPort
	private int port;
	
	static {
		mySQLContainer = new MySQLContainer<>("mysql:latest");
		mySQLContainer.start();
	}

	@BeforeEach
	void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	@Test
	@Order(1)
	void shouldCreateOrder() {
		var order = OrderDto.builder()
				.code(10)
				.quantity(1)
				.build();

		var response = RestAssured
				.given()
					.contentType("application/json")
					.body(order)
				.when()
					.post("/api/v1/orders")
				.then()
					.statusCode(201)
					.extract()
					.body().asString();

		assertThat(response).isEqualTo("Order created");
	}

	@Test
	@Order(2)
	void shouldGetAllOrders() {
		RestAssured
			.when()
				.get("/api/v1/orders")
			.then()
				.statusCode(200)
				.body("size()", Matchers.equalTo(1));
	}

}
