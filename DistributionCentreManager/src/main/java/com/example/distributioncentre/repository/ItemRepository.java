package com.example.distributioncentre.repository;

import com.example.distributioncentre.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    
    List<Item> findByBrand(String brand);
    
    List<Item> findByName(String name);
    
    @Query("SELECT i FROM Item i WHERE i.brand = :brand AND i.name = :name AND i.quantity > 0")
    List<Item> findAvailableItemsByBrandAndName(@Param("brand") String brand, @Param("name") String name);
    
    @Query("SELECT i FROM Item i WHERE i.distributionCentre.id = :centreId")
    List<Item> findByDistributionCentreId(@Param("centreId") Long centreId);
}
