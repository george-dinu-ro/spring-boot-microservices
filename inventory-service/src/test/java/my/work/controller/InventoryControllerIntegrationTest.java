package my.work.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;

import io.restassured.RestAssured;
import my.work.model.Inventory;
import my.work.repository.InventoryRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InventoryControllerIntegrationTest {

	@ServiceConnection
	static MySQLContainer<?> mySQLContainer;

	@LocalServerPort
	private int port;

	@Autowired
	private InventoryRepository inventoryRepository;

	static {
		mySQLContainer = new MySQLContainer<>("mysql:latest");
		mySQLContainer.start();
	}

	@BeforeEach
	void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
		
		inventoryRepository.save(Inventory.builder()
				.code(10)
				.quantity(20)
				.build());
	}

	@AfterEach
	void clean() {
		inventoryRepository.deleteAll();
	}

	@Test
	void givenNotExistingCode_whenVerifyStock_thenReturnFalse() {
		var response = RestAssured
				.given()
					.contentType("application/json")
					.param("code", 100)
					.param("quantity", 1)
				.when()
					.get("/api/v1/inventories/inStock")
				.then()
					.statusCode(200)
					.extract()
					.body().as(Boolean.class);

		assertThat(response).isFalse();
	}

	@ParameterizedTest
	@CsvSource({"10, 19", "10, 20"})
	void givenExistingCode_whenVerifyStockWithSmallerOrEqualQuantity_thenReturnTrue(String code, String quantity) {
		var response = RestAssured
				.given()
					.contentType("application/json")
					.param("code", code)
					.param("quantity", quantity)
				.when()
					.get("/api/v1/inventories/inStock")
				.then()
					.statusCode(200)
					.extract()
						.body().as(Boolean.class);

		assertThat(response).isTrue();
	}
		
	@Test
	void givenExistingCode_whenVerifyStockWithBiggerQuantity_thenReturnFalse() {
		var response = RestAssured
				.given()
					.contentType("application/json")
					.param("code", 10)
					.param("quantity", 21)
				.when()
					.get("/api/v1/inventories/inStock")
				.then()
					.statusCode(200)
					.extract()
						.body().as(Boolean.class);
		
		assertThat(response).isFalse();
	}
	
}
