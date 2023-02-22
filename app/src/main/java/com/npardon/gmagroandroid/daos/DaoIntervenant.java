package com.npardon.gmagroandroid.daos;

import android.util.Log;

import com.npardon.gmagroandroid.beans.Intervenant;
import com.npardon.gmagroandroid.net.WSConnexionHTTPS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DaoIntervenant {
    private static DaoIntervenant instance = null;
    private final List<Intervenant> intervenants;
    private final List<Intervenant> intervenantsByInterv;
    private final List<Intervenant> intervenantsOutOfInterv;

    private DaoIntervenant() {
        intervenants = new ArrayList<>();
        intervenantsByInterv = new ArrayList<>();
        intervenantsOutOfInterv = new ArrayList<>();
    }

    public List<Intervenant> getLocalIntervenants() {
        return intervenants;
    }

    public List<Intervenant> getLocalIntervenantsByInterv() {
        return intervenantsByInterv;
    }

    public List<Intervenant> getLocalIntervenantsOutOfInterv() {
        return intervenantsOutOfInterv;
    }

    public static DaoIntervenant getInstance() {
        if (instance == null) {
            instance = new DaoIntervenant();
        }
        return instance;
    }

    public void getConnexion(String login, String mdp, DelegateAsyncTask delegate) {
        String url = "uc=intervenants&action=connexion&loginInterv=" + login + "&md5Password=" + mdp;
        WSConnexionHTTPS wsConnexionHTTPS = new WSConnexionHTTPS() {
            @Override
            protected void onPostExecute(String s) {
                try {
                    traiterRetourGetConnexion(s, delegate);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        wsConnexionHTTPS.execute(url);
    }
    private void traiterRetourGetConnexion(String s, DelegateAsyncTask delegate) throws JSONException {
        JSONObject jo = new JSONObject(s);
        Intervenant in = null;
        if (jo.getBoolean("success")) {
            JSONObject response = jo.getJSONObject("response");
            String login = response.getString("loginInterv");
            String nom = response.getString("nomInterv");
            String prenom = response.getString("prenomInterv");
            String codeSite = response.getString("codeSite");
            String mail = response.getString("mail");
            String actifString = response.getString("actif");
            boolean actif = Boolean.parseBoolean(actifString);
            in = new Intervenant(login, nom, prenom, mail, actif, codeSite);
        }
        delegate.whenWSIsTerminated(in);
    }

    public void getIntervenantsByInterventionId(String id, DelegateAsyncTask delegate) {
        String url = "uc=intervenants&action=getIntervenantByIntervId&interventionId="+ id;
        WSConnexionHTTPS wsConnexionHTTPS = new WSConnexionHTTPS() {
            @Override
            protected void onPostExecute(String s) {
                try {
                    traiterRetourGetIntervenantsByInterventionId(s, delegate);
                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                }
            }
        };
        wsConnexionHTTPS.execute(url);
    }

    private void traiterRetourGetIntervenantsByInterventionId(String s, DelegateAsyncTask delegate) throws JSONException, ParseException {
        intervenantsByInterv.clear();
        JSONObject jsGlobal = new JSONObject(s);
        JSONArray ja = jsGlobal.getJSONArray("response");
        for(int i = 0; i< ja.length() ; i++){
            JSONObject jsResult = ja.getJSONObject(i);
            String loginInterv = jsResult.getString("loginInterv");
            String nomInterv = jsResult.getString("nomInterv");
            String prenomInterv = jsResult.getString("prenomInterv");
            String mail = jsResult.getString("mail");
            String actifString = jsResult.getString("actif");
            boolean actif = Boolean.parseBoolean(actifString);
            String codeSite = jsResult.getString("codeSite");
            String tempPre = jsResult.getString("tpsPasse");

            SimpleDateFormat formatHeure = new SimpleDateFormat("hh:mm:ss");
            Date newHeure = formatHeure.parse(tempPre);
            formatHeure = new SimpleDateFormat("HH:mm");
            String temps = formatHeure.format(newHeure);

            Intervenant in = new Intervenant(loginInterv, nomInterv, prenomInterv, mail, actif, codeSite, temps);
            intervenantsByInterv.add(in);

        }
        delegate.whenWSIsTerminated(s);
    }

    public void getIntervenants(DelegateAsyncTask delegate) {
        String url = "uc=intervenants&action=getIntervenant";
        WSConnexionHTTPS wsConnexionHTTPS = new WSConnexionHTTPS() {
            @Override
            protected void onPostExecute(String s) {
                try {
                    traiterRetourGetIntervenants(s, delegate);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        wsConnexionHTTPS.execute(url);
    }

    private void traiterRetourGetIntervenants(String s, DelegateAsyncTask delegate) throws JSONException {
        intervenants.clear();
        JSONObject jsGlobal = new JSONObject(s);
        JSONArray ja = jsGlobal.getJSONArray("response");
        for(int i = 0; i< ja.length() ; i++){
            JSONObject jsResult = ja.getJSONObject(i);
            String loginInterv = jsResult.getString("loginInterv");
            String nomInterv = jsResult.getString("nomInterv");
            String prenomInterv = jsResult.getString("prenomInterv");
            String mail = jsResult.getString("mail");
            String actifString = jsResult.getString("actif");
            boolean actif = Boolean.parseBoolean(actifString);
            String codeSite = jsResult.getString("codeSite");

            Intervenant in = new Intervenant(loginInterv, nomInterv, prenomInterv, mail, actif, codeSite);
            intervenants.add(in);
        }
        delegate.whenWSIsTerminated(s);
    }


    public void getIntervenantOutOfIntervId(String id, DelegateAsyncTask delegate) {
        String url = "uc=intervenants&action=getIntervenantOutOfIntervId&interventionId="+ id;
        WSConnexionHTTPS wsConnexionHTTPS = new WSConnexionHTTPS() {
            @Override
            protected void onPostExecute(String s) {
                try {
                    traiterRetourGetIntervenantOutOfIntervId(s, delegate);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        wsConnexionHTTPS.execute(url);
    }

    private void traiterRetourGetIntervenantOutOfIntervId(String s, DelegateAsyncTask delegate) throws JSONException {
        intervenantsOutOfInterv.clear();
        JSONObject jsGlobal = new JSONObject(s);
        JSONArray ja = jsGlobal.getJSONArray("response");
        for(int i = 0; i< ja.length() ; i++){
            JSONObject jsResult = ja.getJSONObject(i);
            String loginInterv = jsResult.getString("loginInterv");
            String nomInterv = jsResult.getString("nomInterv");
            String prenomInterv = jsResult.getString("prenomInterv");
            String mail = jsResult.getString("mail");
            String actifString = jsResult.getString("actif");
            boolean actif = Boolean.parseBoolean(actifString);
            String codeSite = jsResult.getString("codeSite");

            Intervenant in = new Intervenant(loginInterv, nomInterv, prenomInterv, mail, actif, codeSite);
            intervenantsOutOfInterv.add(in);
        }
        delegate.whenWSIsTerminated(s);
    }
}
