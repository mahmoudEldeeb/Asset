package com.g2m.asset.scannDialog;

import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.room.Entity;
import androidx.room.Ignore;

import com.g2m.asset.R;
import com.g2m.asset.models.dataModels.AssetModel;
import com.squareup.picasso.Picasso;


public class ScannModels {

   public int status=0;
   public int asset_id;
   public String barcode,name;

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView view, int s){
        if(s==1){
            view.setImageResource(R.drawable.ic_check_black_24dp);
        }
       else if(s==-1)view.setImageResource(R.drawable.ic_check_black_24dp);

    }
}
