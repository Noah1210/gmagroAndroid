package com.npardon.gmagroandroid.beans;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.npardon.gmagroandroid.R;
import com.npardon.gmagroandroid.daos.DaoActivite;
import com.npardon.gmagroandroid.daos.DaoMachine;
import com.npardon.gmagroandroid.daos.DelegateAsyncTask;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class InterventionsUnfinishedAdapter extends BaseAdapter {
    private Context context;
    private String msg = "";
    private List<Intervention> interventions;
    private Machine ma = null;

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
                }
            }
        });
        ImageView imgMachine = convertView.findViewById(R.id.imageMachine);
        DaoMachine.getInstance().getMachineById(i.getMachineCode(), new DelegateAsyncTask() {
            @Override
            public void whenWSIsTerminated(Object result) {
                ma = (Machine) result;
                if (ma != null) {
                    msg = msg +" "+ ma.getCodeMachine();
                    msg = msg.replaceAll("_", " ").toLowerCase();
                    msg = msg.replaceAll("0", "");
                    tvMachine.setText(msg);
                    Log.d("machine", "MachineTypeCode Is: "+ma.getTypeMachineCode());
                    Picasso.get().load("http://sio.jbdelasalle.com/~npardon/gmagro/photos/"+ma.getTypeMachineCode()+".jpg").into(imgMachine);
                }
            }
        });

        String dateHeure = i.getDhDerniereMaj();
        String[] dateHeureSplit = dateHeure.split("\\s+");
        tvDate.setText(transformDate(dateHeureSplit[0], dateHeureSplit[1]));
        return convertView;
    }

    private String transformDate(String date, String heure){
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        String msg = "";
        try {
            Date newDate = formatDate.parse(date);
            formatDate = new SimpleDateFormat("d MMMM yyyy", Locale.FRANCE);
            String laDate = formatDate.format(newDate);

            SimpleDateFormat formatHeure = new SimpleDateFormat("hh:mm:ss");
            Date newHeure = formatHeure.parse(heure);
            formatHeure = new SimpleDateFormat("HH:mm");
            String lHeure = formatHeure.format(newHeure);
            msg = "Le "+ laDate +" à " + lHeure;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return msg;
    }
}
