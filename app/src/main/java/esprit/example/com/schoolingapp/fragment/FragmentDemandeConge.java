package esprit.example.com.schoolingapp.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import esprit.example.com.schoolingapp.R;

public class FragmentDemandeConge extends Fragment implements View.OnClickListener {

    Spinner spinner;
    private int mYear, mMonth, mDay;
    Button debut, fin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conge, container, false);
        spinner = view.findViewById(R.id.spinner);
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.row_spinner, getResources().getStringArray(R.array.leaves));
        spinner.setAdapter(stringArrayAdapter);
        debut = view.findViewById(R.id.debut);
        fin = view.findViewById(R.id.fin);

        fin.setOnClickListener(this);
        debut.setOnClickListener(this);
        return view;
    }


    void pickDate(Button b) {

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                (DatePickerDialog.OnDateSetListener) (view, year, monthOfYear, dayOfMonth) -> b.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year), mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == fin.getId())
            pickDate(fin);
        else if (v.getId() == debut.getId())
            pickDate(debut);
    }
}

