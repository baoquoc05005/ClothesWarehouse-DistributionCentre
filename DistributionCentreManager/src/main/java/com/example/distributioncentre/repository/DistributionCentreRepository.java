package com.example.distributioncentre.repository;

import com.example.distributioncentre.model.DistributionCentre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistributionCentreRepository extends JpaRepository<DistributionCentre, Long> {
    DistributionCentre findByName(String name);
}
