package com.workshop.Lisa.service;

import com.workshop.Lisa.Dao.RegionDao;
import com.workshop.Lisa.Entity.Region;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegionService {
    private final RegionDao regionDao;

    public Region findRegionByName(String regionName) throws Exception {
        return regionDao.findByRegionName(regionName).orElseThrow(() -> new Exception("Could not cast Optional into Region"));
    }
}
