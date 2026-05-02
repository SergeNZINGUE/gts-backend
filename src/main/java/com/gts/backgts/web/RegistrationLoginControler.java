package com.gts.backgts.web;

import com.gts.backgts.dto.AuthResponse;
import com.gts.backgts.dto.LoginRequest;
import com.gts.backgts.dto.RegistrationRequest;
import com.gts.backgts.entites.Role;
import com.gts.backgts.entites.Users;
import com.gts.backgts.repository.RoleRepository;
import com.gts.backgts.repository.UserRepository;
import com.gts.backgts.services.CustomUserDetailsService;
import com.gts.backgts.services.JwtService;
import com.gts.backgts.services.RegistrationLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gts/auth")
public class RegistrationLoginControler {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final RegistrationLoginService registrationLoginService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequest request) {
        if (userRepository.findByUsername(request.username()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        Role role = roleRepository.findByCodeTypeRole(request.roleCode())
                .orElseThrow(() -> new IllegalArgumentException("Role not found: " + request.roleCode()));

        Users user = new Users();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setActive(request.active());
        user.setNomUsers(request.nomUsers());
        user.setPrenomsUsers(request.prenomsUsers());
        user.setEmailUsers(request.emailUsers());
        user.setCguUsers(request.cguUsers());
        user.setTel1Users(request.tel1Users());
        user.setRoles(new HashSet<>());
        user.setDateCreation(LocalDate.now());
        user.setDateModification(LocalDate.now());
        user.getRoles().add(role);

        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody LoginRequest request) {
     AuthResponse response=registrationLoginService.authenticate(request);
     return ResponseEntity.ok(response);
    }
}
