package com.gts.backgts.entites;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Grue extends Engins {

    private Double capaciteLevageMax; // En tonnes

    private Double longueurFleche; // En mètres

    private Double hauteurSousCrochet;

    private Double momentLevage; // En Tonne-Mètre (t.m)

    private Boolean estMobile; // True pour grue sur porteur, False pour grue à tour


}