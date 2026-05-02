package com.gts.backgts.repository;

import com.gts.backgts.entites.Engins;
import com.gts.backgts.entites.TypeEtatEngins;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface EnginsRepository extends JpaRepository<Engins, Long> {

    Optional <Engins> findById(Long id);
    Engins findByCodeEngin(String codeEngin);
    List<Engins> findByImmatriculationEnginContainingIgnoreCase(String immatriculationEngin);
    List<Engins> findByMarqueEnginContainingIgnoreCase(String marqueEngin);
    List<Engins> findByDateAcqEnginBetween(java.util.Date startDate, java.util.Date endDate);
    List<Engins> findByAnneeEnginBetween(java.util.Date startDate, java.util.Date endDate);
    Engins findByImmatriculationEngin(String immatriculationEngin);
    List<Engins> findByTypeEnginContainingIgnoreCase(String typeEngin);
    List<Engins> findByTypCarbtEnginContainingIgnoreCase(String typCarbtEngin);
    List<Engins> findByEtatEnginContainingIgnoreCase(String etatEngin);
    Boolean existsByCodeEngin(String codeEngin);

}
