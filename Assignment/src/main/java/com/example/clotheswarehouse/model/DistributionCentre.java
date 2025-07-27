package com.example.clotheswarehouse.model;

import java.util.ArrayList;
import java.util.List;

public class DistributionCentre {
    private Long id;
    private String name;
    private Double latitude;
    private Double longitude;
    private List<Item> items = new ArrayList<>();
    
    public DistributionCentre() {
    }
    
    public DistributionCentre(Long id, String name, Double latitude, Double longitude, List<Item> items) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.items = items != null ? items : new ArrayList<>();
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Double getLatitude() {
        return latitude;
    }
    
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    
    public Double getLongitude() {
        return longitude;
    }
    
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    
    public List<Item> getItems() {
        return items;
    }
    
    public void setItems(List<Item> items) {
        this.items = items;
    }
}
