package com.example.clotheswarehouse.repository;

import com.example.clotheswarehouse.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("SELECT i FROM Item i WHERE i.brand = :brand AND i.yearOfCreation = :year")
    List<Item> findByBrandAndYear(@Param("brand") String brand, @Param("year") int year);
    
    @Query("SELECT i FROM Item i WHERE i.price >= :minPrice AND i.price <= :maxPrice")
    List<Item> findByPriceRange(@Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice);
    
    @Query("SELECT i FROM Item i WHERE i.brand = :brand AND i.price >= :minPrice AND i.price <= :maxPrice")
    List<Item> findByBrandAndPriceRange(@Param("brand") String brand, @Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice);
}
