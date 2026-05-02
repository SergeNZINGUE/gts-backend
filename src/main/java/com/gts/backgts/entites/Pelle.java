package com.gts.backgts.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Pelle extends Engins {

    private Double profondeurFouille;
    private TypeTrain typeTrain; // CHENILLES, PNEUS
}
