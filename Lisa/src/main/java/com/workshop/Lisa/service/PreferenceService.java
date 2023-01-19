package com.workshop.Lisa.service;

import com.workshop.Lisa.Dao.PreferenceDao;
import com.workshop.Lisa.Dto.PreferenceDto;
import com.workshop.Lisa.Entity.Hobby;
import com.workshop.Lisa.Entity.Preference;
import com.workshop.Lisa.Entity.Region;
import com.workshop.Lisa.Utils.HobbyEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PreferenceService {
    private final PreferenceDao dao;

    private final RegionService regionService;

    private final HobbyService hobbyService;

    public void createPreference(PreferenceDto preferenceDto) {

        Set<Region> region = new HashSet<Region>();
        for (String regionString : preferenceDto.getRegions()) {
            try {
                region.add(regionService.findRegionByName(regionString));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        Set<Hobby> hobby = new HashSet<Hobby>();
        for(String hobbyString : preferenceDto.getHobbies()) {
            HobbyEnum hobbyEnum = HobbyEnum.valueOf(hobbyString);
            try {
                hobby.add(hobbyService.findByHobby(hobbyEnum));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        Preference preference = new Preference(
                Long.parseLong(preferenceDto.getUserID()),
                preferenceDto.getMinAge(),
                preferenceDto.getMaxAge(),
                preferenceDto.getGender(),
                region,
                hobby
        );

        this.dao.save(preference);
    }
}
