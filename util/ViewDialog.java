package com.g2m.asset.util;

import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.g2m.asset.R;

public class ViewDialog {

    public static void showDialog( String msg,boolean ic){
        final Dialog dialog = new Dialog(Constants.context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.no_network);

        TextView text =  dialog.findViewById(R.id.text_dialog);
        ImageView icon=dialog.findViewById(R.id.icon);
        if(ic){
            icon.setVisibility(View.VISIBLE);
        }
        else icon.setVisibility(View.GONE);
        text.setText(msg);

        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
