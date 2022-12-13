package com.npardon.gmagroandroid.daos;

import android.util.Log;

import com.npardon.gmagroandroid.beans.Activite;
import com.npardon.gmagroandroid.beans.Intervenant;
import com.npardon.gmagroandroid.net.WSConnexionHTTPS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DaoActivite {
    private static DaoActivite instance = null;
    private final List<Activite> activites;

    private DaoActivite() {
        activites = new ArrayList<>();
    }

    public List<Activite> getLocalActivites() {
        return activites;
    }

    public static DaoActivite getInstance() {
        if (instance == null) {
            instance = new DaoActivite();
        }
        return instance;
    }

    public void getActivite(DelegateAsyncTask delegate) {
        String url = "uc=activite&action=getActivite";
        WSConnexionHTTPS wsConnexionHTTPS = new WSConnexionHTTPS() {
            @Override
            protected void onPostExecute(String s) {
                try {
                    traiterRetourGetActivite(s, delegate);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        wsConnexionHTTPS.execute(url);
    }

    private void traiterRetourGetActivite(String s, DelegateAsyncTask delegate) throws JSONException {
        JSONObject jsGlobal = new JSONObject(s);
        JSONArray ja = jsGlobal.getJSONArray("response");
        for (int i = 0; i < ja.length(); i++) {
            JSONObject jsResult = ja.getJSONObject(i);
            String code = jsResult.getString("code");
            String libelle = jsResult.getString("libelle");
            Activite ac = new Activite(code, libelle);
            activites.add(ac);
        }
        delegate.whenWSIsTerminated(s);
    }

    public void getActiviteById(String id, DelegateAsyncTask delegate) {
        String url = "uc=activite&action=getActiviteById&id=" + id;
        WSConnexionHTTPS wsConnexionHTTPS = new WSConnexionHTTPS() {
            @Override
            protected void onPostExecute(String s) {
                try {
                    traiterRetourGetActiviteById(s, delegate);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        wsConnexionHTTPS.execute(url);
    }

    private void traiterRetourGetActiviteById(String s, DelegateAsyncTask delegate) throws JSONException {
        JSONObject jsGlobal = new JSONObject(s);
        JSONArray ja = jsGlobal.getJSONArray("response");
        JSONObject jsResult = ja.getJSONObject(0);
        String code = jsResult.getString("code");
        String libelle = jsResult.getString("libelle");
        Activite ac = new Activite(libelle, code);
        Log.d("wat", "traiterRetourGetActiviteById: "+ac.getLibelle());
        delegate.whenWSIsTerminated(ac);
    }
}
