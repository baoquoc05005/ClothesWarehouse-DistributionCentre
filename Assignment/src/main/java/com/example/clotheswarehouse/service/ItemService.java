package com.example.clotheswarehouse.service;

import com.example.clotheswarehouse.model.Brand;
import com.example.clotheswarehouse.model.Item;
import com.example.clotheswarehouse.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> findAllItems() {
        return itemRepository.findAll();
    }

    public List<Item> findAllItemsSortedByBrand() {
        return itemRepository.findAll(Sort.by(Sort.Direction.ASC, "brand"));
    }
    
    public List<Item> findAllItemsSortedByPriceAsc() {
        return itemRepository.findAll(Sort.by(Sort.Direction.ASC, "price"));
    }
    
    public List<Item> findAllItemsSortedByPriceDesc() {
        return itemRepository.findAll(Sort.by(Sort.Direction.DESC, "price"));
    }

    public List<Item> findByBrandAndYear(String brand, int year) {
        return itemRepository.findByBrandAndYear(brand, year);
    }
    
    public List<Item> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return itemRepository.findByPriceRange(minPrice, maxPrice);
    }
    
    public List<Item> findByBrandAndPriceRange(String brand, BigDecimal minPrice, BigDecimal maxPrice) {
        return itemRepository.findByBrandAndPriceRange(brand, minPrice, maxPrice);
    }
    
    public Page<Item> findPaginated(int page, int size) {
        return itemRepository.findAll(PageRequest.of(page, size));
    }

    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    public Optional<Item> findItemById(Long id) {
        return itemRepository.findById(id);
    }

    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }
    
    public Item findByBrandAndName(String brandName, String name) {
        try {
            Brand brand = Brand.valueOf(brandName);
            List<Item> items = itemRepository.findAll();
            return items.stream()
                    .filter(item -> item.getBrand() == brand && item.getName().equals(name))
                    .findFirst()
                    .orElse(null);
        } catch (IllegalArgumentException e) {
            // Invalid brand name
            return null;
        }
    }
    
    public Item save(Item item) {
        return itemRepository.save(item);
    }
}
