package my.work.config;

import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class Route {

	@Bean
	RouterFunction<ServerResponse> productServiceRoute() {
		return GatewayRouterFunctions.route("product-service")
				.route(RequestPredicates.path("/api/v1/products"), HandlerFunctions.http("http://localhost:8080"))
				.build();
	}

	@Bean
	RouterFunction<ServerResponse> orderServiceRoute() {
		return GatewayRouterFunctions.route("order-service")
				.route(RequestPredicates.path("/api/v1/orders"), HandlerFunctions.http("http://localhost:8081"))
				.build();
	}

	@Bean
	RouterFunction<ServerResponse> inventoryServiceRoute() {
		return GatewayRouterFunctions.route("inventory-service")
				.route(RequestPredicates.path("/api/v1/inventories"), HandlerFunctions.http("http://localhost:8082"))
				.build();
	}

}
