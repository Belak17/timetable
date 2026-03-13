package com.belak.timetable.enumeration;

public enum Departement {

    PHY("PHY", "Physique"),
    CHI("CHI", "Chimie"),
    MAT("MAT", "Mathématiques"),
    INF("INF", "Informatique"),
    SV("SV", "Sciences de la Vie"),
    ST("ST", "Sciences de la Terre"),
    UT("UT", "Unité Transversale");

    private final String code;
    private final String libelle;

    Departement(String code, String libelle) {
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
