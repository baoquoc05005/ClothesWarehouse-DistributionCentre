package com.example.clotheswarehouse.config;

import com.example.clotheswarehouse.model.Brand;
import com.example.clotheswarehouse.model.Item;
import com.example.clotheswarehouse.model.Role;
import com.example.clotheswarehouse.model.User;
import com.example.clotheswarehouse.repository.ItemRepository;
import com.example.clotheswarehouse.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;

@Configuration
public class DatabaseInitializer {

    @Bean
    public CommandLineRunner initDatabase(ItemRepository itemRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Check if item database is empty before adding sample data
            if (itemRepository.count() == 0) {
                // Add sample items
                itemRepository.save(new Item("Luxury T-Shirt", Brand.BALENCIAGA, 2022, new BigDecimal("1500.00")));
                itemRepository.save(new Item("Designer Jeans", Brand.DIOR, 2022, new BigDecimal("2200.50")));
                itemRepository.save(new Item("Premium Jacket", Brand.STONE_ISLAND, 2023, new BigDecimal("3100.75")));
                itemRepository.save(new Item("Casual Shirt", Brand.BALENCIAGA, 2023, new BigDecimal("1800.25")));
                itemRepository.save(new Item("Winter Coat", Brand.DIOR, 2022, new BigDecimal("4500.00")));
                itemRepository.save(new Item("Summer Collection", Brand.STONE_ISLAND, 2022, new BigDecimal("2700.50")));
                
                System.out.println("Database has been populated with sample items!");
            }
            
            // Check if user database is empty before adding sample users
            if (userRepository.count() == 0) {
                // Create admin user
                User adminUser = new User("admin", passwordEncoder.encode("admin123"), "Admin", "User", "admin@warehouse.com");
                adminUser.addRole(Role.ROLE_ADMIN);
                userRepository.save(adminUser);
                
                // Create warehouse employee user
                User employeeUser = new User("employee", passwordEncoder.encode("employee123"), "Employee", "User", "employee@warehouse.com");
                employeeUser.addRole(Role.ROLE_WAREHOUSE_EMPLOYEE);
                userRepository.save(employeeUser);
                
                // Create regular user
                User regularUser = new User("user", passwordEncoder.encode("user123"), "Regular", "User", "user@example.com");
                regularUser.addRole(Role.ROLE_USER);
                userRepository.save(regularUser);
                
                System.out.println("Database has been populated with sample users!");
            }
        };
    }
}
