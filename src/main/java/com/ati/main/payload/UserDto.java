package com.ati.main.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    private  int user_id ;

    @NotEmpty
    @Size( min = 2 , message = "Username must be at least 2 character long")
    private  String user_name;
     @Email(message = "please enter valid email address")
     @NotEmpty
     private  String email;
     @NotNull
     @NotEmpty
     private String password;
     private Boolean locked;
}
