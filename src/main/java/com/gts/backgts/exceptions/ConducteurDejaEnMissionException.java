package com.gts.backgts.exceptions;

public class ConducteurDejaEnMissionException extends RuntimeException{
    public ConducteurDejaEnMissionException(String nomConducteur) {
        super("Le conducteur "+nomConducteur+" a déjà une mission en cours " +
                "Clôturez-la avant d'en créer une nouvelle.");
    }
}
