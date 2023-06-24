package edu.poly.assigment_ph26023.custom_toast;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import edu.poly.assigment_ph26023.R;

public class CustomToast {
    public static void showMessage(Activity context, String chuoi) {
        Toast toast = new Toast(context);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_custom_toast, context.findViewById(R.id.layout_custom_toast));
        toast.setView(view);
        TextView tvMessage = view.findViewById(R.id.tv_custom_toast);
        tvMessage.setText(chuoi);
        toast.show();
    }
}
