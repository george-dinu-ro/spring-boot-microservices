package my.work.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.work.repository.InventoryRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

	private final InventoryRepository inventoryRepository;

	public boolean isInStock(Integer code, Integer quantity) {
		return inventoryRepository.existsByCodeAndQuantityIsGreaterThanEqual(code, quantity);
	}
}
