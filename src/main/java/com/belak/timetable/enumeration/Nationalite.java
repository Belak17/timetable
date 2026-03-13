package com.belak.timetable.enumeration;

public enum Nationalite {

    AF("AF", "Afghanistan", "Afghane"),
    ZA("ZA", "Afrique du Sud", "Sud-africaine"),
    AL("AL", "Albanie", "Albanaise"),
    DZ("DZ", "Algérie", "Algérienne"),
    DE("DE", "Allemagne", "Allemande"),
    AD("AD", "Andorre", "Andorrane"),
    AO("AO", "Angola", "Angolaise"),
    AR("AR", "Argentine", "Argentine"),
    AM("AM", "Arménie", "Arménienne"),
    AU("AU", "Australie", "Australienne"),
    AT("AT", "Autriche", "Autrichienne"),
    AZ("AZ", "Azerbaïdjan", "Azerbaïdjanaise"),
    BS("BS", "Bahamas", "Bahamienne"),
    BH("BH", "Bahreïn", "Bahreïnienne"),
    BD("BD", "Bangladesh", "Bangladaise"),
    BE("BE", "Belgique", "Belge"),
    BJ("BJ", "Bénin", "Béninoise"),
    BR("BR", "Brésil", "Brésilienne"),
    BG("BG", "Bulgarie", "Bulgare"),
    CM("CM", "Cameroun", "Camerounaise"),
    CA("CA", "Canada", "Canadienne"),
    CN("CN", "Chine", "Chinoise"),
    CO("CO", "Colombie", "Colombienne"),
    HR("HR", "Croatie", "Croate"),
    CU("CU", "Cuba", "Cubaine"),
    DK("DK", "Danemark", "Danoise"),
    EG("EG", "Égypte", "Égyptienne"),
    ES("ES", "Espagne", "Espagnole"),
    US("US", "États-Unis", "Américaine"),
    FI("FI", "Finlande", "Finlandaise"),
    FR("FR", "France", "Française"),
    DEU("DEU", "Allemagne", "Allemande"),
    GR("GR", "Grèce", "Hellénique"),
    IN("IN", "Inde", "Indienne"),
    ID("ID", "Indonésie", "Indonésienne"),
    IR("IR", "Iran", "Iranienne"),
    IQ("IQ", "Irak", "Irakienne"),
    IE("IE", "Irlande", "Irlandaise"),
    IS("IS", "Islande", "Islandaise"),
    IT("IT", "Italie", "Italienne"),
    JP("JP", "Japon", "Japonaise"),
    JO("JO", "Jordanie", "Jordanienne"),
    KE("KE", "Kenya", "Kenyane"),
    LB("LB", "Liban", "Libanaise"),
    LY("LY", "Libye", "Libyenne"),
    LU("LU", "Luxembourg", "Luxembourgeoise"),
    MA("MA", "Maroc", "Marocaine"),
    MX("MX", "Mexique", "Mexicaine"),
    NL("NL", "Pays-Bas", "Néerlandaise"),
    NO("NO", "Norvège", "Norvégienne"),
    PK("PK", "Pakistan", "Pakistanaise"),
    PE("PE", "Pérou", "Péruvienne"),
    PH("PH", "Philippines", "Philippine"),
    PL("PL", "Pologne", "Polonaise"),
    PT("PT", "Portugal", "Portugaise"),
    QA("QA", "Qatar", "Qatarienne"),
    RO("RO", "Roumanie", "Roumaine"),
    RU("RU", "Russie", "Russe"),
    SA("SA", "Arabie saoudite", "Saoudienne"),
    SN("SN", "Sénégal", "Sénégalaise"),
    SG("SG", "Singapour", "Singapourienne"),
    ZA2("ZA2", "Afrique du Sud", "Sud-africaine"),
    KR("KR", "Corée du Sud", "Sud-coréenne"),
    SE("SE", "Suède", "Suédoise"),
    CH("CH", "Suisse", "Suisse"),
    SY("SY", "Syrie", "Syrienne"),
    TH("TH", "Thaïlande", "Thaïlandaise"),
    TN("TN", "Tunisie", "Tunisienne"),
    TR("TR", "Turquie", "Turque"),
    UA("UA", "Ukraine", "Ukrainienne"),
    AE("AE", "Émirats arabes unis", "Émirienne"),
    GB("GB", "Royaume-Uni", "Britannique"),
    VN("VN", "Viêt Nam", "Vietnamienne"),
    YE("YE", "Yémen", "Yéménite");

    private final String code;
    private final String pays;
    private final String nationalite;

    Nationalite(String code, String pays, String nationalite) {
        this.code = code;
        this.pays = pays;
        this.nationalite = nationalite;
    }

    public String getCode() {
        return code;
    }

    public String getPays() {
        return pays;
    }

    public String getNationalite() {
        return nationalite;
    }
}