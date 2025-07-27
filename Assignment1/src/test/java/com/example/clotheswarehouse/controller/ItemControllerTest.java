package com.example.clotheswarehouse.controller;

import com.example.clotheswarehouse.model.Brand;
import com.example.clotheswarehouse.model.Item;
import com.example.clotheswarehouse.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ItemControllerTest {

    @Mock
    private ItemService itemService;

    @InjectMocks
    private ItemController itemController;

    private MockMvc mockMvc;

    private Item testItem;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(itemController).build();

        testItem = new Item("Test T-Shirt", Brand.BALENCIAGA, 2023, new BigDecimal("1500.0"));
        testItem.setId(1L);
    }

    @Test
    void listItems() throws Exception {
        List<Item> items = new ArrayList<>();
        items.add(testItem);
        Page<Item> itemPage = new PageImpl<>(items);
        
        when(itemService.findPaginated(anyInt(), anyInt())).thenReturn(itemPage);
        
        mockMvc.perform(get("/items/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("itemList"))
                .andExpect(model().attributeExists("items"))
                .andExpect(model().attributeExists("currentPage"))
                .andExpect(model().attributeExists("totalPages"))
                .andExpect(model().attributeExists("totalItems"));
        
        verify(itemService, times(1)).findPaginated(anyInt(), anyInt());
    }

    @Test
    void sortItems() throws Exception {
        List<Item> items = new ArrayList<>();
        items.add(testItem);
        
        when(itemService.findAllItemsSortedByBrand()).thenReturn(items);
        
        mockMvc.perform(get("/items/sort"))
                .andExpect(status().isOk())
                .andExpect(view().name("itemList"))
                .andExpect(model().attributeExists("items"))
                .andExpect(model().attribute("sortType", "brand"));
        
        verify(itemService, times(1)).findAllItemsSortedByBrand();
    }

    @Test
    void viewItemDetails() throws Exception {
        when(itemService.findItemById(1L)).thenReturn(Optional.of(testItem));
        
        mockMvc.perform(get("/items/view/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("viewItem"))
                .andExpect(model().attributeExists("item"));
        
        verify(itemService, times(1)).findItemById(1L);
    }

    @Test
    void sortItemsByPriceAsc() throws Exception {
        List<Item> items = new ArrayList<>();
        items.add(testItem);
        
        when(itemService.findAllItemsSortedByPriceAsc()).thenReturn(items);
        
        mockMvc.perform(get("/items/sort-price-asc"))
                .andExpect(status().isOk())
                .andExpect(view().name("itemList"))
                .andExpect(model().attributeExists("items"))
                .andExpect(model().attribute("sortType", "price-asc"));
        
        verify(itemService, times(1)).findAllItemsSortedByPriceAsc();
    }
}
