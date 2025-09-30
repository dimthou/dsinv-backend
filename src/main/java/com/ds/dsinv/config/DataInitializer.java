package com.ds.dsinv.config;

import com.ds.dsinv.domain.Role;
import com.ds.dsinv.domain.User;
import com.ds.dsinv.repository.RoleRepository;
import com.ds.dsinv.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(RoleRepository roleRepo, UserRepository userRepo) {
        return args -> {
            // Seed ROLE_ADMIN only if it doesn't exist
            Role adminRole = roleRepo.findByName("ROLE_ADMIN")
                    .orElseGet(() -> roleRepo.save(new Role(null, "ROLE_ADMIN")));

            // Seed admin user only if absent
            userRepo.findByUsername("admin").orElseGet(() -> {
                String encodedPw = new BCryptPasswordEncoder().encode("adminPass");
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(encodedPw);
                admin.setRoles(Collections.singleton(adminRole));
                return userRepo.save(admin);
            });
        };
    }
}
