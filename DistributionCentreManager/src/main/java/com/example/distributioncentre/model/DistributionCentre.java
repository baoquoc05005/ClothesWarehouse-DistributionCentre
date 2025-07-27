package com.example.distributioncentre.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "distribution_centres")
public class DistributionCentre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Latitude is required")
    private Double latitude;

    @NotNull(message = "Longitude is required")
    private Double longitude;

    @OneToMany(mappedBy = "distributionCentre", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Item> items = new ArrayList<>();

    // Helper method to add an item to this distribution centre
    public void addItem(Item item) {
        items.add(item);
        item.setDistributionCentre(this);
    }

    // Helper method to remove an item from this distribution centre
    public void removeItem(Item item) {
        items.remove(item);
        item.setDistributionCentre(null);
    }
    
    // Default constructor
    public DistributionCentre() {
    }
    
    // All-args constructor
    public DistributionCentre(Long id, String name, Double latitude, Double longitude, List<Item> items) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.items = items;
    }
    
    // Getters and setters
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
    
    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DistributionCentre that = (DistributionCentre) o;
        return Objects.equals(id, that.id) &&
               Objects.equals(name, that.name) &&
               Objects.equals(latitude, that.latitude) &&
               Objects.equals(longitude, that.longitude);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, name, latitude, longitude);
    }
    
    @Override
    public String toString() {
        return "DistributionCentre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", itemsCount=" + (items != null ? items.size() : 0) +
                "}";
    }
}
