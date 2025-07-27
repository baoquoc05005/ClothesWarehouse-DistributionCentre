package com.example.clotheswarehouse.repository;

import com.example.clotheswarehouse.model.Brand;
import com.example.clotheswarehouse.model.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class ItemRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    void findByBrandAndYear() {
        // Create test items
        Item item1 = new Item("Test T-Shirt", Brand.BALENCIAGA, 2023, new BigDecimal("1500.0"));
        Item item2 = new Item("Test Hoodie", Brand.BALENCIAGA, 2022, new BigDecimal("2000.0"));
        Item item3 = new Item("Test Jacket", Brand.DIOR, 2023, new BigDecimal("3000.0"));
        
        entityManager.persist(item1);
        entityManager.persist(item2);
        entityManager.persist(item3);
        entityManager.flush();

        // Test the query
        List<Item> foundItems = itemRepository.findByBrandAndYear("BALENCIAGA", 2023);
        
        assertEquals(1, foundItems.size());
        assertEquals("Test T-Shirt", foundItems.get(0).getName());
    }

    @Test
    void findByPriceRange() {
        // Create test items
        Item item1 = new Item("Budget Item", Brand.STONE_ISLAND, 2022, new BigDecimal("1200.0"));
        Item item2 = new Item("Mid-range Item", Brand.BALENCIAGA, 2023, new BigDecimal("2500.0"));
        Item item3 = new Item("Expensive Item", Brand.DIOR, 2023, new BigDecimal("5000.0"));
        
        entityManager.persist(item1);
        entityManager.persist(item2);
        entityManager.persist(item3);
        entityManager.flush();

        // Test the query
        List<Item> foundItems = itemRepository.findByPriceRange(
            new BigDecimal("2000.0"), new BigDecimal("3000.0"));
        
        assertEquals(1, foundItems.size());
        assertEquals("Mid-range Item", foundItems.get(0).getName());
    }

    @Test
    void findByBrandAndPriceRange() {
        // Create test items
        Item item1 = new Item("Budget Balenciaga", Brand.BALENCIAGA, 2022, new BigDecimal("1800.0"));
        Item item2 = new Item("Expensive Balenciaga", Brand.BALENCIAGA, 2023, new BigDecimal("4000.0"));
        Item item3 = new Item("Mid-range Dior", Brand.DIOR, 2023, new BigDecimal("2500.0"));
        
        entityManager.persist(item1);
        entityManager.persist(item2);
        entityManager.persist(item3);
        entityManager.flush();

        // Test the query
        List<Item> foundItems = itemRepository.findByBrandAndPriceRange(
            "BALENCIAGA", new BigDecimal("3000.0"), new BigDecimal("5000.0"));
        
        assertEquals(1, foundItems.size());
        assertEquals("Expensive Balenciaga", foundItems.get(0).getName());
    }
}
