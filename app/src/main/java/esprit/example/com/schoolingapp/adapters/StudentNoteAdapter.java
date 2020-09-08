package esprit.example.com.schoolingapp.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import esprit.example.com.schoolingapp.R;
import esprit.example.com.schoolingapp.entities.Student;
import esprit.example.com.schoolingapp.fragment.FragmentNote;

public class StudentNoteAdapter extends RecyclerView.Adapter<StudentNoteAdapter.Holder> {

    ArrayList<Student> students;
    Context context;
    FragmentNote fragmentNote;

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public StudentNoteAdapter(ArrayList<Student> students, Context context, FragmentNote fragmentNote) {
        this.students = students;
        this.context = context;
        this.fragmentNote = fragmentNote;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.row_student_note, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.setUpView(students.get(position), context);
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        ImageButton saisir_note;
        TextView name, matricule, moyenne_module;
        ImageView imageView4;

        public Holder(@NonNull View itemView) {
            super(itemView);
            saisir_note = itemView.findViewById(R.id.saisir_note);
            name = itemView.findViewById(R.id.name);
            matricule = itemView.findViewById(R.id.matricule);
            moyenne_module = itemView.findViewById(R.id.moyenne_module);
            imageView4 = itemView.findViewById(R.id.imageView4);
        }

        void setUpView(Student student, Context context) {
            name.setText(student.getName() + " " + student.getLast_name());
            matricule.setText(student.getIdentifiant());
            saisir_note.setOnClickListener(v -> {
                inflateDialog(context);

            });
        }

        private void inflateDialog(Context context) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            // Get the layout inflater
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(inflater.inflate(R.layout.dialog_note_one_student, null));
            builder.create().show();
        }
    }
}
