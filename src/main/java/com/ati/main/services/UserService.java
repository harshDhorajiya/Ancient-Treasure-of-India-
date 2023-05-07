package com.ati.main.services;

import com.ati.main.payload.UserDto;

import javax.mail.MessagingException;
import java.util.List;

public interface UserService {

    UserDto registerNewUser( UserDto userDto ) throws MessagingException;
    UserDto createUser(UserDto user);
    UserDto updateUser(UserDto user, Integer userid);
    UserDto getUserbyid(Integer userid);
    List<UserDto> allUsers();
    void deleteUser(Integer userid);
   void unlockUser(Integer userid);

}
