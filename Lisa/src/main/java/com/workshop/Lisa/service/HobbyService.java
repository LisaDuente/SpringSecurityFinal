package com.workshop.Lisa.service;

import com.workshop.Lisa.Dao.HobbyDao;
import com.workshop.Lisa.Entity.Hobby;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HobbyService {

    private final HobbyDao hobbyDao;

    public Hobby findByHobby(String hobby){
        return hobbyDao.findByName(hobby)
                .orElseThrow(() -> new EntityNotFoundException("Could not cast Optional into Hobby"));
    }

    public List<Hobby> getAllHobbies(){
        return (List<Hobby>) this.hobbyDao.findAll();
    }
}
