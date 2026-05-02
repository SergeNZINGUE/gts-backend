package com.gts.backgts.repository;

import com.gts.backgts.entites.ActiviteClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActiviteClientRepository extends JpaRepository<ActiviteClient, Long> {

    @Override
    List<ActiviteClient> findAll();
    ActiviteClient findByCodeActClt(String codeActivite);
}
