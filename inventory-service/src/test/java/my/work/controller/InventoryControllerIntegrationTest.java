package my.work.controller;

import java.io.IOException;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.testcontainers.containers.MySQLContainer;

import io.restassured.RestAssured;
import my.work.model.Inventory;
import my.work.repository.InventoryRepository;
import my.work.stub.ProductClientStub;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
class InventoryControllerIntegrationTest {

	@ServiceConnection
	static MySQLContainer<?> mySQLContainer;

	@LocalServerPort
	private int port;

	@Autowired
	private InventoryRepository inventoryRepository;

	@Autowired
	private ProductClientStub productClientStub;
	
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
				.quantity(5)
				.build());
	}

	@AfterEach
	void clean() {
		inventoryRepository.deleteAll();
	}

	@Test
	void givenNotExistingCode_whenVerifyStock_thenReturnEmptyBody() {
		RestAssured
			.given()
				.contentType("application/json")
				.param("code", 100)
				.param("quantity", 1)
			.when()
				.get("/api/v1/inventories/status")
			.then()
				.statusCode(200)
				.body(CoreMatchers.equalTo(""));
	}

	@ParameterizedTest
	@CsvSource({"10, 4, 8000", "10, 5, 10000"})
	void givenExistingCode_whenVerifyStockWithSmallerOrEqualQuantity_thenReturnCommandInBody(String code, String quantity, String totalPrice) throws NumberFormatException, IOException {
		productClientStub.stubSuccessfullyFindByCodeCall(Integer.parseInt(code));
		
		RestAssured
			.given()
				.contentType("application/json")
				.param("code", code)
				.param("quantity", quantity)
			.when()
				.get("/api/v1/inventories/status")
			.then()
				.statusCode(200)
				.body("code", Matchers.equalTo(Integer.parseInt(code)))
				.body("name", Matchers.equalTo("Asus"))
				.body("description", Matchers.equalTo("Gaming laptop"))
				.body("quantity", Matchers.equalTo(Integer.parseInt(quantity)))
				.body("totalPrice", Matchers.equalTo(Integer.parseInt(totalPrice)));
	}
		
	@Test
	void givenExistingCode_whenVerifyStockWithBiggerQuantity_thenReturnEmptyBody() {
		RestAssured
			.given()
				.contentType("application/json")
				.param("code", 10)
				.param("quantity", 6)
			.when()
				.get("/api/v1/inventories/status")
			.then()
				.statusCode(200)
				.body(CoreMatchers.equalTo(""));
	}
	
	@Test
	void givenExistingCode_whenVerifyStockWithProductWithoutPrice_thenReturnEmptyBody() throws IOException {
		productClientStub.stubUnSuccessfullyFindByCodeCall(10);
				
		RestAssured
			.given()
				.contentType("application/json")
				.param("code", 10)
				.param("quantity", 1)
			.when()
				.get("/api/v1/inventories/status")
			.then()
				.statusCode(200)
				.body(CoreMatchers.equalTo(""));
	}

}
