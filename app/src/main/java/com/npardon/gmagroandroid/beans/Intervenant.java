package com.npardon.gmagroandroid.beans;

import java.io.Serializable;

public class Intervenant implements Serializable {
    private String login;
    private String password;
    private String nom;
    private String prenom;
    private String mail;
    private boolean actif;
    private String codeSite;


    public Intervenant(String login, String password, String nom, String prenom, String mail, String codeSite) {
        this.login = login;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.codeSite = codeSite;
    }

    public Intervenant(String login, String nom, String prenom, String mail, boolean actif, String codeSite) {
        this.login = login;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.actif = actif;
        this.codeSite = codeSite;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getMail() {
        return mail;
    }

    public boolean isActif() {
        return actif;
    }

    public String getCodeSite() {
        return codeSite;
    }
}
