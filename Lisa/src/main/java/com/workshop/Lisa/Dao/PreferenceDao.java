package com.workshop.Lisa.Dao;

import com.workshop.Lisa.Entity.Preference;
import com.workshop.Lisa.Utils.GenderEnum;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PreferenceDao extends CrudRepository<Preference, Long> {

    Optional<Preference> findAllByGender(GenderEnum gender);




}
