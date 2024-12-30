package my.work;

import java.math.BigDecimal;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MongoDBContainer;

import io.restassured.RestAssured;
import my.work.dto.ProductDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

	@ServiceConnection
	static MongoDBContainer mongoDBContainer= new MongoDBContainer("mongo:latest");

	static {
		mongoDBContainer.start();
	}

	@LocalServerPort
	private int port;

	@BeforeEach
	void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	@Test
	void shouldCreateProduct() {
		var product = ProductDto.builder()
				.name("Product 1")
				.description("Description 1")
				.price(BigDecimal.valueOf(10.0))
				.build();
		
		RestAssured
			.given()
				.contentType("application/json")
				.body(product)
				.post("/api/v1/products")
			.then()
				.statusCode(201)
				.body("id", Matchers.notNullValue())
				.body("name", Matchers.equalTo("Product 1"))
				.body("description", Matchers.equalTo("Description 1"))
				.body("price", Matchers.equalTo(10.0F));
	}
}
