package com.gts.backgts.entites;

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

public class TombereauMinier extends Engins {

    private Double chargeUtile;
    private Double volumeBenne;
    private Boolean isElectricDrive;

}
