package com.gts.backgts.entites;

import com.gts.backgts.enums.TypeTrain;
import jakarta.persistence.Entity;
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
