package com.npardon.gmagroandroid.daos;

import com.npardon.gmagroandroid.beans.Intervenant;
import com.npardon.gmagroandroid.net.WSConnexionHTTPS;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DaoIntervenant {
    private static DaoIntervenant instance = null;
    private final List<Intervenant> intervenants;

    private DaoIntervenant() {
        intervenants = new ArrayList<>();
    }

    public List<Intervenant> getLocalIntervenants() {
        return intervenants;
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
}
