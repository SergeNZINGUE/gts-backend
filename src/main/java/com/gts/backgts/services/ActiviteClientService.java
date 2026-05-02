package com.gts.backgts.services;

import com.gts.backgts.dto.ActiviteClientResponse;
import com.gts.backgts.dto.AssuranceEnginResponse;
import com.gts.backgts.entites.ActiviteClient;
import com.gts.backgts.repository.ActiviteClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ActiviteClientService {
    private final ActiviteClientRepository activiteClientRepository;

    @Transactional(readOnly = true)
    public List<ActiviteClientResponse> getActiviteClient() {
        return activiteClientRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private ActiviteClientResponse toResponse(ActiviteClient activiteClient) {
        return new ActiviteClientResponse(
                activiteClient.getId(),
                activiteClient.getCodeActClt(),
                activiteClient.getDescription(),
                activiteClient.getDateCreation(),
                activiteClient.getDateModification());

    }

}
