package com.npardon.gmagroandroid.beans;

import java.io.Serializable;

public class CSOD implements Serializable {
    private String code;
    private String libelle;
    private CSODType csodType;

    public CSOD(String code, String libelle, CSODType csodType) {
        this.code = code;
        this.libelle = libelle;
        this.csodType = csodType;
    }

    public String getCode() {
        return code;
    }

    public String getLibelle() {
        return libelle;
    }

    public CSODType getCsodType() {
        return csodType;
    }
}
