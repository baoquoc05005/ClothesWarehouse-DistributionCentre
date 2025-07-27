package com.example.distributioncentre.service;

import com.example.distributioncentre.model.DistributionCentre;
import com.example.distributioncentre.model.Item;
import com.example.distributioncentre.repository.DistributionCentreRepository;
import com.example.distributioncentre.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final DistributionCentreRepository distributionCentreRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository, DistributionCentreRepository distributionCentreRepository) {
        this.itemRepository = itemRepository;
        this.distributionCentreRepository = distributionCentreRepository;
    }

    public List<Item> findAllItems() {
        return itemRepository.findAll();
    }

    public Optional<Item> findItemById(Long id) {
        return itemRepository.findById(id);
    }

    public List<Item> findItemsByBrand(String brand) {
        return itemRepository.findByBrand(brand);
    }

    public List<Item> findItemsByName(String name) {
        return itemRepository.findByName(name);
    }

    public List<Item> findAvailableItemsByBrandAndName(String brand, String name) {
        return itemRepository.findAvailableItemsByBrandAndName(brand, name);
    }

    public List<Item> findItemsByDistributionCentreId(Long centreId) {
        return itemRepository.findByDistributionCentreId(centreId);
    }

    @Transactional
    public Item addItemToDistributionCentre(Long centreId, Item item) {
        DistributionCentre centre = distributionCentreRepository.findById(centreId)
                .orElseThrow(() -> new RuntimeException("Distribution Centre not found"));
        
        centre.addItem(item);
        return itemRepository.save(item);
    }

    @Transactional
    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }

    @Transactional
    public boolean requestItem(String brand, String name, int quantity) {
        List<Item> availableItems = findAvailableItemsByBrandAndName(brand, name);
        
        for (Item item : availableItems) {
            if (item.getQuantity() >= quantity) {
                item.setQuantity(item.getQuantity() - quantity);
                itemRepository.save(item);
                return true;
            }
        }
        
        return false;
    }
}
