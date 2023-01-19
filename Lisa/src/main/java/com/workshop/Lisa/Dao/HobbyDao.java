package com.workshop.Lisa.Dao;

import com.workshop.Lisa.Entity.Hobby;
import com.workshop.Lisa.Utils.HobbyEnum;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HobbyDao extends CrudRepository<Hobby, Long> {

    Optional<Hobby> findByHobby(HobbyEnum hobbyName);

}
