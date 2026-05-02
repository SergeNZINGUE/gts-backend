package com.gts.backgts.services;

import com.gts.backgts.dto.AuthResponse;
import com.gts.backgts.dto.LoginRequest;
import com.gts.backgts.entites.Role;
import com.gts.backgts.entites.Users;
import com.gts.backgts.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistrationLoginService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtService jwtService;

    @Transactional(readOnly = true)
    public AuthResponse authenticate(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.username(),
                            request.password()
                    )
            );

            Users user = userRepository.findByUsernameWithRoles(request.username())
                    .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable"));

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(request.username());
            String token = jwtService.generateToken(userDetails);

            List<String> roles = user.getRoles()
                    .stream()
                    .map(Role::getCodeTypeRole)
                    .toList();

            return new AuthResponse(token, user.getUsername(), roles);

        } catch (AuthenticationException e) {
            throw new IllegalArgumentException("Identifiants invalides");
        }
    }
}
