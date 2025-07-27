package com.example.clotheswarehouse.controller;

import com.example.clotheswarehouse.model.Item;
import com.example.clotheswarehouse.model.Brand;
import com.example.clotheswarehouse.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/add")
    public String showAddItemForm(Model model) {
        model.addAttribute("item", new Item());
        model.addAttribute("brands", Brand.values());
        return "addItem";
    }

    @PostMapping("/add")
    public String addItem(@Valid @ModelAttribute Item item, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("brands", Brand.values());
            return "addItem";
        }
        itemService.saveItem(item);
        return "redirect:/items/list";
    }

    @GetMapping("/list")
    public String listItems(
            @RequestParam(defaultValue = "0") int page, 
            @RequestParam(defaultValue = "5") int size,
            Model model) {
        
        Page<Item> pageItems = itemService.findPaginated(page, size);
        List<Item> items = pageItems.getContent();
        
        model.addAttribute("items", items);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pageItems.getTotalPages());
        model.addAttribute("totalItems", pageItems.getTotalElements());
        
        return "itemList";
    }

    @GetMapping("/filter")
    public String filterItems(@RequestParam String brand, @RequestParam int year, Model model) {
        List<Item> items = itemService.findByBrandAndYear(brand, year);
        model.addAttribute("items", items);
        return "itemList";
    }

    @GetMapping("/sort")
    public String listItemsSortedByBrand(Model model) {
        List<Item> items = itemService.findAllItemsSortedByBrand();
        model.addAttribute("items", items);
        model.addAttribute("sortType", "brand");
        return "itemList";
    }
    
    @GetMapping("/sort-price-asc")
    public String listItemsSortedByPriceAsc(Model model) {
        List<Item> items = itemService.findAllItemsSortedByPriceAsc();
        model.addAttribute("items", items);
        model.addAttribute("sortType", "price-asc");
        return "itemList";
    }
    
    @GetMapping("/sort-price-desc")
    public String listItemsSortedByPriceDesc(Model model) {
        List<Item> items = itemService.findAllItemsSortedByPriceDesc();
        model.addAttribute("items", items);
        model.addAttribute("sortType", "price-desc");
        return "itemList";
    }
    
    // Delete functionality moved to AdminController to restrict to admin role only
    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Item item = itemService.findItemById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid item ID: " + id));
        model.addAttribute("item", item);
        model.addAttribute("brands", Brand.values());
        return "editItem";
    }
    
    @PostMapping("/edit/{id}")
    public String updateItem(@PathVariable Long id, @Valid @ModelAttribute("item") Item item, 
                           BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("brands", Brand.values());
            return "editItem";
        }
        
        itemService.saveItem(item);
        return "redirect:/items/list";
    }
    
    @GetMapping("/view/{id}")
    public String viewItemDetails(@PathVariable Long id, Model model) {
        Item item = itemService.findItemById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid item ID: " + id));
        model.addAttribute("item", item);
        return "viewItem";
    }
    
    @GetMapping("/price-filter")
    public String showPriceFilterForm(Model model) {
        model.addAttribute("brands", Brand.values());
        return "priceFilter";
    }
    
    @GetMapping("/filter-by-price")
    public String filterByPriceRange(
            @RequestParam(required = false) String brand,
            @RequestParam BigDecimal minPrice, 
            @RequestParam BigDecimal maxPrice, 
            Model model) {
        
        List<Item> items;
        if (brand != null && !brand.isEmpty() && !brand.equals("ALL")) {
            items = itemService.findByBrandAndPriceRange(brand, minPrice, maxPrice);
        } else {
            items = itemService.findByPriceRange(minPrice, maxPrice);
        }
        
        model.addAttribute("items", items);
        return "itemList";
    }
}
