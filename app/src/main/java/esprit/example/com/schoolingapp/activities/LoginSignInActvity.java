package esprit.example.com.schoolingapp.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Date;
import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;
import esprit.example.com.schoolingapp.R;
import esprit.example.com.schoolingapp.entities.Student;
import esprit.example.com.schoolingapp.services.implementations.StudentServices;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginSignInActvity extends AppCompatActivity implements View.OnClickListener, Callback<ResponseBody> {

    EditText name, last_name, mail, phone, father_name, father_phone;
    Button birth_date, anne_bac, submit;
    Spinner bac_type;
    private int mYear;
    private int mMonth;
    private int mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sign_in_actvity);
        getWindow().setExitTransition(new Explode());
        getWindow().setEnterTransition(new Explode());
        getSupportActionBar().setTitle("Inscription Etudiant");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        name = findViewById(R.id.name);
        last_name = findViewById(R.id.last_name);
        mail = findViewById(R.id.mail);
        phone = findViewById(R.id.phone);
        father_name = findViewById(R.id.father_name);
        father_phone = findViewById(R.id.father_phone);
        birth_date = findViewById(R.id.birth_date);
        anne_bac = findViewById(R.id.anne_bac);
        submit = findViewById(R.id.submit);
        bac_type = findViewById(R.id.bac_type);

        submit.setOnClickListener(this);
        birth_date.setOnClickListener(this);
        anne_bac.setOnClickListener(this);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.row_spinner, getResources().getStringArray(R.array.bac));
        bac_type.setAdapter(arrayAdapter);
    }

    private boolean checkDataIntegrity() {
        if (TextUtils.isEmpty(name.getText())) {
            name.setError("Renseignez ce champ");
            name.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(last_name.getText())) {
            last_name.setError("Renseignez ce champ");
            last_name.requestFocus();

            return false;
        } else if (TextUtils.isEmpty(mail.getText())) {
            mail.setError("Renseignez ce champ");
            mail.requestFocus();

            return false;
        } else if (TextUtils.isEmpty(phone.getText())) {
            phone.setError("Renseignez ce champ");
            phone.requestFocus();

            return false;
        } else if (TextUtils.isEmpty(father_name.getText())) {
            father_name.setError("Renseignez ce champ");
            father_name.requestFocus();

            return false;
        } else if (TextUtils.isEmpty(father_phone.getText())) {
            father_phone.setError("Renseignez ce champ");
            father_phone.requestFocus();

            return false;
        }

        long phone = Long.parseLong(father_phone.getText().toString());
        long phone_2 = Long.parseLong(this.phone.getText().toString());
        if (phone > 99999999) {
            father_phone.setError("Ce numéro de telephone n'est pas valide");
            father_phone.requestFocus();

            return false;
        } else if (phone < 10000000) {
            father_phone.setError("Ce numéro de telephone n'est pas valide");
            father_phone.requestFocus();
            return false;
        }
        if (phone_2 > 99999999) {
            this.phone.setError("Ce numéro de telephone n'est pas valide");
            return false;
        } else if (phone_2 < 10000000) {
            this.phone.setError("Ce numéro de telephone n'est pas valide");
            return false;
        }


        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == birth_date.getId()) {
            pickDate(birth_date);
        } else if (v.getId() == anne_bac.getId()) {
            pickDate(anne_bac);
        } else if (v.getId() == submit.getId()) {
            if (checkDataIntegrity()) {
                Student student = new Student();
                student.setActive(false);
                student.setBac(bac_type.getSelectedItem().toString());
                Date date = Date.valueOf(anne_bac.getText().toString());
                student.setBac_date(date);
                date = Date.valueOf(anne_bac.getText().toString());
                student.setBirth_date(date);
                student.setName(name.getText().toString());
                student.setLast_name(last_name.toString());
                student.setEmail(last_name.toString());
                student.setPassword(last_name.toString());
                //student.setIdentifiant();
                long phone = Long.parseLong(father_phone.getText().toString());
                long phone_2 = Long.parseLong(this.phone.getText().toString());
                student.setPhone(phone_2);
                student.setParent_phone(phone);
                student.setParent_name(father_name.getText().toString());
                StudentServices studentServices = new StudentServices();
                studentServices.SignUpUser(student, this);
                Toast.makeText(this, "Data is being processed", Toast.LENGTH_SHORT).show();
            }
        }

    }


    void pickDate(Button b) {

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (DatePickerDialog.OnDateSetListener) (view, year, monthOfYear, dayOfMonth) -> b.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth), mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        Toast.makeText(this, "" + response.code(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        Log.e("Service sign_up", t.getMessage());
    }
}