package com.workshop.Lisa.Dao;


import com.workshop.Lisa.Entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDao extends CrudRepository<User, Long> {
    Optional<User> findByUserName(String string);

    Optional<User> findByContactInformationUserEmail(String string);

    Optional<User> findByUserNameOrContactInformation_UserEmail(String username, String email);

//    Boolean existsByEmail(String email);
}
