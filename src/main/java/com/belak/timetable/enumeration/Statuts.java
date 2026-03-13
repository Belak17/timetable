package com.belak.timetable.enumeration;

public enum Statuts {

    AUTRE("AUTRE", "Autre"),
    VACATAIRE("VA", "Vacataire"),
    PERMANENT("PR", "Permanent"),
    CONTRACTUELLE("C", "Contractuel(le)"),
    CONTRAT_EXPERT("CEXP", "Contrat expert"),
    P_DETACHE("D", "P Détaché"),
    NON_ACTIF("NA", "Non Actif"),
    CONTRACTUEL("CR", "Contractuel"),
    EXPERT_COMPTABLE("EC", "Expert comptable");

    private final String code;
    private final String libelle;

    Statuts(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
    }

    public String getCode() {
        return code;
    }

    public String getLibelle() {
        return libelle;
    }
}
