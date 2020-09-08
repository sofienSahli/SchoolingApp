package esprit.example.com.schoolingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import esprit.example.com.schoolingapp.R;
import esprit.example.com.schoolingapp.entities.Student;
import esprit.example.com.schoolingapp.fragment.FragmentAppelEnseignant;

public class EtudiantAdapters extends RecyclerView.Adapter<EtudiantAdapters.Holder> {

    List<Student> students;
    Context context;
    FragmentAppelEnseignant fragmentAppelEnseignant;

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public EtudiantAdapters(List<Student> students, Context context, FragmentAppelEnseignant fragmentAppelEnseignant) {
        this.students = students;
        this.context = context;
        this.fragmentAppelEnseignant = fragmentAppelEnseignant;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.row_student_appel, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.setView(students.get(position));
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        Switch switch1;
        TextView matricule, name;

        public Holder(@NonNull View itemView) {
            super(itemView);
            switch1 = itemView.findViewById(R.id.switch1);
            matricule = itemView.findViewById(R.id.matricule);
            name = itemView.findViewById(R.id.name);
            switch1.setTextOff("Absent");
            switch1.setTextOn("Present");

        }

        void setView(Student student ){
            name.setText(student.getName() + "  "+ student.getLast_name());
            matricule.setText(student.getIdentifiant());
        }
    }
}
