package com.gts.backgts.services;

import com.gts.backgts.dto.BilanCarburantEnginDto;
import com.gts.backgts.dto.ConsommationMissionDto;
import com.gts.backgts.dto.RapportCarburantResponse;
import com.gts.backgts.entites.ConsommationCarburant;
import com.gts.backgts.entites.Engins;
import com.gts.backgts.entites.Missions;
import com.gts.backgts.repository.ConsommationCarburantRepository;
import com.gts.backgts.repository.EnginsRepository;
import com.gts.backgts.repository.MissionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CarburantReportingService {

    private final MissionsRepository                missionsRepository;
    private final ConsommationCarburantRepository   consommationCarburantRepository;
    private final EnginsRepository                  enginsRepository;

    // ══════════════════════════════════════════════════════════════════════
    // Point d'entrée principal
    // ══════════════════════════════════════════════════════════════════════

    public RapportCarburantResponse getRapport(LocalDate debut,
                                               LocalDate fin,
                                               Long enginIdFiltre) {

        LocalDateTime debutDT = debut.atStartOfDay();
        LocalDateTime finDT   = fin.plusDays(1).atStartOfDay();

        // ── 1. Récupération des données ──────────────────────────────────
        List<ConsommationCarburant> allFillups;
        List<Missions>              allMissions;

        if (enginIdFiltre != null) {
            allFillups  = consommationCarburantRepository
                    .findByEnginAndPeriode(enginIdFiltre, debutDT, finDT);
            allMissions = missionsRepository
                    .findByEnginAndPeriode(enginIdFiltre, debut, fin);
        } else {
            allFillups  = consommationCarburantRepository.findAllByPeriode(debutDT, finDT);
            allMissions = missionsRepository.findAllByPeriode(debut, fin);
        }

        // ── 2. Identification des engins actifs ──────────────────────────
        Set<Long> enginIds = new LinkedHashSet<>();
        allFillups.stream()
                .map(f -> f.getEngin().getId())
                .forEach(enginIds::add);
        allMissions.stream()
                .filter(m -> m.getLocation() != null && m.getLocation().getEngins() != null)
                .map(m -> m.getLocation().getEngins().getId())
                .forEach(enginIds::add);

        List<Engins> enginsActifs = enginsRepository.findAllById(enginIds);

        // ── 3. Regroupement par engin ────────────────────────────────────
        Map<Long, List<ConsommationCarburant>> fillupsParEngin = allFillups.stream()
                .collect(Collectors.groupingBy(f -> f.getEngin().getId()));

        Map<Long, List<Missions>> missionsParEngin = allMissions.stream()
                .filter(m -> m.getLocation() != null && m.getLocation().getEngins() != null)
                .collect(Collectors.groupingBy(m -> m.getLocation().getEngins().getId()));

        // ── 4. Calcul bilan par engin ────────────────────────────────────
        List<BilanCarburantEnginDto> bilans = enginsActifs.stream()
                .map(e -> computeBilanEngin(
                        e,
                        fillupsParEngin.getOrDefault(e.getId(), List.of()),
                        missionsParEngin.getOrDefault(e.getId(), List.of())))
                .sorted(Comparator.comparingDouble(
                        (BilanCarburantEnginDto b) -> b.totalLitresPleins() != null
                                ? b.totalLitresPleins() : 0.0).reversed())
                .toList();

        // ── 5. Agrégats globaux ──────────────────────────────────────────
        double totalLitres = allFillups.stream()
                .mapToDouble(ConsommationCarburant::getQuantiteLitres).sum();

        double coutTotal = allFillups.stream()
                .mapToDouble(ConsommationCarburant::getCoutTotal).sum();

        int nbAvecData = (int) allMissions.stream()
                .filter(m -> m.getCarbtDbtMission() != null && m.getCarbtFinMission() != null)
                .count();

        double tauxCouverture = allMissions.isEmpty() ? 0.0
                : (double) nbAvecData / allMissions.size() * 100.0;

        // Ratio L/h moyen pondéré (depuis les missions avec données complètes)
        double sumConso = bilans.stream()
                .mapToDouble(b -> b.consommationMissions() != null ? b.consommationMissions() : 0.0)
                .sum();
        double sumHeures = bilans.stream()
                .flatMap(b -> b.detailMissions().stream())
                .filter(m -> m.donneesCompletes()
                        && m.nbHeures() != null && m.nbHeures() > 0)
                .mapToDouble(ConsommationMissionDto::nbHeures)
                .sum();
        Double ratioMoyen = sumHeures > 0 ? sumConso / sumHeures : null;

        return new RapportCarburantResponse(
                debut, fin, enginIdFiltre,
                totalLitres, coutTotal, ratioMoyen,
                enginsActifs.size(),
                allMissions.size(), nbAvecData, tauxCouverture,
                bilans
        );
    }

    // ══════════════════════════════════════════════════════════════════════
    // Bilan d'un engin
    // ══════════════════════════════════════════════════════════════════════

    private BilanCarburantEnginDto computeBilanEngin(Engins engin,
                                                     List<ConsommationCarburant> fillups,
                                                     List<Missions> missions) {

        // ── Source 1 : Pleins ────────────────────────────────────────────
        double totalLitresPleins = fillups.stream()
                .mapToDouble(ConsommationCarburant::getQuantiteLitres).sum();
        double coutTotal = fillups.stream()
                .mapToDouble(ConsommationCarburant::getCoutTotal).sum();

        OptionalDouble hMin = fillups.stream()
                .mapToDouble(ConsommationCarburant::getHorametreAuPlein).min();
        OptionalDouble hMax = fillups.stream()
                .mapToDouble(ConsommationCarburant::getHorametreAuPlein).max();

        Double deltaH = (hMin.isPresent() && hMax.isPresent())
                ? hMax.getAsDouble() - hMin.getAsDouble() : null;
        Double ratioHPleins = (deltaH != null && deltaH > 0)
                ? totalLitresPleins / deltaH : null;

        // ── Source 2 : Jauges missions ───────────────────────────────────
        List<ConsommationMissionDto> details = missions.stream()
                .map(m -> computeConsommationMission(m, fillups))
                .toList();

        long nbAvec = details.stream().filter(ConsommationMissionDto::donneesCompletes).count();
        double taux = missions.isEmpty() ? 0.0 : (double) nbAvec / missions.size() * 100.0;

        // Σ consommation uniquement sur les missions cohérentes (consommation ≥ 0)
        double sumConso = details.stream()
                .filter(d -> d.donneesCompletes()
                        && d.consommationReelle() != null
                        && d.consommationReelle() >= 0)
                .mapToDouble(ConsommationMissionDto::consommationReelle)
                .sum();

        double sumH = details.stream()
                .filter(d -> d.donneesCompletes()
                        && d.nbHeures() != null && d.nbHeures() > 0)
                .mapToDouble(ConsommationMissionDto::nbHeures)
                .sum();
        Double ratioHMissions = sumH > 0 ? sumConso / sumH : null;

        return new BilanCarburantEnginDto(
                engin.getId(), engin.getCodeEngin(), engin.getModelEngin(),
                totalLitresPleins, coutTotal, deltaH, ratioHPleins,
                missions.size(), (int) nbAvec, taux,
                sumConso, ratioHMissions,
                details
        );
    }

    // ══════════════════════════════════════════════════════════════════════
    // Formule hybride — par mission
    // ══════════════════════════════════════════════════════════════════════

    /**
     * Formule :
     *   Δréservoir       = carbtDbt − carbtFin
     *   Σravitaillement  = Σ(litres) des pleins dont horamètre ∈ [compteurDbt, compteurFin]
     *   Consommation     = Δréservoir + Σravitaillement
     */
    private ConsommationMissionDto computeConsommationMission(Missions mission,
                                                              List<ConsommationCarburant> fillupsEngin) {

        Engins engin = (mission.getLocation() != null)
                ? mission.getLocation().getEngins() : null;

        Long distKm = null;
        if (mission.getKmDbtMission() != null && mission.getKmFinMission() != null) {
            distKm = mission.getKmFinMission() - mission.getKmDbtMission();
        }

        // Données incomplètes → retour partiel
        if (mission.getCarbtDbtMission() == null || mission.getCarbtFinMission() == null) {
            return new ConsommationMissionDto(
                    mission.getId(), mission.getCodeMission(), mission.getDateTravail(),
                    engin != null ? engin.getId() : null,
                    engin != null ? engin.getCodeEngin() : null,
                    engin != null ? engin.getModelEngin() : null,
                    mission.getCarbtDbtMission(), mission.getCarbtFinMission(),
                    null, null, null,
                    mission.getNbHeures(), null,
                    distKm, null, false);
        }

        // ── Δréservoir ───────────────────────────────────────────────────
        double delta = mission.getCarbtDbtMission() - mission.getCarbtFinMission();

        // ── Σ ravitaillements pendant la mission (via horamètre) ─────────
        double ravit = 0.0;
        if (mission.getCompteurDbtMission() != null && mission.getCompteurFinMission() != null) {
            double hDbt = mission.getCompteurDbtMission().doubleValue();
            double hFin = mission.getCompteurFinMission().doubleValue();
            ravit = fillupsEngin.stream()
                    .filter(f -> f.getHorametreAuPlein() != null
                            && f.getHorametreAuPlein() >= hDbt
                            && f.getHorametreAuPlein() <= hFin)
                    .mapToDouble(ConsommationCarburant::getQuantiteLitres)
                    .sum();
        }

        double consommation = delta + ravit;

        // ── Ratios ───────────────────────────────────────────────────────
        Double ratioLH = (mission.getNbHeures() != null && mission.getNbHeures() > 0)
                ? consommation / mission.getNbHeures() : null;

        Double ratioL100Km = null;
        if (distKm != null && distKm > 0) {
            ratioL100Km = consommation / distKm * 100.0;
        }

        return new ConsommationMissionDto(
                mission.getId(), mission.getCodeMission(), mission.getDateTravail(),
                engin != null ? engin.getId() : null,
                engin != null ? engin.getCodeEngin() : null,
                engin != null ? engin.getModelEngin() : null,
                mission.getCarbtDbtMission(), mission.getCarbtFinMission(),
                delta, ravit, consommation,
                mission.getNbHeures(), ratioLH,
                distKm, ratioL100Km, true);
    }
}
