package my.work.controller;

import java.io.IOException;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.testcontainers.containers.MySQLContainer;

import io.restassured.RestAssured;
import my.work.dto.OrderDto;
import my.work.stub.InventoryClientStub;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderControllerIntegrationTest {

	@ServiceConnection
	static MySQLContainer<?> mySQLContainer;

	@LocalServerPort
	private int port;

	@Autowired
	private InventoryClientStub inventoryClientStub;
	
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
	void givenExistingStock_shouldCreateOrder() throws IOException {
		var order = OrderDto.builder()
				.code(10)
				.quantity(1)
				.build();

		inventoryClientStub.stubSuccessfullyGetStatusCall(10, 1);
		
		RestAssured
			.given()
				.contentType("application/json")
				.body(order)
			.when()
				.post("/api/v1/orders")
			.then()
				.statusCode(201)
				.body("number", Matchers.notNullValue(null))
				.body("code", Matchers.equalTo(10))
				.body("quantity", Matchers.equalTo(1))
				.body("totalPrice", Matchers.equalTo(2000));
	}

	@Test
	@Order(2)
	void givenNotExistingStock_shouldNotCreateOrder() {
		var order = OrderDto.builder()
				.code(10)
				.quantity(1)
				.build();

		inventoryClientStub.stubUnsuccessfullyGetStatusCall(10, 1);
		
		RestAssured
			.given()
				.contentType("application/json")
				.body(order)
			.when()
				.post("/api/v1/orders")
			.then()
				.statusCode(201)
				.body(CoreMatchers.equalTo(""));
	}
	
	@Test
	@Order(3)
	void shouldGetAllOrders() {
		RestAssured
			.when()
				.get("/api/v1/orders")
			.then()
				.statusCode(200)
				.body("size()", Matchers.equalTo(1));
	}

}
