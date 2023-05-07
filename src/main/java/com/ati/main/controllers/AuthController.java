package com.ati.main.controllers;

import com.ati.main.payload.JwtAuthRequest;
import com.ati.main.payload.JwtAuthResponce;
import com.ati.main.payload.UserDto;
import com.ati.main.security.JwtTokenHelp;
import com.ati.main.security.UserServiceSecurity;
import com.ati.main.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/auth/")
public class AuthController {

  @Autowired
 private JwtTokenHelp jwtTokenHelp;

  @Autowired
  private UserServiceSecurity userServiceSecurity;

  @Autowired
  private UserDetailsService userDetailsService;
  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private UserService userService;

  @PostMapping("/login")
  public ResponseEntity<JwtAuthResponce> createToken (@RequestBody JwtAuthRequest request ) throws Exception {

    this.authenticate(request.getUseremail() , request.getPassword());
       UserDetails userDetail = this.userDetailsService.loadUserByUsername(request.getUseremail());
      String token = this.jwtTokenHelp.generateToken(userDetail);

     JwtAuthResponce responce =  new JwtAuthResponce( );
     responce.setToken(token);
     return new ResponseEntity<JwtAuthResponce>(responce, HttpStatus.OK);

  }

  private void authenticate(String username , String password) throws Exception {

    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username , password);

    try {
      this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }
    catch (DisabledException e){
      System.out.println("User id disable");
    }

    catch (BadCredentialsException e){
        System.out.println("Email id or Password not matched !!");
        throw  new  Exception("Invalid details !!");
    }

  }

  //register new user
   @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto) throws MessagingException {
     UserDto registeredUser = userService.registerNewUser(userDto);
     return new ResponseEntity<UserDto>(registeredUser,HttpStatus.CREATED);

  }


}
