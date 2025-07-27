package com.example.distributioncentre.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Brand is required")
    private String brand;

    @NotNull(message = "Year of creation is required")
    @Min(value = 1900, message = "Year must be at least 1900")
    private Integer yearOfCreation;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private BigDecimal price;

    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer quantity;

    @ManyToOne
    @JsonBackReference
    private DistributionCentre distributionCentre;
    
    // Default constructor
    public Item() {
    }
    
    // All-args constructor
    public Item(Long id, String name, String brand, Integer yearOfCreation, BigDecimal price, Integer quantity, DistributionCentre distributionCentre) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.yearOfCreation = yearOfCreation;
        this.price = price;
        this.quantity = quantity;
        this.distributionCentre = distributionCentre;
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
    
    public String getBrand() {
        return brand;
    }
    
    public void setBrand(String brand) {
        this.brand = brand;
    }
    
    public Integer getYearOfCreation() {
        return yearOfCreation;
    }
    
    public void setYearOfCreation(Integer yearOfCreation) {
        this.yearOfCreation = yearOfCreation;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public Integer getQuantity() {
        return quantity;
    }
    
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    public DistributionCentre getDistributionCentre() {
        return distributionCentre;
    }
    
    public void setDistributionCentre(DistributionCentre distributionCentre) {
        this.distributionCentre = distributionCentre;
    }
    
    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(id, item.id) &&
               Objects.equals(name, item.name) &&
               Objects.equals(brand, item.brand) &&
               Objects.equals(yearOfCreation, item.yearOfCreation) &&
               Objects.equals(price, item.price) &&
               Objects.equals(quantity, item.quantity);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, name, brand, yearOfCreation, price, quantity);
    }
    
    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", yearOfCreation=" + yearOfCreation +
                ", price=" + price +
                ", quantity=" + quantity +
                "}"
                ;
    }
}
