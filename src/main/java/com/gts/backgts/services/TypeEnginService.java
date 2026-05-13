package com.gts.backgts.services;


import com.gts.backgts.dto.TypeEnginRequest;
import com.gts.backgts.dto.TypeEnginResponse;
import com.gts.backgts.entites.FamilleEngin;
import com.gts.backgts.entites.TypeEngin;
import com.gts.backgts.repository.TypeEnginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TypeEnginService {

    private final TypeEnginRepository typeEnginRepository;

    public List<TypeEnginResponse> getAll() {
        return typeEnginRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<TypeEnginResponse> getActifs() {
        return typeEnginRepository.findByActifTrue()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<TypeEnginResponse> getByFamille(FamilleEngin famille) {
        return typeEnginRepository.findByFamilleAndActifTrue(famille)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public TypeEnginResponse getById(Long id) {
        return typeEnginRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new RuntimeException("Type engin non trouvé : " + id));
    }

    public TypeEnginResponse create(TypeEnginRequest request) {
        if (typeEnginRepository.existsByCode(request.code().toUpperCase())) {
            throw new RuntimeException("Ce code existe déjà : " + request.code());
        }
        TypeEngin entity = TypeEngin.builder()
                .code(request.code().toUpperCase())
                .libelle(request.libelle())
                .famille(request.famille())
                .description(request.description())
                .actif(request.actif() != null ? request.actif() : true)
                .build();
        return toResponse(typeEnginRepository.save(entity));
    }

    public TypeEnginResponse update(Long id, TypeEnginRequest request) {
        TypeEngin entity = typeEnginRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Type engin non trouvé : " + id));
        entity.setLibelle(request.libelle());
        entity.setFamille(request.famille());
        entity.setDescription(request.description());
        if (request.actif() != null) entity.setActif(request.actif());
        return toResponse(typeEnginRepository.save(entity));
    }

    public void delete(Long id) {
        typeEnginRepository.deleteById(id);
    }

    public TypeEnginResponse toResponse(TypeEngin e) {
        return new TypeEnginResponse(
                e.getId(),
                e.getCode(),
                e.getLibelle(),
                e.getFamille(),
                e.getDescription(),
                e.getActif()
        );
    }

}
