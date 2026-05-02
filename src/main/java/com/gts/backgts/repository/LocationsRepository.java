package com.gts.backgts.repository;

import com.gts.backgts.entites.ActiviteClient;
import com.gts.backgts.entites.Conducteur;
import com.gts.backgts.entites.Locations;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface LocationsRepository extends JpaRepository<Locations, Long> {

    Optional<Locations> findById(Long id);

    List<Locations> findByConducteur(Conducteur conducteur);

    List<Locations> findByClient_Id(Long clientId, Pageable pageable);
    List<Locations> findByClient_Id(Long clientId);
    List<Locations> findByClient_DescriptionEntreprise(String descriptionEntreprise);

    List<Locations> findByClient_ActiviteClient(ActiviteClient clientActiviteClient);
    List<Locations> findByClient_ActiviteClient_Id(Long activiteClientId);
    List<Locations> findByClient_ActiviteClient_Description(String activiteDescription);

    List<Locations> findByEtatLocation(Integer etatLocation);

    // Reporting
    List<Locations> findByDateDbtLocBetween(Date dateDebut, Date dateFin);
    List<Locations> findByClient_IdAndDateDbtLocBetween(Long clientId, Date dateDebut, Date dateFin);

}
