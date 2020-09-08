package esprit.example.com.schoolingapp.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import esprit.example.com.schoolingapp.R;
import esprit.example.com.schoolingapp.activities.EmploieStudentActivity;
import esprit.example.com.schoolingapp.activities.NouvelleFichePFE;
import esprit.example.com.schoolingapp.activities.ResultatDetailActivity;

public class EtudiantHomeFragment extends Fragment implements View.OnClickListener {
    Button resultat, emploie, abcense, fichPFE, attestation_precesne;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        resultat = view.findViewById(R.id.Resultat);
        emploie = view.findViewById(R.id.emploie);
        abcense = view.findViewById(R.id.abcense);
        fichPFE = view.findViewById(R.id.fichPFE);
        attestation_precesne = view.findViewById(R.id.attestation_precesne);

        resultat.setOnClickListener(this);
        emploie.setOnClickListener(this);
        abcense.setOnClickListener(this);
        fichPFE.setOnClickListener(this);
        attestation_precesne.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Resultat:
                Intent intent = new Intent(getActivity(), ResultatDetailActivity.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                break;

            case R.id.emploie:

                Intent i = new Intent(getActivity(), EmploieStudentActivity.class);
                startActivity(i, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                break;

            case R.id.abcense:
                Toast.makeText(getActivity(), "being cooked", Toast.LENGTH_SHORT).show();

                break;

            case R.id.fichPFE:
                Intent intent1 = new Intent(getActivity() , NouvelleFichePFE.class);
                startActivity(intent1, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                break;

            case R.id.attestation_precesne:
                Toast.makeText(getActivity(), "being cooked", Toast.LENGTH_SHORT).show();

                break;
        }
    }
}
