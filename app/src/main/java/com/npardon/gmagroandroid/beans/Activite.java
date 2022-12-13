package com.npardon.gmagroandroid.beans;

import java.io.Serializable;

public class Activite implements Serializable {
    private String code;
    private String libelle;

    public Activite(String libelle, String code) {
        this.code = code;
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }

    public String getCode() {
        return code;
    }
}
