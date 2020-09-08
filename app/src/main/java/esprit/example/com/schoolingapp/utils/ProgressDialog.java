package esprit.example.com.schoolingapp.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import esprit.example.com.schoolingapp.R;

public class ProgressDialog {
    Context context;
    AlertDialog alertDialog;

    public ProgressDialog(Context context) {
        this.context = context;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.dialog_waiting_layout, null, false);
        alertDialog = new AlertDialog.Builder(context).setView(view).create();
    }

    public void showDialog() {
        alertDialog.show();
    }

    public void dismissDialog() {
        alertDialog.dismiss();
    }

    public boolean isDialogSHowing() {
        return alertDialog.isShowing();
    }
}
