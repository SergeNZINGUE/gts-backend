package com.gts.backgts.repository;

import com.gts.backgts.entites.Reglement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReglementRepository extends JpaRepository<Reglement, Long> {

    List<Reglement> findByFacture_Id(Long factureId);
}
