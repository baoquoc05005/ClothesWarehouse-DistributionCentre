package com.example.clotheswarehouse.controller;

import com.example.clotheswarehouse.model.Brand;
import com.example.clotheswarehouse.model.DistributionCentre;
import com.example.clotheswarehouse.service.DistributionCentreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/distribution-centres")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class DistributionCentreController {

    private final DistributionCentreService distributionCentreService;

    @Autowired
    public DistributionCentreController(DistributionCentreService distributionCentreService) {
        this.distributionCentreService = distributionCentreService;
    }

    @GetMapping
    public String listDistributionCentres(Model model) {
        List<DistributionCentre> centres = distributionCentreService.getAllDistributionCentres();
        model.addAttribute("centres", centres);
        model.addAttribute("brands", Brand.values());
        return "admin/distribution-centres";
    }

    @GetMapping("/request-item")
    public String showRequestItemForm(Model model) {
        model.addAttribute("brands", Brand.values());
        return "admin/request-item";
    }

    @PostMapping("/request-item")
    public String requestItem(
            @RequestParam("brand") String brand,
            @RequestParam("name") String name,
            @RequestParam("quantity") int quantity,
            RedirectAttributes redirectAttributes) {
        
        boolean success = distributionCentreService.requestItem(brand, name, quantity);
        
        if (success) {
            redirectAttributes.addFlashAttribute("success", 
                    String.format("Successfully requested %d units of %s %s from the closest distribution centre.", 
                            quantity, brand, name));
        } else {
            redirectAttributes.addFlashAttribute("error", 
                    String.format("Failed to request %s %s. No distribution centre has enough stock available.", 
                            brand, name));
        }
        
        return "redirect:/admin/distribution-centres";
    }
}
