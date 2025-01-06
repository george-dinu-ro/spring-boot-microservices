package my.work.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenAPIConfig {

	@Bean
	OpenAPI inventoryServiceApi() {
		return new OpenAPI().info(new Info()
				.title("Inventory Service API")
				.description("REST API for Inventory Service")
				.version("0.0.1"));
	}
}
