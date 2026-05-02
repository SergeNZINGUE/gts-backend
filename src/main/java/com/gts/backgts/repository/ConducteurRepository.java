package com.gts.backgts.repository;

import com.gts.backgts.entites.Conducteur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConducteurRepository extends JpaRepository<Conducteur, Long> {

    Conducteur findByIdConducteur(Long idConducteur);
    List<Conducteur> findByStatutConducteur(Integer statutConducteur);
    List<Conducteur> findByNomConducteurContainingIgnoreCase(String nomConducteur);
    List<Conducteur> findByCodeConducteurContainingIgnoreCase(String codeConducteur);
    List<Conducteur> findByNomConducteurContainingIgnoreCaseAndStatutConducteur(String nomConducteur, Integer statutConducteur);
    Conducteur findByCodeConducteur(String codeConducteur);
    Conducteur findByNomConducteurAndPrenomsConducteur(String nomConducteur, String prenomConducteur);
    List<Conducteur> findByDateDebutEmpBetween(java.util.Date startDate, java.util.Date endDate);
}
