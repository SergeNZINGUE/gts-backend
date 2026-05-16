package com.gts.backgts.services;

import com.gts.backgts.enums.ModeCloture;
import com.gts.backgts.enums.StatutMission;
import com.gts.backgts.repository.MissionsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Component
@RequiredArgsConstructor
@Slf4j
public class MissionScheduler {

    private final MissionsRepository missionsRepository;
    private final MissionsService missionsService;

    /**
     * Clôture automatiquement toutes les missions EN_COURS
     * dont la dateFinMission est dépassée.
     * Tourne chaque soir à 23h30.
     */
    @Scheduled(cron = "0 30 23 * * *")
    @Transactional
    public void cloturerMissionsTerminees() {
        var missions = missionsRepository
                .findMissionsACloturer(StatutMission.EN_COURS, LocalDate.now());

        if (missions.isEmpty()) {
            log.info("[Scheduler] Aucune mission à clôturer automatiquement.");
            return;
        }
        missions.forEach(m -> missionsService.cloturerMission(m.getId()));
        log.info("[Scheduler] {} mission(s) clôturée(s) automatiquement.", missions.size());
    }
}
