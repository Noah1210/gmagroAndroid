package com.npardon.gmagroandroid.daos;

import android.util.Log;

import com.npardon.gmagroandroid.beans.Activite;
import com.npardon.gmagroandroid.beans.CSOD;
import com.npardon.gmagroandroid.beans.CSODType;
import com.npardon.gmagroandroid.net.WSConnexionHTTPS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DaoCSOD {
    private static DaoCSOD instance = null;
    private final List<CSOD> cds;
    private final List<CSOD> cos;
    private final List<CSOD> sds;
    private final List<CSOD> sos;

    private DaoCSOD() {
        cds = new ArrayList<>();
        cos = new ArrayList<>();
        sds = new ArrayList<>();
        sos = new ArrayList<>();
    }

    public List<CSOD> getLocalCds() {
        return cds;
    }

    public List<CSOD> getLocalCos() {
        return cos;
    }

    public List<CSOD> getLocalSds() {
        return sds;
    }

    public List<CSOD> getLocalSos() {
        return sos;
    }

    public static DaoCSOD getInstance() {
        if (instance == null) {
            instance = new DaoCSOD();
        }
        return instance;
    }

    public void getCSOD(DelegateAsyncTask delegate) {
        String url = "uc=csod&action=getCSOD";
        WSConnexionHTTPS wsConnexionHTTPS = new WSConnexionHTTPS() {
            @Override
            protected void onPostExecute(String s) {
                try {
                    traiterRetourGetCSOD(s, delegate);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        wsConnexionHTTPS.execute(url);
    }

    private void traiterRetourGetCSOD(String s, DelegateAsyncTask delegate) throws JSONException {
        JSONObject jsGlobal = new JSONObject(s);
        JSONArray ja = jsGlobal.getJSONArray("response");
        for (int i = 0; i < ja.length(); i++) {
            JSONObject jsResult = ja.getJSONObject(i);
            Log.d("TEST JSON", "TEST JSON: "+jsResult);
            switch (jsResult.getString("cd")){
                case "cd":
                    String code_cd = jsResult.getString("code");
                    String libelle_cd = jsResult.getString("libelle");
                    CSODType type_cd = CSODType.CD;
                    CSOD cd = new CSOD(code_cd, libelle_cd, type_cd);
                    cds.add(cd);
                    break;
                case "co":
                    String code_co = jsResult.getString("code");
                    String libelle_co = jsResult.getString("libelle");

                    CSODType type_co = CSODType.CO;
                    Log.d("TAG", "TYPE: "+type_co);
                    CSOD co = new CSOD(code_co, libelle_co, type_co);
                    Log.d("TAG", "OBJECT CO: "+co.getLibelle());
                    cos.add(co);
                    //Log.d("TAG", "WOOOORK CO: "+cos.get(0).getLibelle());
                    break;
                case "so":
                    String code_so = jsResult.getString("code");
                    String libelle_so = jsResult.getString("libelle");
                    CSODType type_so = CSODType.SO;
                    CSOD so = new CSOD(code_so, libelle_so, type_so);
                    sos.add(so);
                    break;
                case "sd":
                    String code_sd = jsResult.getString("code");
                    String libelle_sd = jsResult.getString("libelle");
                    //Log.d("TAG", "traiterSD: "+libelle_sd);
                    CSODType type_sd = CSODType.SD;
                    CSOD sd = new CSOD(code_sd, libelle_sd, type_sd);

                    sds.add(sd);
                    break;
            }
            Log.d("TAG", "WOOOORK CO: "+cos.get(i).getLibelle());
            //Log.d("TAG", "traiterCD: "+cds.get(0).getLibelle());
            //Log.d("TAG", "WorkOrGae: "+cos.get(0).getLibelle());
        }
        delegate.whenWSIsTerminated(s);
    }

//    public void getActiviteById(String id, DelegateAsyncTask delegate) {
//        String url = "uc=activite&action=getActiviteById&id=" + id;
//        WSConnexionHTTPS wsConnexionHTTPS = new WSConnexionHTTPS() {
//            @Override
//            protected void onPostExecute(String s) {
//                try {
//                    traiterRetourGetActiviteById(s, delegate);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//        wsConnexionHTTPS.execute(url);
//    }
//
//    private void traiterRetourGetActiviteById(String s, DelegateAsyncTask delegate) throws JSONException {
//        JSONObject jsGlobal = new JSONObject(s);
//        JSONArray ja = jsGlobal.getJSONArray("response");
//        JSONObject jsResult = ja.getJSONObject(0);
//        String code = jsResult.getString("code");
//        String libelle = jsResult.getString("libelle");
//        Activite ac = new Activite(libelle, code);
//        Log.d("wat", "traiterRetourGetActiviteById: "+ac.getLibelle());
//        delegate.whenWSIsTerminated(ac);
//    }
}
