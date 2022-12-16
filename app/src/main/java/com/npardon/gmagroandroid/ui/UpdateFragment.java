package com.npardon.gmagroandroid.ui;

import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.npardon.gmagroandroid.R;
import com.npardon.gmagroandroid.beans.Intervention;
import com.npardon.gmagroandroid.beans.Machine;
import com.npardon.gmagroandroid.daos.DaoMachine;
import com.npardon.gmagroandroid.daos.DelegateAsyncTask;
import com.squareup.picasso.Picasso;

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
        titleDate.setText(date);
        title.setText(activite);

        DaoMachine.getInstance().getMachineById(in.getMachineCode(), new DelegateAsyncTask() {
            @Override
            public void whenWSIsTerminated(Object result) {
                Machine ma = (Machine) result;
                if (ma != null) {
                    //Picasso.get().load("http://sio.jbdelasalle.com/~npardon/gmagro/photos/" + ma.getTypeMachineCode() + ".jpg").into(imgMachine);
                }
            }
        });

        causeSymp.setText(in.getSymptomeObjetCode()+" "+in.getSymptomeDefaultCode()+"\n"+in.getCauseObjetCode()+" "+in.getCauseDefautCode());
        ImageView imgMachine = v.findViewById(R.id.imageMachine);
        DaoMachine.getInstance().getMachineById(in.getMachineCode(), new DelegateAsyncTask() {
            @Override
            public void whenWSIsTerminated(Object result) {
                Machine ma = (Machine) result;
                if (ma != null) {
                    Picasso.get().load("http://sio.jbdelasalle.com/~npardon/gmagro/photos/" + ma.getTypeMachineCode() + ".jpg").into(imgMachine);
                }
            }
        });
        return v;
    }
}