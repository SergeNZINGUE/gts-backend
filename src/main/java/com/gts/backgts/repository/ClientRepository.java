package com.gts.backgts.repository;

import com.gts.backgts.entites.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findById(Long id);
    Client findByDescriptionEntreprise(String descriptionEntreprise);
    List<Client> findByDescriptionEntrepriseContaining(String descriptionEntreprise);
    List<Client> findByActiviteClient_CodeActClt(String codeActivite);
    @Override
    List<Client> findAll();
}
