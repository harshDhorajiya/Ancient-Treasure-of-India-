package com.ati.main.repositories;

import com.ati.main.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Integer>  {

    Optional <User> findByEmail(String email);


   // int enableUser(String email);

}
