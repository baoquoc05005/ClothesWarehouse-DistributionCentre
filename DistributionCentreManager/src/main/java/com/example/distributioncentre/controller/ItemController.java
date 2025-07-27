package com.example.distributioncentre.controller;

import com.example.distributioncentre.model.Item;
import com.example.distributioncentre.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public ResponseEntity<List<Item>> getAllItems() {
        List<Item> items = itemService.findAllItems();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        Optional<Item> item = itemService.findItemById(id);
        return item.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search/brand")
    public ResponseEntity<List<Item>> getItemsByBrand(@RequestParam String brand) {
        List<Item> items = itemService.findItemsByBrand(brand);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/search/name")
    public ResponseEntity<List<Item>> getItemsByName(@RequestParam String name) {
        List<Item> items = itemService.findItemsByName(name);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Item>> getAvailableItemsByBrandAndName(
            @RequestParam String brand,
            @RequestParam String name) {
        
        List<Item> items = itemService.findAvailableItemsByBrandAndName(brand, name);
        return ResponseEntity.ok(items);
    }

    @PostMapping("/request")
    public ResponseEntity<String> requestItem(
            @RequestParam String brand,
            @RequestParam String name,
            @RequestParam int quantity) {
        
        boolean success = itemService.requestItem(brand, name, quantity);
        
        if (success) {
            return ResponseEntity.ok("Item requested successfully");
        } else {
            return ResponseEntity.badRequest().body("Not enough stock available");
        }
    }
}
