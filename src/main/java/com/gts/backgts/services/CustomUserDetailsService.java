package com.gts.backgts.services;

import com.gts.backgts.entites.Users;
import com.gts.backgts.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        // 2. Transformer tes rôles en liste de SimpleGrantedAuthority
        List<SimpleGrantedAuthority> authorities = users.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getCodeTypeRole()))
                .toList();
         return new org.springframework.security.core.userdetails.User(
                 users.getUsername(),
                 users.getPassword(),
                 authorities);
    }
}
