package com.metropolitan.config;

import com.metropolitan.entity.Car;
import com.metropolitan.entity.Owner;
import com.metropolitan.entity.Role;
import com.metropolitan.entity.User;
import com.metropolitan.repository.CarRepository;
import com.metropolitan.repository.OwnerRepository;
import com.metropolitan.repository.RoleRepository;
import com.metropolitan.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initData(
            RoleRepository roleRepository,
            UserRepository userRepository,
            OwnerRepository ownerRepository,
            CarRepository carRepository
    ) {
        return args -> {
            // 1. ROLE INIT
            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseGet(() -> roleRepository.save(new Role(null, "ROLE_ADMIN")));

            Role userRole = roleRepository.findByName("ROLE_USER")
                    .orElseGet(() -> roleRepository.save(new Role(null, "ROLE_USER")));

            // 2. ADMIN USER
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setName("Admin User");
                admin.setUsername("admin");
                admin.setEmail("admin@example.com");
                admin.setPassword(passwordEncoder.encode("admin"));

                admin.setRoles(Set.of(adminRole, userRole));
                userRepository.save(admin);
            }

            // 3. REGULAR USER
            if (userRepository.findByUsername("user").isEmpty()) {
                User user = new User();
                user.setName("Regular User");
                user.setUsername("user");
                user.setEmail("user@example.com");
                user.setPassword(passwordEncoder.encode("user"));
                user.setRoles(Set.of(userRole));
                userRepository.save(user);
            }

            // 4. OWNERS
            Owner owner1 = Owner.builder().firstName("Ana").lastName("Petrović").build();
            Owner owner2 = Owner.builder().firstName("Marko").lastName("Jovanović").build();
            ownerRepository.saveAll(List.of(owner1, owner2));

            // 5. CARS
            Car car1 = Car.builder().brand("Toyota").model("Yaris").manufactureYear(2020).owner(owner1).build();
            Car car2 = Car.builder().brand("Ford").model("Focus").manufactureYear(2018).owner(owner1).build();
            Car car3 = Car.builder().brand("Hyundai").model("i30").manufactureYear(2022).owner(owner2).build();
            carRepository.saveAll(List.of(car1, car2, car3));
        };
    }
}

