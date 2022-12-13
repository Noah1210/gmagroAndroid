package com.npardon.gmagroandroid.daos;

import android.graphics.Bitmap;

import com.npardon.gmagroandroid.beans.Machine;
import com.npardon.gmagroandroid.net.WSConnexionHTTPS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DaoMachine {
    private static DaoMachine instance = null;
    private final List<Machine> machines;

    private DaoMachine() {
        machines = new ArrayList<>();
    }

    public List<Machine> getLocalMachines() {
        return machines;
    }

    public static DaoMachine getInstance() {
        if (instance == null) {
            instance = new DaoMachine();
        }
        return instance;
    }

    public void getMachine(DelegateAsyncTask delegate) {
        String url = "uc=machine&action=getMachine";
        WSConnexionHTTPS wsConnexionHTTPS = new WSConnexionHTTPS() {
            @Override
            protected void onPostExecute(String s) {
                try {
                    traiterRetourGetMachine(s, delegate);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        wsConnexionHTTPS.execute(url);
    }

    private void traiterRetourGetMachine(String s, DelegateAsyncTask delegate) throws JSONException {
        JSONObject jsGlobal = new JSONObject(s);
        JSONArray ja = jsGlobal.getJSONArray("response");
        for (int i = 0; i < ja.length(); i++) {
            JSONObject jsResult = ja.getJSONObject(i);
            String codeMachine = jsResult.getString("codeMachine");
            String dateFab = jsResult.getString("dateFab");
            String numSerie = jsResult.getString("numSerie");
            String codeSite = jsResult.getString("codeSite");
            String typeMachineCode = jsResult.getString("typeMachineCode");
            Machine ma = new Machine(codeMachine, dateFab, numSerie, codeSite, typeMachineCode);
            machines.add(ma);
        }
        delegate.whenWSIsTerminated(s);
    }

    public void getMachineById(String id, DelegateAsyncTask delegate) {
        String url = "uc=machine&action=getMachineById&id=" + id;
        WSConnexionHTTPS wsConnexionHTTPS = new WSConnexionHTTPS() {
            @Override
            protected void onPostExecute(String s) {
                try {
                    traiterRetourGetMachineById(s, delegate);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        wsConnexionHTTPS.execute(url);
    }

    private void traiterRetourGetMachineById(String s, DelegateAsyncTask delegate) throws JSONException {
        JSONObject jsGlobal = new JSONObject(s);
        JSONArray ja = jsGlobal.getJSONArray("response");
        JSONObject jsResult = ja.getJSONObject(0);
        String codeMachine = jsResult.getString("codeMachine");
        String dateFab = jsResult.getString("dateFab");
        String numSerie = jsResult.getString("numSerie");
        String codeSite = jsResult.getString("codeSite");
        String typeMachineCode = jsResult.getString("typeMachineCode");
        Machine ma = new Machine(codeMachine, dateFab, numSerie, codeSite, typeMachineCode);
        delegate.whenWSIsTerminated(ma);
    }

}
