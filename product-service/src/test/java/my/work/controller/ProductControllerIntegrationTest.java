package my.work.controller;

import java.math.BigDecimal;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MongoDBContainer;

import io.restassured.RestAssured;
import my.work.dto.ProductDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductControllerIntegrationTest {

	@ServiceConnection
	static MongoDBContainer mongoDBContainer;

	@LocalServerPort
	private int port;
	
	static {
		mongoDBContainer = new MongoDBContainer("mongo:latest");
		mongoDBContainer.start();
	}

	@BeforeEach
	void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	@Test
	@Order(1)
	void shouldCreateProduct() {
		var product = ProductDto.builder()
				.name("Nokia")
				.code(10)
				.description("Mobile phone")
				.price(BigDecimal.valueOf(500))
				.build();

		RestAssured
			.given()
				.contentType("application/json")
				.body(product)
			.when()
				.post("/api/v1/products")
			.then()
				.statusCode(201)
				.body("id", Matchers.notNullValue())
				.body("code", Matchers.equalTo(10))
				.body("name", Matchers.equalTo("Nokia"))
				.body("description", Matchers.equalTo("Mobile phone"))
				.body("price", Matchers.equalTo(500));
	}

	@Test
	@Order(2)
	void shouldGetAllProducts() {
		RestAssured
			.when()
				.get("/api/v1/products")
			.then()
				.statusCode(200)
				.body("size()", Matchers.equalTo(1));
	}

	@Test
	@Order(3)
	void givenNotExistingCode_whenCallFindByCode_shouldReturnStatus404AndEmptyBody() {
		RestAssured
			.given()
				.param("code", 100)
			.when()
				.get("/api/v1/products/filter")
			.then()
				.statusCode(404)
				.body(CoreMatchers.equalTo(""));
	}
	
	@Test
	@Order(4)
	void givenExistingCode_whenCallFindByCode_shouldReturnStatus200AndProductInBody() {
		RestAssured
			.given()
				.param("code", 10)
			.when()
				.get("/api/v1/products/filter")
			.then()
				.statusCode(200)
				.body("id", Matchers.notNullValue())
				.body("code", Matchers.equalTo(10))
				.body("name", Matchers.equalTo("Nokia"))
				.body("description", Matchers.equalTo("Mobile phone"))
				.body("price", Matchers.equalTo(500));
	}
	
}
