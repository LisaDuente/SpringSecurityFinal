package com.workshop.Lisa.service;

import com.workshop.Lisa.Dao.PreferenceDao;
import com.workshop.Lisa.Entity.Hobby;
import com.workshop.Lisa.Entity.Preference;
import com.workshop.Lisa.Entity.Region;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PreferenceService {
    private final PreferenceDao dao;

    private final RegionService regionService;

    private final HobbyService hobbyService;

    public List<Hobby> getAllHobbies(){
        return this.hobbyService.getAllHobbies();
    }

    public void createPreference(Preference pref){
        this.dao.save(pref);
    }

    public Preference getPrefById(long userId){
        return this.dao.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("could not find user"));
    }

    public List<Region> getAllRegions(){
        return this.regionService.getAllRegions();

    }
}
