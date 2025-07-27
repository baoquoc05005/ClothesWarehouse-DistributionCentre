package com.example.clotheswarehouse.service;

import com.example.clotheswarehouse.model.Brand;
import com.example.clotheswarehouse.model.DistributionCentre;
import com.example.clotheswarehouse.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.List;

@Service
public class DistributionCentreService {

    private final RestTemplate restTemplate;
    private final String apiUrl;
    private final String username;
    private final String password;
    private final ItemService itemService;

    @Autowired
    public DistributionCentreService(
            RestTemplate restTemplate,
            @Value("${distribution.centre.api.url:http://localhost:8081/api}") String apiUrl,
            @Value("${distribution.centre.api.username:api-user}") String username,
            @Value("${distribution.centre.api.password:api-password}") String password,
            ItemService itemService) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
        this.username = username;
        this.password = password;
        this.itemService = itemService;
    }

    /**
     * Get all distribution centres
     * @return List of distribution centres
     */
    public List<DistributionCentre> getAllDistributionCentres() {
        HttpHeaders headers = createHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        
        try {
            ResponseEntity<List<DistributionCentre>> response = restTemplate.exchange(
                    apiUrl + "/distribution-centres",
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<List<DistributionCentre>>() {}
            );
            
            return response.getBody();
        } catch (Exception e) {
            // Log the exception for debugging
            System.err.println("Error getting all distribution centres: " + e.getMessage());
            e.printStackTrace();
            return List.of(); // Return empty list on error
        }
    }

    /**
     * Find the closest distribution centre with the requested item in stock
     * @param brand Item brand
     * @param name Item name
     * @param quantity Quantity needed
     * @param warehouseLat Warehouse latitude
     * @param warehouseLon Warehouse longitude
     * @return The closest distribution centre with the item in stock, or null if none found
     */
    public DistributionCentre findClosestDistributionCentreWithItem(
            String brand, String name, int quantity, double warehouseLat, double warehouseLon) {
        
        HttpHeaders headers = createHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        
        String url = String.format(
                "%s/distribution-centres/closest?brand=%s&name=%s&quantity=%d&warehouseLat=%f&warehouseLon=%f",
                apiUrl, brand, name, quantity, warehouseLat, warehouseLon
        );
        
        System.out.println("Requesting from URL: " + url);
        
        try {
            ResponseEntity<DistributionCentre> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    DistributionCentre.class
            );
            
            return response.getBody();
        } catch (Exception e) {
            // Log the exception for debugging
            System.err.println("Error finding closest distribution centre: " + e.getMessage());
            e.printStackTrace();
            // No distribution centre found with the requested item
            return null;
        }
    }

    /**
     * Request an item from a distribution centre
     * @param brand Item brand
     * @param name Item name
     * @param quantity Quantity needed
     * @return true if the request was successful, false otherwise
     */
    public boolean requestItem(String brand, String name, int quantity) {
        // Fixed warehouse location in GTA (Toronto)
        double warehouseLat = 43.6532;
        double warehouseLon = -79.3832;
        
        // Find the closest distribution centre with the item in stock
        DistributionCentre centre = findClosestDistributionCentreWithItem(
                brand, name, quantity, warehouseLat, warehouseLon);
        
        if (centre == null) {
            return false;
        }
        
        // Request the item from the distribution centre
        HttpHeaders headers = createHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        
        String url = String.format(
                "%s/items/request?brand=%s&name=%s&quantity=%d",
                apiUrl, brand, name, quantity
        );
        
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    String.class
            );
            
            if (response.getStatusCode().is2xxSuccessful()) {
                // Update the warehouse stock
                Item item = itemService.findByBrandAndName(brand, name);
                if (item == null) {
                    // Create a new item if it doesn't exist in the warehouse
                    item = new Item();
                    item.setBrand(Brand.valueOf(brand));
                    item.setName(name);
                    item.setYearOfCreation(2023); // Default value
                    item.setPrice(centre.getItems().stream()
                            .filter(i -> i.getBrand().equals(brand) && i.getName().equals(name))
                            .findFirst()
                            .map(Item::getPrice)
                            .orElse(null));
                    item.setQuantity(quantity);
                } else {
                    // Update existing item quantity
                    item.setQuantity(item.getQuantity() + quantity);
                }
                
                itemService.save(item);
                return true;
            }
            
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Create HTTP headers with basic authentication
     * @return HTTP headers
     */
    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
        String authHeader = "Basic " + new String(encodedAuth);
        headers.set("Authorization", authHeader);
        return headers;
    }
}
