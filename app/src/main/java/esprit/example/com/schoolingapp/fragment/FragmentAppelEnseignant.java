package esprit.example.com.schoolingapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import esprit.example.com.schoolingapp.R;
import esprit.example.com.schoolingapp.adapters.EtudiantAdapters;
import esprit.example.com.schoolingapp.entities.Groupe;
import esprit.example.com.schoolingapp.entities.Student;

public class FragmentAppelEnseignant extends Fragment implements SearchView.OnQueryTextListener {
    RecyclerView list_etudiant;
    Spinner spinner_classes;
    Button button_submit_appel;
    SearchView searchView2;
    ArrayList<Student> students;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appel, container, false);

        list_etudiant = view.findViewById(R.id.list_etudiant);
        spinner_classes = view.findViewById(R.id.spinner_classes);
        button_submit_appel = view.findViewById(R.id.button_submit_appel);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.row_spinner, getResources().getStringArray(R.array.classes));
        spinner_classes.setAdapter(arrayAdapter);


        list_etudiant = view.findViewById(R.id.list_etudiant);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        list_etudiant.setLayoutManager(mLayoutManager);
        populate_list();
        searchView2 = view.findViewById(R.id.searchView2);
        searchView2.setOnQueryTextListener(this);
        return view;
    }

    private void populate_list() {
        students = new ArrayList<>();
        students.add(new Student(1, "Farhad", "Hachad", "farhad.hachad@gmail.com", "131JMT0300", 71238428, new Groupe("4SIM2", 1)));
        students.add(new Student(2, "George", "Belotte", "George.belotte@gmail.com", "231JMT0322", 71238428, new Groupe("4SIM2", 1)));
        students.add(new Student(3, "Black", "Ghouaiel", "Black.Ghouaiel@gmail.com", "131JMT0300", 71238428, new Groupe("4SIM2", 1)));
        students.add(new Student(4, "AZDZD", "TEST", "AZDZAD.TEST@gmail.com", "131JMT0300", 71238428, new Groupe("4SIM2", 1)));
        students.add(new Student(5, "SQdqdazd", "Hachdazdazad", "dazd.dza@gmail.com", "131JMT0300", 71238428, new Groupe("4SIM2", 1)));
        students.add(new Student(6, "dsqdqscg", "fezhrsqdq", "zadaz.azsqc@gmail.com", "131JMT0300", 71238428, new Groupe("4SIM2", 1)));
        students.add(new Student(7, "sdqcbg", "htzza", "dadzad.hadazdachad@gmail.com", "131JMT0300", 71238428, new Groupe("4SIM2", 1)));
        students.add(new Student(8, "xafef", "dzazadc", "farhad.hachad@gmail.com", "131JMT0300", 71238428, new Groupe("4SIM2", 1)));
        students.add(new Student(10, "nbjfkei", "kdkjdfj", "farhad.hachad@gmail.com", "131JMT0300", 71238428, new Groupe("4SIM2", 1)));
        students.add(new Student(11, "kfjajhaz", "kjjhz", "farhad.hachad@gmail.com", "131JMT0300", 71238428, new Groupe("4SIM2", 1)));
        students.add(new Student(12, "kjazjkza", "azjkzalkaz", "farhad.hachad@gmail.com", "131JMT0300", 71238428, new Groupe("4SIM2", 1)));
        students.add(new Student(13, "dbaz", "Hachfead", "farhad.hachad@gmail.com", "131JMT0300", 71238428, new Groupe("4SIM2", 1)));
        students.add(new Student(14, "aedazaze", "Hachad", "farhad.hachad@gmail.com", "131JMT0300", 71238428, new Groupe("4SIM2", 1)));
        students.add(new Student(15, "gzevze", "Hachafeafaed", "farhad.hachad@gmail.com", "131JMT0300", 71238428, new Groupe("4SIM2", 1)));
        students.add(new Student(16, "fzacaz", "azzda", "farhad.hachad@gmail.com", "131JMT0300", 71238428, new Groupe("4SIM2", 1)));
        students.add(new Student(17, "zfbrg", "brbr", "farhad.hachad@gmail.com", "131JMT0300", 71238428, new Groupe("4SIM2", 1)));
        students.add(new Student(18, "Facazcarhad", "dazdazdaz", "farhad.hachad@gmail.com", "131JMT0300", 71238428, new Groupe("4SIM2", 1)));
        students.add(new Student(19, "dazdaz", "Hachad", "farhad.hachad@gmail.com", "131JMT0300", 71238428, new Groupe("4SIM2", 1)));
        students.add(new Student(20, "dazdazd", "dazdazdaz", "farhad.hachad@gmail.com", "131JMT0300", 71238428, new Groupe("4SIM2", 1)));
        students.add(new Student(21, "dazdaz", "Hachad", "farhad.hachad@gmail.com", "131JMT0300", 71238428, new Groupe("4SIM2", 1)));

        EtudiantAdapters etudiantAdapters = new EtudiantAdapters(students, getActivity(), this);
        list_etudiant.setAdapter(etudiantAdapters);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        ArrayList<Student> students = searchStudent(query);
        ((EtudiantAdapters) list_etudiant.getAdapter()).setStudents(students);
        list_etudiant.getAdapter().notifyDataSetChanged();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        ArrayList<Student> students = searchStudent(newText);
        ((EtudiantAdapters) list_etudiant.getAdapter()).setStudents(students);
        list_etudiant.getAdapter().notifyDataSetChanged();
        return true;
    }


    ArrayList<Student> searchStudent(String query) {
        ArrayList<Student> stud = new ArrayList<>();
        for (Student s : students) {
            if (s.getName().toLowerCase().contains(query.toLowerCase())) {
                stud.add(s);
            } else if (s.getLast_name().toLowerCase().contains(query.toLowerCase())) {
                stud.add(s);
            } else if (s.getIdentifiant().toLowerCase().contains(query.toLowerCase())) {
                stud.add(s);
            }
        }
        return stud;
    }

}
