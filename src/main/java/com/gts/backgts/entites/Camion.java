package com.gts.backgts.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


@Table (name = "CAMIONS")

public class Camion extends Engins{

    private Double chargeUtile;
    private Integer nbEssieux;
    private Double volumeBenne;


}
