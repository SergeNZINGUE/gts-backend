package com.gts.backgts.services;

import com.gts.backgts.dto.UserRequest;
import com.gts.backgts.dto.UserResponse;
import com.gts.backgts.entites.Role;
import com.gts.backgts.entites.Users;
import com.gts.backgts.repository.RoleRepository;
import com.gts.backgts.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        return userRepository.findAllWithRoles()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable avec id: " + id));
        return toResponse(user);
    }

    public UserResponse updateUser(Long id, UserRequest request) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable avec id: " + id));

        user.setUsername(request.username());
        user.setNomUsers(request.nomUsers());
        user.setPrenomsUsers(request.prenomsUsers());
        user.setEmailUsers(request.emailUsers());
        user.setTel1Users(request.tel1Users());
        user.setActive(request.active());
        user.setCguUsers(request.cguUsers());
        user.setDateModification(LocalDate.now());

        if (request.roleCode() != null && !request.roleCode().isBlank()) {
            Role role = roleRepository.findByCodeTypeRole(request.roleCode())
                    .orElseThrow(() -> new IllegalArgumentException("Rôle introuvable: " + request.roleCode()));
            user.getRoles().clear();
            user.getRoles().add(role);
        }

        return toResponse(userRepository.save(user));
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("Utilisateur introuvable avec id: " + id);
        }
        userRepository.deleteById(id);
    }

    public UserResponse toggleActive(Long id) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable avec id: " + id));
        user.setActive(Boolean.FALSE.equals(user.getActive()));
        user.setDateModification(LocalDate.now());
        return toResponse(userRepository.save(user));
    }

    private UserResponse toResponse(Users user) {
        List<String> roles = user.getRoles()
                .stream()
                .map(Role::getCodeTypeRole)
                .toList();
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getActive(),
                user.getNomUsers(),
                user.getPrenomsUsers(),
                user.getEmailUsers(),
                user.getTel1Users(),
                user.getCguUsers(),
                user.getDateCreation(),
                user.getDateModification(),
                roles
        );
    }
}