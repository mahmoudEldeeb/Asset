package com.g2m.asset.assetInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.g2m.asset.R;
import com.g2m.asset.databinding.ActivityAssetInfoBinding;
import com.g2m.asset.interfaces.ClickListener;
import com.g2m.asset.models.dataModels.AllDataField;
import com.g2m.asset.models.dataModels.AssetModel;
import com.g2m.asset.util.Constants;

public class AssetInfoActivity extends AppCompatActivity implements ClickListener {
ActivityAssetInfoBinding assetInfoBinding;
    AllDataField allDataField;
AssetInfoViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assetInfoBinding= DataBindingUtil.setContentView(this,R.layout.activity_asset_info);
        getSupportActionBar().setTitle("Asset Information");

        assetInfoBinding.setClickListener(this);
        Constants.context=this;
        allDataField=new AllDataField();
        assetInfoBinding.setAssetModel(allDataField);
        viewModel= ViewModelProviders.of(this).get(AssetInfoViewModel.class);

//        viewModel.getAssetData("000002");
//        viewModel.data.observe(this, new Observer<AssetModel>() {
//            @Override
//            public void onChanged(AssetModel a) {
//                if(a!=null) {
//                    aseetModel = a;
//                    assetInfoBinding.setAssetModel(aseetModel);
//                }
//            }
//        });

    }
    @Override
    public void onClick() {
    }

    @Override
    public void onTextChange() {

            if(assetInfoBinding.barcode.getText().toString().length()==6){
                Log.v("rrrr",assetInfoBinding.barcode.getText().toString());
                //viewModel.getAssetData("000002");
                getAssetsData(assetInfoBinding.barcode.getText().toString());
            }
        //aseetModel.setDescribtion("aaaaaaaaaaaaa");

    }

    private void getAssetsData(String barcode) {
        viewModel.getAssetData(barcode).observe(this, new Observer<AllDataField>() {
            @Override
            public void onChanged(AllDataField allDataField) {
                Log.v("sssss","mmmmm   vv");
//                Log.v("sssss",allDataField.getAssetModel().name+"   vv");
                assetInfoBinding.setAssetModel(allDataField);

            }
        });
    }
}
