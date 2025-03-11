package com.jocata.oms.service;

import com.jocata.oms.datamodel.um.entity.InventoryEntity;
import com.jocata.oms.repo.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public List<InventoryEntity> getAllInventory() {
        return inventoryRepository.findAll();
    }

    public InventoryEntity reserveStock(Integer productId, Integer quantity) {
        InventoryEntity inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Product not found in inventory"));
        if (inventory.getStockQuantity() >= quantity) {
            inventory.setStockQuantity(inventory.getStockQuantity() - quantity);
            inventory.setReservedStock(inventory.getReservedStock() + quantity);
            return inventoryRepository.save(inventory);
        } else {
            throw new RuntimeException("Insufficient stock available");
        }
    }

    public InventoryEntity releaseStock(Integer productId, Integer quantity) {
        InventoryEntity inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Product not found in inventory"));
        if (inventory.getReservedStock() >= quantity) {
            inventory.setReservedStock(inventory.getReservedStock() - quantity);
            inventory.setStockQuantity(inventory.getStockQuantity() + quantity);
            return inventoryRepository.save(inventory);
        } else {
            throw new RuntimeException("Insufficient reserved stock to release");
        }
    }

    public InventoryEntity updateStock(Integer productId, Integer quantity) {
        InventoryEntity inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Product not found in inventory"));
        inventory.setStockQuantity(quantity);
        return inventoryRepository.save(inventory);
    }
}
