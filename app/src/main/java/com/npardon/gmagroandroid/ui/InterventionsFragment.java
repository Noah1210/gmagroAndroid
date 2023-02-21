package com.npardon.gmagroandroid.ui;

import android.app.Fragment;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.npardon.gmagroandroid.R;
import com.npardon.gmagroandroid.beans.Intervenant;
import com.npardon.gmagroandroid.beans.Intervention;
import com.npardon.gmagroandroid.beans.InterventionsUnfinishedAdapter;
import com.npardon.gmagroandroid.daos.DaoIntervention;
import com.npardon.gmagroandroid.daos.DelegateAsyncTask;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InterventionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InterventionsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button button;
    private InterventionsUnfinishedAdapter interventionsUnfinishedAdapter ;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InterventionsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InterventionsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InterventionsFragment newInstance(String param1, String param2) {
        InterventionsFragment fragment = new InterventionsFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_interventions, container, false);

        TextView tvUser = view.findViewById(R.id.tvUser);
        tvUser.setText("Bonjour "+Connexion.intervenantConnecte.getPrenom());
        button = view.findViewById(R.id.button);
        List<Intervention> interventions = DaoIntervention.getInstance().getLocalInterventions();
        ListView lv = ((ListView)view.findViewById(R.id.lvIntervention)) ;
        interventionsUnfinishedAdapter = new InterventionsUnfinishedAdapter(getActivity().getApplicationContext(), interventions);
        lv.setAdapter(interventionsUnfinishedAdapter);
        DaoIntervention.getInstance().getInterventions(new DelegateAsyncTask() {
            @Override
            public void whenWSIsTerminated(Object result) {
                interventionsUnfinishedAdapter.notifyDataSetChanged();
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intervention in = (Intervention) parent.getAdapter().getItem(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("intervention", in);
                bundle.putSerializable("activite",((TextView)view.findViewById(R.id.tvInterventionActivite)).getText().toString());
                bundle.putSerializable("date",((TextView)view.findViewById(R.id.tvInterventionDate)).getText().toString());
                UpdateFragment frag = new UpdateFragment();
                frag.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.frameLayout, frag).commit();
            }
        });
        Bundle bundle = this.getArguments();
        if(bundle != null){
            Intervenant test = (Intervenant) bundle.getSerializable("intervenant");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(), "ITS WORKING "+test.getLogin(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        return view;
    }
}