package com.workshop.Lisa.service;

import com.workshop.Lisa.Dao.HobbyDao;
import com.workshop.Lisa.Entity.Hobby;
import com.workshop.Lisa.Utils.HobbyEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class HobbyService {

    private final HobbyDao hobbyDao;

    public Hobby findByHobby(HobbyEnum hobby){
        return hobbyDao.findByHobby(hobby)
                .orElseThrow(() -> new EntityNotFoundException("Could not cast Optional into Hobby"));
    }

    public List<Hobby> getAllHobbies(){
        return (List<Hobby>) this.hobbyDao.findAll();
    }
}
