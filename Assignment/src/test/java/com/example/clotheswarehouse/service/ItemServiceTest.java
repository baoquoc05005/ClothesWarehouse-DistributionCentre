package com.example.clotheswarehouse.service;

import com.example.clotheswarehouse.model.Brand;
import com.example.clotheswarehouse.model.Item;
import com.example.clotheswarehouse.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    private Item testItem1;
    private Item testItem2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        testItem1 = new Item("Test T-Shirt", Brand.BALENCIAGA, 2023, new BigDecimal("1500.0"));
        testItem1.setId(1L);
        
        testItem2 = new Item("Test Hoodie", Brand.DIOR, 2022, new BigDecimal("2000.0"));
        testItem2.setId(2L);
    }

    @Test
    void findAllItems() {
        when(itemRepository.findAll()).thenReturn(Arrays.asList(testItem1, testItem2));
        
        List<Item> items = itemService.findAllItems();
        
        assertEquals(2, items.size());
        verify(itemRepository, times(1)).findAll();
    }

    @Test
    void findItemById() {
        when(itemRepository.findById(1L)).thenReturn(Optional.of(testItem1));
        
        Optional<Item> foundItem = itemService.findItemById(1L);
        
        assertTrue(foundItem.isPresent());
        assertEquals("Test T-Shirt", foundItem.get().getName());
        verify(itemRepository, times(1)).findById(1L);
    }

    @Test
    void saveItem() {
        when(itemRepository.save(any(Item.class))).thenReturn(testItem1);
        
        Item savedItem = itemService.saveItem(testItem1);
        
        assertEquals(testItem1.getName(), savedItem.getName());
        verify(itemRepository, times(1)).save(testItem1);
    }

    @Test
    void findAllItemsSortedByBrand() {
        when(itemRepository.findAll(any(Sort.class))).thenReturn(Arrays.asList(testItem1, testItem2));
        
        List<Item> items = itemService.findAllItemsSortedByBrand();
        
        assertEquals(2, items.size());
        verify(itemRepository, times(1)).findAll(any(Sort.class));
    }

    @Test
    void findByBrandAndYear() {
        when(itemRepository.findByBrandAndYear("BALENCIAGA", 2023))
                .thenReturn(Arrays.asList(testItem1));
        
        List<Item> items = itemService.findByBrandAndYear("BALENCIAGA", 2023);
        
        assertEquals(1, items.size());
        assertEquals("Test T-Shirt", items.get(0).getName());
        verify(itemRepository, times(1)).findByBrandAndYear("BALENCIAGA", 2023);
    }
}
