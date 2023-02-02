package com.workshop.Lisa.Dao;

import com.workshop.Lisa.Entity.Region;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegionDao extends CrudRepository<Region, Long> {

    Optional<Region> findByName(String regionName);
}
