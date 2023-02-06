package com.workshop.Lisa.Dao;


import com.workshop.Lisa.Entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDao extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String string);

    Optional<User> findByContactInformationEmail(String string);

    Optional<User> findByUsernameOrContactInformation_email(String username, String email);

//    Boolean existsByEmail(String email);
}
