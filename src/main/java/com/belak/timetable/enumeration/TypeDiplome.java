package com.belak.timetable.enumeration;

public enum TypeDiplome {

    DOCTORAT("doctorat", "Doctorat"),
    INGENIERIE("ingenierie", "Ingénierie"),
    LICENCE("licence", "Licence"),
    LICENCE_APPLIQUEE("licence_appliquee", "Licence Appliquée"),
    LICENCE_FONDAMENTALE("licence_fondamentale", "Licence Fondamentale"),
    MAITRISE("maitrise", "Maîtrise"),
    MASTER_PRO("master_pro", "Master Professionnel"),
    MASTER_RECHERCHE("master_recherche", "Master de Recherche"),
    PREPARATOIRE("preparatoire", "Préparatoire");

    private final String code;
    private final String libelle;

    TypeDiplome(String code, String libelle) {
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
