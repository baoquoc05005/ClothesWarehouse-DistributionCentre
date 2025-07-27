package com.example.clotheswarehouse.controller;

import com.example.clotheswarehouse.model.Item;
import com.example.clotheswarehouse.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {

    private final ItemService itemService;

    @Autowired
    public AdminController(ItemService itemService) {
        this.itemService = itemService;
    }
    
    @GetMapping
    public String adminDashboard() {
        return "redirect:/admin/items";
    }

    @GetMapping("/items")
    public String manageItems(Model model) {
        List<Item> items = itemService.findAllItems();
        model.addAttribute("items", items);
        return "admin/manageItems";
    }

    @PostMapping("/items/delete/{id}")
    public String deleteItem(@PathVariable("id") Long id) {
        itemService.deleteItem(id);
        return "redirect:/admin/items";
    }
}
