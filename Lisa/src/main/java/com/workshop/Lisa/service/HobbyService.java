package com.workshop.Lisa.service;

import com.workshop.Lisa.Dao.HobbyDao;
import com.workshop.Lisa.Entity.Hobby;
import com.workshop.Lisa.Utils.HobbyEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HobbyService {

    private final HobbyDao hobbyDao;

    public Hobby findByHobby(HobbyEnum hobby) throws Exception {
        return hobbyDao.findByHobby(hobby).orElseThrow(() -> new Exception("Could not cast Optional into Hobby"));
    }
}
