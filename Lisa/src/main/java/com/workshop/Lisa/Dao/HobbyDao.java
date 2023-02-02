package com.workshop.Lisa.Dao;

import com.workshop.Lisa.Entity.Hobby;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HobbyDao extends CrudRepository<Hobby, Long> {

    Optional<Hobby> findByName(String name);

}
