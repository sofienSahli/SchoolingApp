package esprit.example.com.schoolingapp.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import esprit.example.com.schoolingapp.R;
import esprit.example.com.schoolingapp.activities.DepotEnseignantActivity;
import esprit.example.com.schoolingapp.entities.FichePFE;
import esprit.example.com.schoolingapp.entities.NotePFE;
import esprit.example.com.schoolingapp.services.implementations.NotePFEServices;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class FragmentNotePFE extends Fragment {
    EditText technolo, equipe, encadrement, asseduite, ergonomi, total;
    FichePFE fichePFE;
    Button button6;

    public FichePFE getFichePFE() {
        return fichePFE;
    }

    public void setFichePFE(FichePFE fichePFE) {
        this.fichePFE = fichePFE;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_pfe, container, false);
        init_view(view);
        fetch_note_from_server();
        return view;
    }

    private void fetch_note_from_server() {
        if (fichePFE != null) {
            NotePFEServices notePFEServices = new NotePFEServices();
            notePFEServices.getByID(fichePFE.getId(), new Callback<NotePFE>() {
                @Override
                public void onResponse(Call<NotePFE> call, Response<NotePFE> response) {
                    if (response.code() == 200) {
                        if (response.body() != null) {
                            equipe.setText(response.body().getTravail_equipe() + " ");
                            technolo.setText(response.body().getMaitrise_technologie() + " ");
                            encadrement.setText(response.body().getEncadrement() + " ");
                            asseduite.setText(response.body().getAsseduite() + " ");
                            total.setText(response.body().getTotal() + " ");
                            ergonomi.setText(response.body().getErgonomi() + "");
                        } else {
                            Toast.makeText(getActivity(), "Aucune note n'a été saisie pour cette fiche", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<NotePFE> call, Throwable t) {
                    Toast.makeText(getActivity(), "Erreur récupération des notes", Toast.LENGTH_SHORT).show();
                    Timber.e("fetch_note_from_server: " + t.getMessage());
                }
            });
        }

    }

    private void init_view(View view) {

        technolo = view.findViewById(R.id.technolo);
        equipe = view.findViewById(R.id.equipe);
        encadrement = view.findViewById(R.id.encadrement);
        asseduite = view.findViewById(R.id.asseduite);
        ergonomi = view.findViewById(R.id.ergonomi);
        total = view.findViewById(R.id.total);
        button6 = view.findViewById(R.id.button6);

        if (getActivity() instanceof DepotEnseignantActivity) {


            equipe.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!TextUtils.isEmpty(s)) {
                        double value = Double.parseDouble(s.toString());
                        if (value > 4)
                            equipe.setText(4.0 + "");
                    }
                }
            });
            encadrement.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!TextUtils.isEmpty(s)) {

                        double value = Double.parseDouble(s.toString());
                        if (value > 4)
                            encadrement.setText(4.0 + "");
                    }
                }
            });
            technolo.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!TextUtils.isEmpty(s)) {
                        double value = Double.parseDouble(s.toString());
                        if (value > 4)
                            technolo.setText(4.0 + "");
                    }
                }
            });
            asseduite.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!s.toString().equals("")) {
                        double value = Double.parseDouble(s.toString());
                        if (value > 4)
                            asseduite.setText(4.0 + "");
                    }
                }
            });
            ergonomi.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!TextUtils.isEmpty(s)) {

                        double value = Double.parseDouble(s.toString());
                        if (value > 4)
                            ergonomi.setText(4.0 + "");
                    }
                }
            });
            //  total.addTextChangedListener(this);

            button6.setOnClickListener(v -> {
                if (fichePFE != null) {

                    if (TextUtils.isEmpty(technolo.getText())) {
                        technolo.setError("Veuilliez renseigner ce champs");
                    } else if (TextUtils.isEmpty(equipe.getText())) {
                        equipe.setError("Veuilliez renseigner ce champs");
                    } else if (TextUtils.isEmpty(encadrement.getText())) {
                        encadrement.setError("Veuilliez renseigner ce champs");
                    } else if (TextUtils.isEmpty(asseduite.getText())) {
                        asseduite.setError("Veuilliez renseigner ce champs");
                    } else if (TextUtils.isEmpty(ergonomi.getText())) {
                        ergonomi.setError("Veuilliez renseigner ce champs");
                    } else {
                        upload_note();
                    }
                } else {
                    Toast.makeText(getActivity(), "Erreur interne veuiller relancer l'application", Toast.LENGTH_LONG).show();
                }
            });
        }else {
            button6.setVisibility(View.GONE);
            equipe.setEnabled(false);
            encadrement.setEnabled(false);
            technolo.setEnabled(false);
            asseduite.setEnabled(false);
            ergonomi.setEnabled(false);
            total.setEnabled(false);
        }
    }

    private void upload_note() {
        double techno, equi, enc, asse, ergo, tot;
        techno = Double.parseDouble(technolo.getText().toString());
        equi = Double.parseDouble(equipe.getText().toString());
        enc = Double.parseDouble(encadrement.getText().toString());
        asse = Double.parseDouble(asseduite.getText().toString());
        ergo = Double.parseDouble(ergonomi.getText().toString());
        tot = techno + equi + enc + asse + ergo;
        total.setText(tot + " ");
        NotePFE notePFE = new NotePFE();
        notePFE.setAsseduite(asse);
        notePFE.setEncadrement(enc);
        notePFE.setErgonomi(ergo);
        notePFE.setTravail_equipe(equi);
        notePFE.setMaitrise_technologie(techno);
        notePFE.setTotal(tot);
        notePFE.setFiche_id(fichePFE.getId());
        NotePFEServices notePFEServices = new NotePFEServices();
        notePFEServices.store(notePFE, new Callback<NotePFE>() {
            @Override
            public void onResponse(Call<NotePFE> call, Response<NotePFE> response) {
                if (response.code() == 200) {
                    Toast.makeText(getActivity(), "Changement Sauvegardé", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NotePFE> call, Throwable t) {
                Timber.e("upload_note" + t.getMessage());
            }
        });
    }


}
