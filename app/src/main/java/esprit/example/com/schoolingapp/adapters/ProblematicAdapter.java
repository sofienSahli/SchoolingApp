package esprit.example.com.schoolingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.appcompat.app.AlertDialog;
import esprit.example.com.schoolingapp.R;
import esprit.example.com.schoolingapp.activities.FicheEncadrement;
import esprit.example.com.schoolingapp.entities.Problematique;
import esprit.example.com.schoolingapp.local_storage.AppDatabase;

public class ProblematicAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {
    List<Problematique> problematiqueLis;
    Context context;

    public ProblematicAdapter() {
    }

    public ProblematicAdapter(List<Problematique> problematiqueLis, Context context) {
        this.problematiqueLis = problematiqueLis;
        this.context = context;
    }

    public List<Problematique> getProblematiqueLis() {
        return problematiqueLis;
    }

    public void setProblematiqueLis(List<Problematique> problematiqueLis) {
        this.problematiqueLis = problematiqueLis;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return problematiqueLis.size();
    }

    @Override
    public Object getItem(int position) {
        return problematiqueLis.get(position).getProb();
    }

    @Override
    public long getItemId(int position) {
        return problematiqueLis.get(position).getId_prob();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TextView view = (TextView) layoutInflater.inflate(R.layout.list_view_row, parent, false);
        view.setText(problematiqueLis.get(position).getProb());
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        showDialog(position);
    }

    void showDialog(int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_update_fonc_prob, null, false);
        EditText editText = view.findViewById(R.id.editTextTextMultiLine);
        editText.setText(problematiqueLis.get(position).getProb());
        Button button4, button3;
        button3 = view.findViewById(R.id.button3);
        button4 = view.findViewById(R.id.button4);

        AlertDialog dialog = new AlertDialog.Builder(context).setView(view).create();
        dialog.show();
        button3.setOnClickListener(v -> {
            if (context instanceof FicheEncadrement) {
                Toast.makeText(context, "Cooking", Toast.LENGTH_SHORT).show();
            } else {
                Problematique fonctionalite = problematiqueLis.get(position);
                if (fonctionalite.getId_prob() != 0) {
                    AppDatabase.getAppDatabase(context).Problematiques().delete(fonctionalite);
                }
                problematiqueLis.remove(position);
                notifyDataSetChanged();
            }
            dialog.dismiss();

        });
        button4.setOnClickListener(v -> {
            if (context instanceof FicheEncadrement) {
                Toast.makeText(context, "Cooking", Toast.LENGTH_SHORT).show();
            } else {
                Problematique fonctionalite = problematiqueLis.get(position);
                fonctionalite.setProb(editText.getText().toString());
                if (fonctionalite.getId_prob() != 0) {
                    AppDatabase.getAppDatabase(context).Problematiques().update(fonctionalite);
                }
                dialog.dismiss();
            }
            notifyDataSetChanged();
        });
    }
}
