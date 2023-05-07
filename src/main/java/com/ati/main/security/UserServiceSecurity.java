package com.ati.main.security;

import com.ati.main.exceptions.ResourceNotFound;
import com.ati.main.model.User;
import com.ati.main.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceSecurity implements UserDetailsService {
      @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //load user from database by username -> emailId
          User user =  this.userRepo.findByEmail(username).orElseThrow( ()-> {
              return new ResourceNotFound("User", "email" + username, 0);
          }) ;
        return user;
    }
}
