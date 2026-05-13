package com.gts.backgts.repository;

import com.gts.backgts.entites.FamilleEngin;
import com.gts.backgts.entites.TypeEngin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TypeEnginRepository extends JpaRepository<TypeEngin, Long> {
    Optional<TypeEngin> findByCode(String code);
    List<TypeEngin> findByFamille(FamilleEngin famille);
    List<TypeEngin> findByActifTrue();
    List<TypeEngin> findByFamilleAndActifTrue(FamilleEngin famille);
    boolean existsByCode(String code);

}
