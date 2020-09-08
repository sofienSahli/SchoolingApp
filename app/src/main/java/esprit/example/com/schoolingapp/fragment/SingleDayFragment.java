package esprit.example.com.schoolingapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import esprit.example.com.schoolingapp.R;

public class SingleDayFragment extends Fragment {

    final static public String GROUPE = "gr";
    final static public String COURS_1 = "c1";
    final static public String COURS_2 = "c2";
    final static public String COURS_3 = "c3";
    final static public String COURS_4 = "c4";
    TextView seance_1, seance_2, seance_3, seance_4, groupe;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single_day, container, false);
        seance_1 = view.findViewById(R.id.seance_1);
        seance_2 = view.findViewById(R.id.seance_2);
        seance_3 = view.findViewById(R.id.seance_3);
        seance_4 = view.findViewById(R.id.seance_4);
        groupe = view.findViewById(R.id.groupe);

        if (getArguments() != null) {
            Bundle bundle = getArguments();
            seance_1.setText(bundle.getString(COURS_1));
            seance_2.setText(bundle.getString(COURS_2));
            seance_3.setText(bundle.getString(COURS_3));
            seance_4.setText(bundle.getString(COURS_4));
            groupe.setText(bundle.getString(GROUPE));

        }

        return view;
    }
}
