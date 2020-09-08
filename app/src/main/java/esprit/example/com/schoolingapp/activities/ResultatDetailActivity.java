package esprit.example.com.schoolingapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import esprit.example.com.schoolingapp.R;
import esprit.example.com.schoolingapp.adapters.ResultatAdapter;
import esprit.example.com.schoolingapp.entities.Resultat;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ResultatDetailActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    static public final String TITLE = "t";
    static public final String MOYENNE_CC = "cc";
    static public final String MOYENNE_DS = "ds";
    static public final String MOYENNE_EXAMEN = "ex";
    static public final String MOYENNE = "mo";
    static public final String ID = "id";

    List<Resultat> list;
    TextView moyenne_total, note_cc, note_ds, note_examen, enseignant;
    SearchView searchView;
    RecyclerView resultat_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultat_detail);
        moyenne_total = findViewById(R.id.moyenne_total);
        note_cc = findViewById(R.id.note_cc);
        note_ds = findViewById(R.id.note_ds);
        note_examen = findViewById(R.id.note_examen);
        enseignant = findViewById(R.id.enseignant);
        searchView = findViewById(R.id.searchView);
        resultat_list = findViewById(R.id.resultat_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        resultat_list.setLayoutManager(mLayoutManager);
        populate_list();

        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            setUpResultat(new Resultat(bundle.getString(TITLE),
                    bundle.getFloat(MOYENNE),
                    bundle.getFloat(MOYENNE_DS),
                    bundle.getFloat(MOYENNE_EXAMEN),
                    bundle.getFloat(MOYENNE_CC)
            ));
        }
        searchView.setOnQueryTextListener(this);
    }


    public void setUpResultat(Resultat resultat) {
        moyenne_total.setText(resultat.getMoyenne() + " / 20");
        note_cc.setText(resultat.getNote_cc() + " / 20");
        note_examen.setText(resultat.getNote_examens() + " / 20");
        note_ds.setText(resultat.getNote_ds() + " / 20");
        getSupportActionBar().setTitle(resultat.getSubject_name());
    }

    private void populate_list() {
        list = new ArrayList<>();
        list.add(new Resultat("Matematic 1 ", 10.12f, 11, 15, 17));
        list.add(new Resultat("JEE ", 7f, 8, 20, 26));
        list.add(new Resultat(".Net ", 9f, 1, 43, 32));
        list.add(new Resultat("Unity ", 2f, 1, 5, 7));
        list.add(new Resultat("Java  ", 10.12f, 11, 15, 17));
        list.add(new Resultat("Java me ", 10.12f, 11, 15, 17));
        list.add(new Resultat("Android", 10.12f, 11, 15, 17));
        list.add(new Resultat("IOS ", 15f, 27, 16, 28));
        ResultatAdapter resultatAdapter = new ResultatAdapter(list, this);
        resultat_list.setAdapter(resultatAdapter);
        resultatAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        List<Resultat> filtredList = new ArrayList<>();
        if (!TextUtils.isEmpty(newText)) {

            for (Resultat r : list) {
                if (r.getSubject_name().toLowerCase().contains(newText.toLowerCase()))
                    filtredList.add(r);
            }
            ResultatAdapter resultatAdapter = new ResultatAdapter(filtredList, this);
            resultat_list.setAdapter(resultatAdapter);
            resultatAdapter.notifyDataSetChanged();
        } else {
            ResultatAdapter resultatAdapter = new ResultatAdapter(list, this);
            resultat_list.setAdapter(resultatAdapter);
            resultatAdapter.notifyDataSetChanged();
        }
        return true;
    }
}