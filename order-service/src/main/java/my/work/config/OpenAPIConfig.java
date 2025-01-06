package my.work.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenAPIConfig {

	@Bean
	OpenAPI orderServiceApi() {
		return new OpenAPI().info(new Info()
				.title("Order Service API")
				.description("REST API for Order Service")
				.version("0.0.1"));
	}
}
