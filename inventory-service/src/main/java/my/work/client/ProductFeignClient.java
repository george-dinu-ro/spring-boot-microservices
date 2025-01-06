package my.work.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import my.work.dto.ProductDto;

@FeignClient(value = "productClient", url = "${productservice.url}")
public interface ProductFeignClient {

	@GetMapping(value = "/api/v1/products/filter")
	ProductDto findByCode(@RequestParam int code);

}
