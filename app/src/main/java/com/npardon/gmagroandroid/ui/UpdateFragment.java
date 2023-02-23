package com.npardon.gmagroandroid.ui;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import android.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.npardon.gmagroandroid.R;
import com.npardon.gmagroandroid.beans.CSOD;
import com.npardon.gmagroandroid.beans.CSODType;
import com.npardon.gmagroandroid.beans.Intervenant;
import com.npardon.gmagroandroid.beans.IntervenantsUpdateAdapter;
import com.npardon.gmagroandroid.beans.Intervention;
import com.npardon.gmagroandroid.beans.InterventionsUnfinishedAdapter;
import com.npardon.gmagroandroid.beans.Machine;
import com.npardon.gmagroandroid.daos.DaoCSOD;
import com.npardon.gmagroandroid.daos.DaoIntervenant;
import com.npardon.gmagroandroid.daos.DaoIntervention;
import com.npardon.gmagroandroid.daos.DaoMachine;
import com.npardon.gmagroandroid.daos.DelegateAsyncTask;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    List<CSOD> csodId = new ArrayList<>();
    private int hour, min;
    private IntervenantsUpdateAdapter intervenantsUpdateAdapter;
    private TextView txUpdateAddTemps, txHeureFin, txTpsMachineAdd;
    private Spinner spinnerInterv;
    private List<Intervenant> intervenants, intervenantsOutOfInterv;
    private ArrayAdapter<Intervenant> intervAdapter;
    private CheckBox cbIntervStatus, cbMachineOff, cbChangOrgane, cbPertes;
    private Calendar date;
    private String dateHour;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UpdateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpdateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdateFragment newInstance(String param1, String param2) {
        UpdateFragment fragment = new UpdateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_update, container, false);

        Bundle bundle = this.getArguments();
        Intervention in = (Intervention) bundle.getSerializable("intervention");
        String activite = bundle.getString("activite");
        String date = bundle.getString("date");
        TextView title = (TextView) v.findViewById(R.id.txActivite);
        TextView titleDate = (TextView) v.findViewById(R.id.txDate);
        TextView causeSymp = (TextView) v.findViewById(R.id.txSymptomes);
        TextView causeDefaut = (TextView) v.findViewById(R.id.txDefauts);
        TextView commentaire = (TextView) v.findViewById(R.id.txComment);

        ImageView imgMachine = v.findViewById(R.id.imageMachine);

        cbIntervStatus = ((CheckBox) v.findViewById(R.id.cbIntervStatus));
        cbMachineOff = ((CheckBox) v.findViewById(R.id.cbMachineOff));
        cbChangOrgane = ((CheckBox) v.findViewById(R.id.cbChangOrgane));
        cbPertes = ((CheckBox) v.findViewById(R.id.cbPertes));

        txTpsMachineAdd = ((TextView) v.findViewById(R.id.txMachineOff));
        txHeureFin = ((TextView) v.findViewById(R.id.txIntervStatus));
        ListView lv = ((ListView) v.findViewById(R.id.lvIntervenantsUpdate));
        spinnerInterv = ((Spinner) v.findViewById(R.id.spinnerUpdateIntervenant));
        txUpdateAddTemps = (TextView) v.findViewById(R.id.txUpdateAddTemps);


        titleDate.setText(date);
        title.setText(activite);

        DaoCSOD.getInstance().getCSODById(in.getId(), new DelegateAsyncTask() {
            @Override
            public void whenWSIsTerminated(Object result) {
                csodId = (List<CSOD>) result;
                causeSymp.setText(csodId.get(3).getLibelle() + " " + csodId.get(2).getLibelle());
                causeDefaut.setText(csodId.get(1).getLibelle() + " " + csodId.get(0).getLibelle());
            }
        });
        commentaire.setText(in.getCommentaire());

        DaoMachine.getInstance().getMachineById(in.getMachineCode(), new DelegateAsyncTask() {
            @Override
            public void whenWSIsTerminated(Object result) {
                Machine ma = (Machine) result;
                if (ma != null) {
                    Picasso.get().load("http://sio.jbdelasalle.com/~npardon/gmagro/photos/" + ma.getTypeMachineCode() + ".jpg").into(imgMachine);
                }
            }
        });

        intervenants = DaoIntervenant.getInstance().getLocalIntervenantsByInterv();
        intervenantsUpdateAdapter = new IntervenantsUpdateAdapter(getContext(), intervenants, getActivity());
        lv.setAdapter(intervenantsUpdateAdapter);
        DaoIntervenant.getInstance().getIntervenantsByInterventionId(in.getId(), new DelegateAsyncTask() {
            @Override
            public void whenWSIsTerminated(Object result) {
                intervenantsUpdateAdapter.notifyDataSetChanged();
            }
        });

        intervenantsOutOfInterv = DaoIntervenant.getInstance().getLocalIntervenantsOutOfInterv();
        intervAdapter = new ArrayAdapter<Intervenant>(getContext(), android.R.layout.simple_spinner_dropdown_item, intervenantsOutOfInterv);
        spinnerInterv.setAdapter(intervAdapter);
        DaoIntervenant.getInstance().getIntervenantOutOfIntervId(in.getId(), new DelegateAsyncTask() {
            @Override
            public void whenWSIsTerminated(Object result) {
                intervAdapter.notifyDataSetChanged();
            }
        });


        txUpdateAddTemps.setText("00:00");
        txUpdateAddTemps.setOnClickListener(vi -> {
            showTimePickerDialog(v, txUpdateAddTemps);
        });

        Button btUpdateAddIntervenant = ((Button) v.findViewById(R.id.btUpdateAddIntervenant));
        btUpdateAddIntervenant.setOnClickListener(view -> {
            OnClickAdd();
        });

        txTpsMachineAdd.setVisibility(v.GONE);
        txHeureFin.setVisibility(v.GONE);

        cbIntervStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    SetVisibility(txHeureFin, true);
                } else {
                    SetVisibility(txHeureFin, false);
                }
            }
        });

        cbMachineOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    SetVisibility(txTpsMachineAdd, true);
                } else {
                    SetVisibility(txTpsMachineAdd, false);
                }
            }
        });

        if (!in.getTempsArret().equals("00:00:00")) {
            cbMachineOff.setChecked(true);
            cbMachineOff.setText(in.getTempsArret());
            cbMachineOff.setEnabled(false);
            cbMachineOff.setText("Machine arrêtée : " + in.getTempsArret());
            SetVisibility(txTpsMachineAdd, true);
        }
        Log.d("TAG", "onCreateView: "+in.isPerte());

        if(in.isChangementOrgane() == true) {
            cbChangOrgane.setChecked(true);
            cbChangOrgane.setEnabled(false);
        }

        if(in.isPerte() == true) {
            cbPertes.setChecked(true);
            cbPertes.setEnabled(false);
        }

        txHeureFin.setOnClickListener(view -> {
            showDateTimePickerDialog(view);
        });

        txTpsMachineAdd.setOnClickListener(view -> {
            showTimePickerDialog(view, txTpsMachineAdd);
        });

        Button btUpdateIntervention = ((Button) v.findViewById(R.id.btUpdateEnd));
        btUpdateIntervention.setOnClickListener(view -> {
            OnClickUpdate();
        });


        return v;


    }

    private void OnClickUpdate() {
        Log.d("TAG", "OnClickUpdate: "+intervenants.get(1).getLogin() +" "+ intervenants.get(1).getTemps());

    }

    private void SetVisibility(TextView tv, boolean visibility) {
        if (visibility == true) {
            tv.setVisibility(tv.VISIBLE);
        } else {
            tv.setVisibility(tv.GONE);
        }

    }

    private void OnClickAdd() {
        if (txUpdateAddTemps.getText() != "00:00") {
            String time = (String) txUpdateAddTemps.getText();
            Intervenant interv = (Intervenant) spinnerInterv.getSelectedItem();
            Intervenant intervTime = new Intervenant(interv.getLogin(), interv.getNom(), interv.getPrenom(), interv.getMail(), interv.isActif(), interv.getCodeSite(), time);
            intervenants.add(intervTime);
            intervenantsUpdateAdapter.notifyDataSetChanged();
            intervenantsOutOfInterv.remove(interv);
            intervAdapter.notifyDataSetChanged();


        } else {
            showError(txUpdateAddTemps, "Please add a time");
        }
    }

    private void showDateTimePickerDialog(View vi) {
        final Calendar currentDate = Calendar.getInstance();
        date = Calendar.getInstance();
        new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date.set(year, monthOfYear, dayOfMonth);
                new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        date.set(Calendar.MINUTE, minute);
                        SimpleDateFormat formatHeure = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        dateHour = formatHeure.format(date.getTime());
                        txHeureFin.setText(dateHour);
                    }
                }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), true).show();
            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }

    private void showTimePickerDialog(View vi, TextView tv) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMin) {
                hour = selectedHour;
                min = selectedMin;
                tv.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, min));
            }
        };
        int style = AlertDialog.THEME_HOLO_DARK;
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), style, onTimeSetListener, hour, min, true);
        timePickerDialog.show();
    }

    private void showError(TextView input, String s) {
        input.setError(s);
        input.requestFocus();
    }


}

