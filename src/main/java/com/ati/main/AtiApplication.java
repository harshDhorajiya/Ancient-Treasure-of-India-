package com.ati.main;

import com.ati.main.model.Role;
import com.ati.main.repositories.RoleRepo;
import com.ati.main.services.impl.EmailSenderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class AtiApplication implements CommandLineRunner {



    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private EmailSenderService emailSenderService;


	public static void main(String[] args) {
		SpringApplication.run(AtiApplication.class, args);
	}



    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Override
    public void run(String... args) throws Exception {

        try {

            Role admin = new Role();
            admin.setRoleId(1);
            admin.setName("ADMIN");

            Role creator = new Role();
            creator.setRoleId(2);
            creator.setName("CREATOR");

            Role user = new Role();
            creator.setRoleId(3);
            creator.setName("USER");

            List<Role> roles = List.of(admin,creator,user);
            List<Role> savedroles = roleRepo.saveAll(roles);

            savedroles.forEach( role -> {
                System.out.println(role.getName() + role.getRoleId());
                    }
            );

        }
        catch ( Exception e  ) {
            e.getStackTrace();
        }
    }
}


