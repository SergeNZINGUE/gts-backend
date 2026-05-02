package com.gts.backgts.services;

import com.gts.backgts.dto.RoleRequest;
import com.gts.backgts.dto.RoleResponse;
import com.gts.backgts.entites.Role;
import com.gts.backgts.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleResponse createRole(RoleRequest request) {
        if (roleRepository.findByCodeTypeRole(request.codeTypeRole()).isPresent()) {
            throw new IllegalArgumentException("Un rôle avec ce code existe déjà: " + request.codeTypeRole());
        }

        Role role = new Role();
        role.setCodeTypeRole(request.codeTypeRole());
        role.setRolename(request.rolename());
        role.setDateCreation(LocalDate.now());

        return toResponse(roleRepository.save(role));
    }

    @Transactional(readOnly = true)
    public List<RoleResponse> getAllRoles() {
        return roleRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public RoleResponse getRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rôle introuvable avec id: " + id));
        return toResponse(role);
    }

    public RoleResponse updateRole(Long id, RoleRequest request) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rôle introuvable avec id: " + id));

        role.setCodeTypeRole(request.codeTypeRole());
        role.setRolename(request.rolename());
        role.setDateModification(LocalDate.now());

        return toResponse(roleRepository.save(role));
    }

    public void deleteRole(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new IllegalArgumentException("Rôle introuvable avec id: " + id);
        }
        roleRepository.deleteById(id);
    }

    private RoleResponse toResponse(Role role) {
        return new RoleResponse(
                role.getId(),
                role.getCodeTypeRole(),
                role.getRolename(),
                role.getDateCreation(),
                role.getDateModification()
        );
    }
}
