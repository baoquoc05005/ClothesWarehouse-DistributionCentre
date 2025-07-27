package com.example.clotheswarehouse.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Enumerated(EnumType.STRING)
    private Brand brand;

    @Min(2022)
    private int yearOfCreation;

    @DecimalMin(value = "1000.0", inclusive = false)
    private BigDecimal price;
    
    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer quantity = 0;

    public Item() {}

    public Item(String name, Brand brand, int yearOfCreation, double price) {
        this.name = name;
        this.brand = brand;
        this.yearOfCreation = yearOfCreation;
        this.price = BigDecimal.valueOf(price);
        this.quantity = 0;
    }
    
    public Item(String name, Brand brand, int yearOfCreation, BigDecimal price) {
        this.name = name;
        this.brand = brand;
        this.yearOfCreation = yearOfCreation;
        this.price = price;
        this.quantity = 0;
    }
    
    public Item(String name, Brand brand, int yearOfCreation, BigDecimal price, Integer quantity) {
        this.name = name;
        this.brand = brand;
        this.yearOfCreation = yearOfCreation;
        this.price = price;
        this.quantity = quantity;
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

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public int getYearOfCreation() {
        return yearOfCreation;
    }

    public void setYearOfCreation(int yearOfCreation) {
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
}
