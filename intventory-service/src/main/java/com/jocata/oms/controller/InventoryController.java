package com.jocata.oms.controller;

import com.jocata.oms.datamodel.um.entity.InventoryEntity;
import com.jocata.oms.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// Controller
@RestController
@RequestMapping("/warehouse")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    public ResponseEntity<List<InventoryEntity>> getAllInventory() {
        return ResponseEntity.ok(inventoryService.getAllInventory());
    }

    @PutMapping("/reserve")
    public ResponseEntity<InventoryEntity> reserveStock(@RequestParam Integer productId, @RequestParam Integer quantity) {
        return ResponseEntity.ok(inventoryService.reserveStock(productId, quantity));
    }

    @PutMapping("/release")
    public ResponseEntity<InventoryEntity> releaseStock(@RequestParam Integer productId, @RequestParam Integer quantity) {
        return ResponseEntity.ok(inventoryService.releaseStock(productId, quantity));
    }

    @PutMapping("/update")
    public ResponseEntity<InventoryEntity> updateStock(@RequestParam Integer productId, @RequestParam Integer quantity) {
        return ResponseEntity.ok(inventoryService.updateStock(productId, quantity));
    }
    @PutMapping("/updateStock")
    public ResponseEntity<InventoryEntity> update(@RequestParam Integer productId , @RequestParam Integer quantity) {
        return ResponseEntity.ok(inventoryService.update(productId , quantity));
    }
}

