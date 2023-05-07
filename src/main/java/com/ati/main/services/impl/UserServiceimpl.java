package com.ati.main.services.impl;

import com.ati.main.exceptions.ResourceNotFound;
import com.ati.main.model.Role;
import com.ati.main.model.User;
import com.ati.main.payload.UserDto;
import com.ati.main.repositories.PostRepo;
import com.ati.main.repositories.RoleRepo;
import com.ati.main.repositories.UserRepo;
import com.ati.main.services.PostService;
import com.ati.main.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceimpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private PostService postService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private EmailSenderService emailSenderService;


    @Override
    public UserDto registerNewUser(UserDto userDto) throws MessagingException {

       if(userRepo.findByEmail(userDto.getEmail()).isPresent())
       {
           throw new IllegalStateException("User already exist please try to login instead ");
       }

       User user = this.dtotouser(userDto);
       user.setPassword(passwordEncoder.encode(user.getPassword()));
       Role role =  roleRepo.findById(3).get();
       user.getRoles().add(role);
       User newuser = userRepo.save(user);
       emailSenderService.sendMail(user.getEmail(), emailSenderService.buildEmail(user.getUser_name(),"http://localhost:8080/api/user/confirm/"));
       return usertodto(newuser);
    }

    @Override
    public UserDto createUser(UserDto user) {

        User objuser = dtotouser(user);
        User saveduser = userRepo.save(objuser);
        return usertodto(saveduser);
    }

    @Override
    public UserDto updateUser(UserDto userdto, Integer userid) {

        User user = this.userRepo.findById(userid).orElseThrow( ()-> new ResourceNotFound("User","Id",userid));

        user.setUser_name(userdto.getUser_name());
        user.setEmail(userdto.getEmail());
        user.setPassword(userdto.getPassword());

        User updateduser = userRepo.save(user);
        return this.usertodto(updateduser);
    }

    @Override
    public UserDto getUserbyid(Integer userid) {

        User user = this.userRepo.findById(userid).orElseThrow( ()-> new ResourceNotFound("User","Id",userid));
        return this.usertodto(user);
    }

    @Override
    public List<UserDto> allUsers() {
        List<User> users = this.userRepo.findAll();

         List<UserDto> userdtos =  users.stream().map(user -> usertodto(user)).collect(Collectors.toList());

        return userdtos;

    }

    @Override
    public void deleteUser(Integer userid) {

        User user = this.userRepo.findById(userid).orElseThrow( ()-> new ResourceNotFound("USer","Id",userid));
        userRepo.delete(user);
    }

    @Override
    public void unlockUser(Integer userid) {
        User user = this.userRepo.findById(userid).orElseThrow( ()-> new ResourceNotFound("USer","Id",userid));
        user.setLocked(true);
    }

    private User dtotouser (UserDto userdto){

          User user = this.modelMapper.map(userdto,User.class);

       /*
        User user = new User();
        user.setUser_id(userdto.getUser_id());
        user.setUser_name(userdto.getUser_name());
        user.setEmail(userdto.getEmail());
        user.setPassword(userdto.getPassword());

          */

        return user;

    }

    public  UserDto usertodto(User user){

        UserDto userdto = this.modelMapper.map(user,UserDto.class);

        /*
        UserDto userdto = new UserDto();
        userdto.setUser_id(user.getUser_id());
        userdto.setUser_name(user.getUser_name());
        userdto.setEmail(user.getEmail());
        userdto.setPassword(user.getPassword());
        */
         return userdto;
    }

}





