package com.example.distributioncentre.controller;

import com.example.distributioncentre.model.DistributionCentre;
import com.example.distributioncentre.model.Item;
import com.example.distributioncentre.service.DistributionCentreService;
import com.example.distributioncentre.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/distribution-centres")
public class DistributionCentreController {

    private final DistributionCentreService distributionCentreService;
    private final ItemService itemService;

    @Autowired
    public DistributionCentreController(DistributionCentreService distributionCentreService, ItemService itemService) {
        this.distributionCentreService = distributionCentreService;
        this.itemService = itemService;
    }

    @GetMapping
    public ResponseEntity<List<DistributionCentre>> getAllDistributionCentres() {
        List<DistributionCentre> centres = distributionCentreService.findAllDistributionCentres();
        return ResponseEntity.ok(centres);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DistributionCentre> getDistributionCentreById(@PathVariable Long id) {
        Optional<DistributionCentre> centre = distributionCentreService.findDistributionCentreById(id);
        return centre.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DistributionCentre> createDistributionCentre(@Valid @RequestBody DistributionCentre distributionCentre) {
        DistributionCentre savedCentre = distributionCentreService.saveDistributionCentre(distributionCentre);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCentre);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DistributionCentre> updateDistributionCentre(
            @PathVariable Long id, 
            @Valid @RequestBody DistributionCentre distributionCentre) {
        
        if (!distributionCentreService.findDistributionCentreById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        distributionCentre.setId(id);
        DistributionCentre updatedCentre = distributionCentreService.saveDistributionCentre(distributionCentre);
        return ResponseEntity.ok(updatedCentre);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDistributionCentre(@PathVariable Long id) {
        if (!distributionCentreService.findDistributionCentreById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        distributionCentreService.deleteDistributionCentre(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{centreId}/items")
    public ResponseEntity<List<Item>> getItemsByDistributionCentreId(@PathVariable Long centreId) {
        if (!distributionCentreService.findDistributionCentreById(centreId).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        List<Item> items = itemService.findItemsByDistributionCentreId(centreId);
        return ResponseEntity.ok(items);
    }

    @PostMapping("/{centreId}/items")
    public ResponseEntity<Item> addItemToDistributionCentre(
            @PathVariable Long centreId, 
            @Valid @RequestBody Item item) {
        
        if (!distributionCentreService.findDistributionCentreById(centreId).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        Item savedItem = itemService.addItemToDistributionCentre(centreId, item);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedItem);
    }

    @DeleteMapping("/{centreId}/items/{itemId}")
    public ResponseEntity<Void> deleteItemFromDistributionCentre(
            @PathVariable Long centreId, 
            @PathVariable Long itemId) {
        
        if (!distributionCentreService.findDistributionCentreById(centreId).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        Optional<Item> item = itemService.findItemById(itemId);
        if (!item.isPresent() || !item.get().getDistributionCentre().getId().equals(centreId)) {
            return ResponseEntity.notFound().build();
        }
        
        itemService.deleteItem(itemId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/closest")
    public ResponseEntity<DistributionCentre> findClosestDistributionCentreWithItem(
            @RequestParam String brand,
            @RequestParam String name,
            @RequestParam int quantity,
            @RequestParam double warehouseLat,
            @RequestParam double warehouseLon) {
        
        DistributionCentre centre = distributionCentreService.findClosestDistributionCentreWithItem(
                brand, name, quantity, warehouseLat, warehouseLon);
        
        if (centre == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(centre);
    }
}
