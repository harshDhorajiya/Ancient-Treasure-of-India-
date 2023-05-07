package com.ati.main.security;

import com.ati.main.model.User;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
   private UserServiceSecurity userServiceSecurity;

    @Autowired
    private JwtTokenHelp jwtTokenHelp;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

      //get token

        String requestToken = request.getHeader("Authorization");
        System.out.println(requestToken +" here null !! ");
        String username = null;
        String token = null;
        if(requestToken!= null && requestToken.startsWith("Bearer") ){

             token =  requestToken.substring(7);

            try {
                username = jwtTokenHelp.getUsername(token);
            }

            catch ( IllegalArgumentException e ) {
                System.out.println("Unable to get jwt token");
            }

            catch (ExpiredJwtException e ){
                System.out.println("Jwt token has expired");
            }

            catch (MalformedJwtException e){
                System.out.println("invalid jwt token");
            }
        }
        else{
            System.out.println("jwt doesn't start with bearer");
        }

        //need to validate token

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null ){

            UserDetails userDetail =  this.userServiceSecurity.loadUserByUsername(username);

            if(this.jwtTokenHelp.validateToken(token,userDetail)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            }
            else {
                System.out.println("Invalid jwt token");
            }
        }
        else {
            System.out.println("Username is null or Context is not null");
         }

        filterChain.doFilter(request,response);

    }
}
