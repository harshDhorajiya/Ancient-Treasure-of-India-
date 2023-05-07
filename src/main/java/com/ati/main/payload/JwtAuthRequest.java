package com.ati.main.payload;

import lombok.Data;

@Data
public class JwtAuthRequest {
    private String useremail;
    private String password;
}
