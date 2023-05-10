package com.ati.main.config;

import com.ati.main.security.JwtAuthEntry;
import com.ati.main.security.JwtAuthFilter;
import com.ati.main.security.UserServiceSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)

public class Securityconfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserServiceSecurity userServiceSecurity;

    @Autowired
    private JwtAuthEntry jwtAuthEntry;

    @Autowired
    private JwtAuthFilter jwtAuthFilter;


     @Override
    protected  void configure(HttpSecurity httpSecurity) throws Exception{

                 httpSecurity.
                 csrf().disable()
                 .authorizeHttpRequests()
                         .antMatchers("/api/auth/**").permitAll()
                         .antMatchers(HttpMethod.GET).permitAll()
                         .antMatchers("/api/user/confirm/**").permitAll()
                 .anyRequest()
                 .authenticated()
                 .and()
                         .exceptionHandling().authenticationEntryPoint(jwtAuthEntry)
                 .and()
                         .sessionManagement()
                         .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

                 httpSecurity.addFilterBefore(jwtAuthFilter , UsernamePasswordAuthenticationFilter.class );
     }

   @Override
    protected void configure(AuthenticationManagerBuilder auth)throws Exception{

       auth.userDetailsService(userServiceSecurity).passwordEncoder(passwordEncoder());
     }

   @Bean
   public PasswordEncoder passwordEncoder(){
         return new BCryptPasswordEncoder();
   }


   @Bean
   @Override
   public AuthenticationManager authenticationManagerBean() throws
           Exception {
         return super.authenticationManagerBean();
   }
}
