package esprit.example.com.schoolingapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import esprit.example.com.schoolingapp.R;
import esprit.example.com.schoolingapp.activities.FicheEncadrement;
import esprit.example.com.schoolingapp.entities.Fonctionalite;
import esprit.example.com.schoolingapp.local_storage.AppDatabase;
import esprit.example.com.schoolingapp.services.implementations.FonctionsServices;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FonctionaliteAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {
    List<Fonctionalite> fonctionalites;
    Context context;

    public FonctionaliteAdapter(List<Fonctionalite> fonctionalites, Context context) {
        this.fonctionalites = fonctionalites;
        this.context = context;
    }

    public List<Fonctionalite> getFonctionalites() {
        return fonctionalites;
    }

    public void setFonctionalites(List<Fonctionalite> fonctionalites) {
        this.fonctionalites = fonctionalites;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return fonctionalites.size();
    }

    @Override
    public Object getItem(int position) {
        return fonctionalites.get(position).getFunc();
    }

    @Override
    public long getItemId(int position) {
        return fonctionalites.get(position).getId_fonct();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TextView textView = (TextView) layoutInflater.inflate(R.layout.list_view_row, parent, false);
        textView.setText(fonctionalites.get(position).getFunc());
        return textView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        showDialog(position);
    }

    void showDialog(int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_update_fonc_prob, null, false);
        EditText editText = view.findViewById(R.id.editTextTextMultiLine);
        Button button4, button3;
        button3 = view.findViewById(R.id.button3);
        button4 = view.findViewById(R.id.button4);

        editText.setText(fonctionalites.get(position).getFunc());
        AlertDialog dialog = new AlertDialog.Builder(context).setView(view).create();
        dialog.show();
        button3.setOnClickListener(v -> {
            if (context instanceof FicheEncadrement) {
                Toast.makeText(context, "Cooking", Toast.LENGTH_SHORT).show();
            } else {
                Fonctionalite fonctionalite = fonctionalites.get(position);
                if (fonctionalite.getId_fonct() != 0) {
                    AppDatabase.getAppDatabase(context).FonctionalitesDAO().delete(fonctionalite);
                }
                fonctionalites.remove(position);
                notifyDataSetChanged();
            }
            dialog.dismiss();
        });
        button4.setOnClickListener(v -> {

            Fonctionalite fonctionalite = fonctionalites.get(position);

            fonctionalite.setFunc(editText.getText().toString());
            if (context instanceof FicheEncadrement) {
                if (fonctionalite.getId_fonct() != 0) {
                    update_fonctionalite(fonctionalite);
                }
            } else {
                if (fonctionalite.getId_fonct() != 0) {
                    AppDatabase.getAppDatabase(context).FonctionalitesDAO().update(fonctionalite);
                }
            }
            notifyDataSetChanged();

            dialog.dismiss();
        });
    }

    private void update_fonctionalite(Fonctionalite f) {
        List<Fonctionalite> fonctionalites = new ArrayList<>();
        fonctionalites.add(f);
        FonctionsServices fonctionsServices = new FonctionsServices();
        fonctionsServices.update_fonctions(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    Toast.makeText(context, "Modification enregistrée", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Fonctionalité update", "onFailure: " + t.getMessage());
            }
        }, fonctionalites);
    }
}
