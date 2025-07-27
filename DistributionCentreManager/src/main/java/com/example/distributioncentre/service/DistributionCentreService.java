package com.example.distributioncentre.service;

import com.example.distributioncentre.model.DistributionCentre;
import com.example.distributioncentre.model.Item;
import com.example.distributioncentre.repository.DistributionCentreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DistributionCentreService {

    private final DistributionCentreRepository distributionCentreRepository;

    @Autowired
    public DistributionCentreService(DistributionCentreRepository distributionCentreRepository) {
        this.distributionCentreRepository = distributionCentreRepository;
    }

    public List<DistributionCentre> findAllDistributionCentres() {
        return distributionCentreRepository.findAll();
    }

    public Optional<DistributionCentre> findDistributionCentreById(Long id) {
        return distributionCentreRepository.findById(id);
    }

    public DistributionCentre findDistributionCentreByName(String name) {
        return distributionCentreRepository.findByName(name);
    }

    @Transactional
    public DistributionCentre saveDistributionCentre(DistributionCentre distributionCentre) {
        return distributionCentreRepository.save(distributionCentre);
    }

    @Transactional
    public void deleteDistributionCentre(Long id) {
        distributionCentreRepository.deleteById(id);
    }

    /**
     * Calculate the distance between two points using the Haversine formula
     * @param lat1 Latitude of first point
     * @param lon1 Longitude of first point
     * @param lat2 Latitude of second point
     * @param lon2 Longitude of second point
     * @return Distance in kilometers
     */
    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the earth in km
        
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        
        return R * c;
    }

    /**
     * Find the closest distribution centre that has the requested item in stock
     * @param brand Item brand
     * @param name Item name
     * @param quantity Quantity needed
     * @param warehouseLat Warehouse latitude
     * @param warehouseLon Warehouse longitude
     * @return The closest distribution centre with the item in stock, or null if none found
     */
    public DistributionCentre findClosestDistributionCentreWithItem(String brand, String name, int quantity,
                                                                  double warehouseLat, double warehouseLon) {
        List<DistributionCentre> centres = findAllDistributionCentres();
        DistributionCentre closestCentre = null;
        double minDistance = Double.MAX_VALUE;
        
        for (DistributionCentre centre : centres) {
            // Check if this centre has the item in stock
            boolean hasItem = centre.getItems().stream()
                    .anyMatch(item -> item.getBrand().equals(brand) 
                            && item.getName().equals(name) 
                            && item.getQuantity() >= quantity);
            
            if (hasItem) {
                double distance = calculateDistance(warehouseLat, warehouseLon, 
                        centre.getLatitude(), centre.getLongitude());
                
                if (distance < minDistance) {
                    minDistance = distance;
                    closestCentre = centre;
                }
            }
        }
        
        return closestCentre;
    }
}
