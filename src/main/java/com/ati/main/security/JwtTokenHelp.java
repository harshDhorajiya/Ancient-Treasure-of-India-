package com.ati.main.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenHelp {

    public static final long Token_validity = 15*60*60;
    private String secret = "myJwtTokenKey";

    //get username from token
    public String getUsername (String token){

        return getClaimFromToken(token, Claims:: getSubject);
    }

    //get Expiration Date
    public Date getExpDate(String token){
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public<T> T getClaimFromToken(String token, Function<Claims,T> claimsTFunction) {
      final Claims claims = getAllClaimsFromToken(token);
      return claimsTFunction.apply(claims);
    }

    //to get info about token we need secret key
    private Claims getAllClaimsFromToken(String token) {
     return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    //to check token expire or not
    private Boolean isTokenExpired (String token){
        final Date exp = getExpDate(token);
        return exp.before(new Date());
    }

    //generate token for user
    public String generateToken(UserDetails userDetails){
        Map<String,Object> claims = new HashMap<>();
        return doGenerateToken(claims,userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
      return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
              .setExpiration(new Date(System.currentTimeMillis() + Token_validity*1000)).signWith(SignatureAlgorithm.HS512,secret).compact();

    }

   public Boolean validateToken(String token , UserDetails userDetails){
        final String username = getUsername(token);
        return (username.equals(userDetails.getUsername())  && !isTokenExpired(token)) ;
   }

}
