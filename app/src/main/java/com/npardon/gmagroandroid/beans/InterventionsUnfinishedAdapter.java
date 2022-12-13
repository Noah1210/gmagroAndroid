package com.npardon.gmagroandroid.beans;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.npardon.gmagroandroid.R;
import com.npardon.gmagroandroid.daos.DaoActivite;
import com.npardon.gmagroandroid.daos.DaoMachine;
import com.npardon.gmagroandroid.daos.DelegateAsyncTask;
import com.npardon.gmagroandroid.ui.Connexion;
import com.npardon.gmagroandroid.ui.LesInterventions;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.crypto.Mac;

public class InterventionsUnfinishedAdapter extends BaseAdapter {
    private Context context;
    private String msg = "";
    private ImageView imgMachine;
    private List<Intervention> interventions;

    public InterventionsUnfinishedAdapter(Context context, List<Intervention> interventions) {
        this.interventions = interventions;
        this.context = context;
    }

    @Override
    public int getCount() {
        return interventions.size();
    }

    @Override
    public Object getItem(int position) {
        return interventions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_lv_interventions_unfinished, parent, false);
        }
        Intervention i = interventions.get(position);
        TextView tvMachine = convertView.findViewById(R.id.tvInterventionActivite);
        TextView tvDate = convertView.findViewById(R.id.tvInterventionDate);
        DaoActivite.getInstance().getActiviteById(i.getActiviteCode(), new DelegateAsyncTask() {
            @Override
            public void whenWSIsTerminated(Object result) {
                Activite ac = (Activite) result;
                if (ac != null) {
                    msg = ac.getLibelle();
                    Log.d("TAG", "LE LIBELLE: "+ac.getLibelle());

                }
            }
        });

        DaoMachine.getInstance().getMachineById(i.getMachineCode(), new DelegateAsyncTask() {
            @Override
            public void whenWSIsTerminated(Object result) {
                Machine ma = (Machine) result;
                if (ma != null) {
                    msg = msg +" "+ ma.getCodeMachine();
                    tvMachine.setText(msg);
                }
            }
        });
        imgMachine = convertView.findViewById(R.id.imageMachine);
        tvDate.setText(i.getDhDerniereMaj());
        return convertView;
    }
}
