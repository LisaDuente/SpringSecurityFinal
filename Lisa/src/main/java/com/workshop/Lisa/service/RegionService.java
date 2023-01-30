package com.workshop.Lisa.service;

import com.workshop.Lisa.Dao.RegionDao;
import com.workshop.Lisa.Entity.Region;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class RegionService {
    private final RegionDao regionDao;

    public Region findRegionByName(String regionName){
        return regionDao.findByRegionName(regionName)
                .orElseThrow(() -> new EntityNotFoundException("Could not cast Optional into Region"));
    }
}
