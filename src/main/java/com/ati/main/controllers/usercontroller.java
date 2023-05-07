package com.ati.main.controllers;

import com.ati.main.payload.UserDto;
import com.ati.main.services.PostService;
import com.ati.main.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/user")

public class usercontroller {

    @Autowired
  private UserService userService;

    private PostService postService;

    //post create user
    @PostMapping("/")
    public ResponseEntity<UserDto> createuser( @Valid @RequestBody UserDto userdto ){
        UserDto createduserdto = this.userService.createUser(userdto);
        return new ResponseEntity<>(createduserdto, HttpStatus.CREATED);
    }

    //put update user
    @PutMapping("/{userid}")
    public ResponseEntity<UserDto> updateuser (@Valid @RequestBody UserDto userdto , @PathVariable Integer userid) {
        UserDto updateduserdto = this.userService.updateUser(userdto,userid);
        return  ResponseEntity.ok(updateduserdto);
    }

     //Delete user
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userid}")
    public ResponseEntity<?> deleteeuser (  @PathVariable Integer userid) {
        this.userService.deleteUser(userid);
        return new ResponseEntity(Map.of("Message" ,"User deleted successfully" ) , HttpStatus.OK);
    }

    // Get user
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> listofusers () {
      return  ResponseEntity.ok(this.userService.allUsers());
    }

    @GetMapping("/{userid}")
    public ResponseEntity<UserDto> getuser ( @PathVariable Integer userid ) {
        return  ResponseEntity.ok(this.userService.getUserbyid(userid));
    }

    @PutMapping("/confirm/{userid}")
    public ResponseEntity<?> unlockeUserbyemail(@PathVariable Integer userid){
         userService.unlockUser(userid);
       return new ResponseEntity<>(Map.of("Message","User unlocked !!"),HttpStatus.OK);
    }

}