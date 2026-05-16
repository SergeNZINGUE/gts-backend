package com.gts.backgts.repository;

import com.gts.backgts.entites.ConducteurMission;
import com.gts.backgts.entites.Missions;
import com.gts.backgts.enums.StatutMission;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ConducteurMissionRepository extends JpaRepository<ConducteurMission, Long> {

    Optional<ConducteurMission> findByMission(Missions mission);

    @Query("""
        select m
        from ConducteurMission cm
        join cm.mission m
        where cm.conducteur.idConducteur = :conducteurId
       order by m.dateTravail desc
    """)
    Page<Missions> findMissionsByConducteurId(@Param("conducteurId") Long conducteurId, Pageable pageable);

    @Query("""
        select m
        from ConducteurMission cm
        join cm.mission m
        join m.location l
        where cm.conducteur.idConducteur = :conducteurId
          and l.id = :locationId
    """)
    List<Missions> findMissionsByConducteurIdAndLocationId(
            @Param("conducteurId") Long conducteurId,
            @Param("locationId") Long locationId
    );

    @Query("""
    SELECT COUNT(cm) > 0
    FROM ConducteurMission cm
    WHERE cm.conducteur.idConducteur = :conducteurId
      AND cm.mission.statutMission = :statut
""")
    boolean existsConducteurEnMission(
            @Param("conducteurId") Long conducteurId,
            @Param("statut") StatutMission statut
    );
}
