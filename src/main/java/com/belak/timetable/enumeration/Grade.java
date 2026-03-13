package com.belak.timetable.enumeration;

public enum Grade {

    ASSISTANT("AS", "Assistant"),
    ASSISTANT_CONTRACTUEL("ASC", "Assistant Contractuel"),
    EXPERT("EX", "Expert"),
    MAITRE_DE_CONFERENCES("MC", "Maître de conférences"),
    MAITRE_ASSISTANT("MA", "Maître Assistant"),
    PROF_ENS_SECONDAIRE("PES", "Prof d'enseignement secondaire"),
    PROFESSEUR("PR", "Professeur"),
    TECHNOLOGUE("T", "Technologue"),
    PES_LOWER("ps", "PES"), // attention code 'ps' minuscule
    CONTRACTUEL("CR", "Contractuel"),
    C_EXPERT("EC", "C-EXPERT"),
    VACATAIRE("VA", "Vacataire"),
    EXPERT_COMPTABLE("EX_COMPT", "Expert Comptable");

    private final String code;
    private final String libelle;

    Grade(String code, String libelle) {
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
