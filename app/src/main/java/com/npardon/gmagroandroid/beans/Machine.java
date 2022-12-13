package com.npardon.gmagroandroid.beans;

import java.io.Serializable;

public class Machine implements Serializable {
    private String codeMachine;
    private String dateFab;
    private String numSerie;
    private String codeSite;
    private String typeMachineCode;

    public Machine(String codeMachine, String dateFab, String numSerie, String codeSite, String typeMachineCode) {
        this.codeMachine = codeMachine;
        this.dateFab = dateFab;
        this.numSerie = numSerie;
        this.codeSite = codeSite;
        this.typeMachineCode = typeMachineCode;
    }

    public String getCodeMachine() {
        return codeMachine;
    }

    public String getDateFab() {
        return dateFab;
    }

    public String getNumSerie() {
        return numSerie;
    }

    public String getCodeSite() {
        return codeSite;
    }

    public String getTypeMachineCode() {
        return typeMachineCode;
    }
}
