package com.gts.backgts.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "FOREUSES_MINIERES")
public class Foreuse extends Engins {
    private Double diametreForage;
    private Double profondeurMax;
    private String typePerforation;

}

