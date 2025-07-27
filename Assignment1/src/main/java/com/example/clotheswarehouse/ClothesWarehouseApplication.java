package com.example.clotheswarehouse;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.clotheswarehouse.model.Brand;
import com.example.clotheswarehouse.model.Item;
import com.example.clotheswarehouse.repository.ItemRepository;

@SpringBootApplication
public class ClothesWarehouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClothesWarehouseApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(ItemRepository itemRepository) {
        return args -> {
            // Add sample data with a variety of prices and brands for testing filters and pagination
            itemRepository.save(new Item("T-Shirt", Brand.BALENCIAGA, 2023, 1500.0));
            itemRepository.save(new Item("Hoodie", Brand.STONE_ISLAND, 2022, 1200.0));
            itemRepository.save(new Item("Jacket", Brand.DIOR, 2023, 2500.0));
            itemRepository.save(new Item("Jeans", Brand.BALENCIAGA, 2023, 1800.0));
            itemRepository.save(new Item("Cap", Brand.STONE_ISLAND, 2022, 1050.0));
            itemRepository.save(new Item("Sneakers", Brand.DIOR, 2022, 3500.0));
            itemRepository.save(new Item("Sweater", Brand.BALENCIAGA, 2022, 2200.0));
            itemRepository.save(new Item("Scarf", Brand.DIOR, 2023, 1350.0));
            itemRepository.save(new Item("Backpack", Brand.STONE_ISLAND, 2023, 2100.0));
            itemRepository.save(new Item("Gloves", Brand.BALENCIAGA, 2022, 1150.0));
            itemRepository.save(new Item("Sunglasses", Brand.DIOR, 2023, 1650.0));
            itemRepository.save(new Item("Belt", Brand.STONE_ISLAND, 2022, 1450.0));
        };
    }
}
