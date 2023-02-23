package com.npardon.gmagroandroid.daos;

import android.util.Log;

import com.npardon.gmagroandroid.beans.Intervenant;
import com.npardon.gmagroandroid.beans.Intervention;
import com.npardon.gmagroandroid.net.WSConnexionHTTPS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DaoIntervention {
    private static DaoIntervention instance = null;
    private final List<Intervention> interventions;

    private DaoIntervention() {
        interventions = new ArrayList<>();
    }


    public List<Intervention> getLocalInterventions() {
        return interventions;
    }

    public static DaoIntervention getInstance() {
        if (instance == null) {
            instance = new DaoIntervention();
        }
        return instance;
    }

    public void getInterventions(DelegateAsyncTask delegate) {
        String url = "uc=interventions&action=getInterventions";
        WSConnexionHTTPS wsConnexionHTTPS = new WSConnexionHTTPS() {
            @Override
            protected void onPostExecute(String s) {
                try {
                    traiterRetourGetInterventions(s, delegate);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        wsConnexionHTTPS.execute(url);
    }
    private void traiterRetourGetInterventions(String s, DelegateAsyncTask delegate) throws JSONException {
        interventions.clear();
        JSONObject jsGlobal = new JSONObject(s);
        JSONArray ja = jsGlobal.getJSONArray("response");
        for(int i = 0; i< ja.length() ; i++){
            JSONObject jsResult = ja.getJSONObject(i);
            String id = jsResult.getString("id");
            String dhDebut = jsResult.getString("dhDebut");
            String dhFin = jsResult.getString("dhFin");
            String commentaire = jsResult.getString("commentaire");
            String tempsArret = jsResult.getString("tempsArret");
            String changementOrganeString = jsResult.getString("changementOrgane");
            String perteString = jsResult.getString("perte");
            boolean changementOrgane = false;
            boolean perte = false;
            if(changementOrganeString.equals("1")){
                changementOrgane = true;
            }
            if(perteString.equals("1")){
                perte = true;
            }
            String dhCreation = jsResult.getString("dhCreation");
            String dhDerniereMaj = jsResult.getString("dhDerniereMaj");
            String intervenantLogin = jsResult.getString("intervenantLogin");
            String activiteCode = jsResult.getString("activiteCode");
            String machineCode = jsResult.getString("machineCode");
            String causeDefautCode = jsResult.getString("causeDefautCode");
            String causeObjetCode = jsResult.getString("causeObjetCode");
            String symptomeDefaultCode = jsResult.getString("symptomeDefaultCode");
            String symptomeObjetCode = jsResult.getString("symptomeObjetCode");
            Intervention in = new Intervention(id, dhDebut, dhFin, commentaire, tempsArret, changementOrgane, perte, dhCreation, dhDerniereMaj, intervenantLogin, activiteCode, machineCode,causeDefautCode, causeObjetCode, symptomeDefaultCode, symptomeObjetCode);
            interventions.add(in);
        }
        delegate.whenWSIsTerminated(s);
    }
}
