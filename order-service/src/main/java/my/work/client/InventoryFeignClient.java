package my.work.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import my.work.dto.StatusDto;

@FeignClient(value = "inventoryClient", url = "${inventoryservice.url}")
public interface InventoryFeignClient {

	@GetMapping(value = "/api/v1/inventories/status")
	StatusDto getStatus(@RequestParam int code, @RequestParam int quantity);

}
