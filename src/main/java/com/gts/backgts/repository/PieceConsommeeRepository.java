package com.gts.backgts.repository;

import com.gts.backgts.entites.PieceConsommee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PieceConsommeeRepository extends JpaRepository<PieceConsommee, Long> {

    List<PieceConsommee> findByMaintenanceId(Long maintenanceId);

    List<PieceConsommee> findByPieceRechangeId(Long pieceRechangeId);
}
