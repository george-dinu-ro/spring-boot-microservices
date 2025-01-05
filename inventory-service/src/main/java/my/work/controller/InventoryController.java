package my.work.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import my.work.service.InventoryService;

@RequestMapping("/api/v1/inventories")
@RestController
@RequiredArgsConstructor
public class InventoryController {

	private final InventoryService inventoryService;

	@GetMapping("/inStock")
	@ResponseStatus(code = HttpStatus.OK)
	boolean isInStock(@RequestParam Integer code, @RequestParam Integer quantity) {
		return inventoryService.isInStock(code, quantity);
	}

}
