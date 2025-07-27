package com.example.distributioncentre.config;

import com.example.distributioncentre.model.DistributionCentre;
import com.example.distributioncentre.model.Item;
import com.example.distributioncentre.repository.DistributionCentreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final DistributionCentreRepository distributionCentreRepository;

    @Autowired
    public DataInitializer(DistributionCentreRepository distributionCentreRepository) {
        this.distributionCentreRepository = distributionCentreRepository;
    }

    @Override
    public void run(String... args) {
        // Create distribution centres in different GTA locations
        DistributionCentre scarborough = new DistributionCentre();
        scarborough.setName("Scarborough Distribution Centre");
        scarborough.setLatitude(43.7764);
        scarborough.setLongitude(-79.2318);

        DistributionCentre mississauga = new DistributionCentre();
        mississauga.setName("Mississauga Distribution Centre");
        mississauga.setLatitude(43.5890);
        mississauga.setLongitude(-79.6441);

        DistributionCentre vaughan = new DistributionCentre();
        vaughan.setName("Vaughan Distribution Centre");
        vaughan.setLatitude(43.8563);
        vaughan.setLongitude(-79.5085);

        DistributionCentre markham = new DistributionCentre();
        markham.setName("Markham Distribution Centre");
        markham.setLatitude(43.8561);
        markham.setLongitude(-79.3370);

        // Create items for Scarborough
        Item balenciagaShirtScarborough = new Item();
        balenciagaShirtScarborough.setName("T-Shirt");
        balenciagaShirtScarborough.setBrand("BALENCIAGA");
        balenciagaShirtScarborough.setPrice(new BigDecimal("299.99"));
        balenciagaShirtScarborough.setYearOfCreation(2023);
        balenciagaShirtScarborough.setQuantity(50);
        
        Item diorShoesScarborough = new Item();
        diorShoesScarborough.setName("Running Shoes");
        diorShoesScarborough.setBrand("DIOR");
        diorShoesScarborough.setPrice(new BigDecimal("899.99"));
        diorShoesScarborough.setYearOfCreation(2022);
        diorShoesScarborough.setQuantity(30);

        // Create items for Mississauga
        Item stoneIslandShirtMississauga = new Item();
        stoneIslandShirtMississauga.setName("T-Shirt");
        stoneIslandShirtMississauga.setBrand("STONE_ISLAND");
        stoneIslandShirtMississauga.setPrice(new BigDecimal("224.99"));
        stoneIslandShirtMississauga.setYearOfCreation(2023);
        stoneIslandShirtMississauga.setQuantity(40);
        
        Item balenciagaShoeMississauga = new Item();
        balenciagaShoeMississauga.setName("Running Shoes");
        balenciagaShoeMississauga.setBrand("BALENCIAGA");
        balenciagaShoeMississauga.setPrice(new BigDecimal("1099.99"));
        balenciagaShoeMississauga.setYearOfCreation(2023);
        balenciagaShoeMississauga.setQuantity(25);

        // Create items for Vaughan
        Item diorShirtVaughan = new Item();
        diorShirtVaughan.setName("T-Shirt");
        diorShirtVaughan.setBrand("DIOR");
        diorShirtVaughan.setPrice(new BigDecimal("327.99"));
        diorShirtVaughan.setYearOfCreation(2022);
        diorShirtVaughan.setQuantity(35);
        
        Item stoneIslandShirtVaughan = new Item();
        stoneIslandShirtVaughan.setName("T-Shirt");
        stoneIslandShirtVaughan.setBrand("STONE_ISLAND");
        stoneIslandShirtVaughan.setPrice(new BigDecimal("229.99"));
        stoneIslandShirtVaughan.setYearOfCreation(2023);
        stoneIslandShirtVaughan.setQuantity(20);

        // Create items for Markham
        Item balenciagaShirtMarkham = new Item();
        balenciagaShirtMarkham.setName("T-Shirt");
        balenciagaShirtMarkham.setBrand("BALENCIAGA");
        balenciagaShirtMarkham.setPrice(new BigDecimal("334.99"));
        balenciagaShirtMarkham.setYearOfCreation(2023);
        balenciagaShirtMarkham.setQuantity(45);
        
        Item diorShoeMarkham = new Item();
        diorShoeMarkham.setName("Running Shoes");
        diorShoeMarkham.setBrand("DIOR");
        diorShoeMarkham.setPrice(new BigDecimal("779.99"));
        diorShoeMarkham.setYearOfCreation(2022);
        diorShoeMarkham.setQuantity(15);

        // Add items to their respective centres
        scarborough.addItem(balenciagaShirtScarborough);
        scarborough.addItem(diorShoesScarborough);
        
        mississauga.addItem(stoneIslandShirtMississauga);
        mississauga.addItem(balenciagaShoeMississauga);
        
        vaughan.addItem(diorShirtVaughan);
        vaughan.addItem(stoneIslandShirtVaughan);
        
        markham.addItem(balenciagaShirtMarkham);
        markham.addItem(diorShoeMarkham);

        // Save all distribution centres
        List<DistributionCentre> centres = Arrays.asList(scarborough, mississauga, vaughan, markham);
        distributionCentreRepository.saveAll(centres);
    }
}
