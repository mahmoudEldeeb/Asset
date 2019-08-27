package com.g2m.asset.scannDialog;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.g2m.asset.R;
import com.g2m.asset.assetInfo.AssetInfoActivity;
import com.g2m.asset.transfeer.TransfeerActivity;
import com.g2m.asset.util.Constants;

public class ViewDialogNotPlace {

    public static void showDialog(final String code){
        final Dialog dialog = new Dialog(Constants.context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.no_place);




        Button dialogButton = dialog.findViewById(R.id.nothing);
        Button transfer = dialog.findViewById(R.id.btn_transfer);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Constants.context, TransfeerActivity.class);
                intent.putExtra("barcode",code);
                Constants.context.startActivity(intent);
                dialog.dismiss();

            }
        });
        dialog.show();
    }
}
