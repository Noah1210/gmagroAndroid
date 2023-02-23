package com.npardon.gmagroandroid.beans;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

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
import java.util.TimeZone;

public class IntervenantsUpdateAdapter extends BaseAdapter {
    private Context context;
    private String msg = "";
    private List<Intervenant> intervenants;
    private Machine ma = null;
    private int hour, min ;
    private TextView tvTempsAdded;
    private Activity parentActivity;
    private String previousDate = "00:00";

    public IntervenantsUpdateAdapter(Context context, List<Intervenant> intervenants, Activity parentActivity) {
        this.intervenants = intervenants;
        this.context = context;
        this.parentActivity = parentActivity;

    }

    @Override
    public int getCount() {
        return intervenants.size();
    }

    @Override
    public Object getItem(int position) {
        return intervenants.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_lv_intervenants_update, parent, false);
        }


        Intervenant in = intervenants.get(position);
        TextView tvInterv = convertView.findViewById(R.id.txIntevenants);
        TextView tvTemps = convertView.findViewById(R.id.txIntevenantsTemps);
        tvTempsAdded = convertView.findViewById(R.id.txIntevenantsTempsAdded);

        tvInterv.setText(in.getPrenom()+ " "+in.getNom());
        tvTemps.setText(in.getTemps());
        tvTempsAdded.setText("00:00");
        tvTempsAdded.setOnClickListener(v -> {
            showTimePickerDialog(v, position);
        });

        return convertView;
    }

    private void showTimePickerDialog(View v, int position) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMin) {
                hour = selectedHour;
                min = selectedMin;
                if(!tvTempsAdded.equals("00:00")){
                    tvTempsAdded.setText("00:00");
                }
                tvTempsAdded.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, min));
                try {
                    AddTimeToList(position);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        };
        int style = AlertDialog.THEME_HOLO_DARK;
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, style, onTimeSetListener, hour, min, true);
        timePickerDialog.show();
    }

    private void AddTimeToList(int position) throws ParseException {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        if(previousDate.equals("00:00")){
            previousDate = intervenants.get(position).getTemps();
        }
        if(!previousDate.equals("00:00")){
            intervenants.get(position).setTemps(previousDate);
        }
        Date date1 = timeFormat.parse(intervenants.get(position).getTemps());
        Date date2 = timeFormat.parse((String) tvTempsAdded.getText());

        long sum = date1.getTime() + date2.getTime();
        String date = timeFormat.format(new Date(sum));
        intervenants.get(position).setTemps(date);
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
