package esprit.example.com.schoolingapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import esprit.example.com.schoolingapp.R;
import esprit.example.com.schoolingapp.adapters.EtudiantAdapters;
import esprit.example.com.schoolingapp.adapters.StudentNoteAdapter;
import esprit.example.com.schoolingapp.entities.Groupe;
import esprit.example.com.schoolingapp.entities.Student;

public class FragmentNote extends Fragment {
    RecyclerView list_students;
    private ArrayList<Student> students;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_note, container, false);
        list_students = view.findViewById(R.id.list_students);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        list_students.setLayoutManager(layoutManager);
        populate_list();
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

        StudentNoteAdapter etudiantAdapters = new StudentNoteAdapter(students, getActivity(), this);
        list_students.setAdapter(etudiantAdapters);
    }
}
