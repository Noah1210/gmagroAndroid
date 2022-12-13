package com.npardon.gmagroandroid.beans;

import java.io.Serializable;

public class Intervention implements Serializable {
    private String id;
    private String dhDebut;
    private String dhFin;
    private String commentaire;
    private String tempsArret;
    private boolean changementOrgane;
    private boolean perte;
    private String dhCreation;
    private String dhDerniereMaj;
    private String intervenantLogin;
    private String activiteCode;
    private String machineCode;
    private String causeDefautCode;
    private String causeObjetCode;
    private String symptomeDefaultCode;
    private String symptomeObjetCode;

    public Intervention(String id, String dhDebut, String dhFin, String commentaire, String tempsArret, boolean changementOrgane, boolean perte, String dhCreation, String dhDerniereMaj, String intervenantLogin, String activiteCode, String machineCode, String causeDefautCode, String causeObjetCode, String symptomeDefaultCode, String symptomeObjetCode) {
        this.id = id;
        this.dhDebut = dhDebut;
        this.dhFin = dhFin;
        this.commentaire = commentaire;
        this.tempsArret = tempsArret;
        this.changementOrgane = changementOrgane;
        this.perte = perte;
        this.dhCreation = dhCreation;
        this.dhDerniereMaj = dhDerniereMaj;
        this.intervenantLogin = intervenantLogin;
        this.activiteCode = activiteCode;
        this.machineCode = machineCode;
        this.causeDefautCode = causeDefautCode;
        this.causeObjetCode = causeObjetCode;
        this.symptomeDefaultCode = symptomeDefaultCode;
        this.symptomeObjetCode = symptomeObjetCode;
    }

    public Intervention(String dhDebut, String dhFin, String commentaire, String tempsArret, boolean changementOrgane, boolean perte, String dhCreation, String dhDerniereMaj, String intervenantLogin, String activiteCode, String machineCode, String causeDefautCode, String causeObjetCode, String symptomeDefaultCode, String symptomeObjetCode) {
        this.dhDebut = dhDebut;
        this.dhFin = dhFin;
        this.commentaire = commentaire;
        this.tempsArret = tempsArret;
        this.changementOrgane = changementOrgane;
        this.perte = perte;
        this.dhCreation = dhCreation;
        this.dhDerniereMaj = dhDerniereMaj;
        this.intervenantLogin = intervenantLogin;
        this.activiteCode = activiteCode;
        this.machineCode = machineCode;
        this.causeDefautCode = causeDefautCode;
        this.causeObjetCode = causeObjetCode;
        this.symptomeDefaultCode = symptomeDefaultCode;
        this.symptomeObjetCode = symptomeObjetCode;
    }

    public String getId() {
        return id;
    }

    public String getDhDebut() {
        return dhDebut;
    }

    public String getDhFin() {
        return dhFin;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public String getTempsArret() {
        return tempsArret;
    }

    public boolean isChangementOrgane() {
        return changementOrgane;
    }

    public boolean isPerte() {
        return perte;
    }

    public String getDhCreation() {
        return dhCreation;
    }

    public String getDhDerniereMaj() {
        return dhDerniereMaj;
    }

    public String getIntervenantLogin() {
        return intervenantLogin;
    }

    public String getActiviteCode() {
        return activiteCode;
    }

    public String getMachineCode() {
        return machineCode;
    }

    public String getCauseDefautCode() {
        return causeDefautCode;
    }

    public String getCauseObjetCode() {
        return causeObjetCode;
    }

    public String getSymptomeDefaultCode() {
        return symptomeDefaultCode;
    }

    public String getSymptomeObjetCode() {
        return symptomeObjetCode;
    }
}
