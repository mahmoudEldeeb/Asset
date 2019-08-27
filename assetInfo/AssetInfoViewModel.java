package com.g2m.asset.assetInfo;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.g2m.asset.models.dataModels.AllDataField;
import com.g2m.asset.models.dataModels.AssetModel;
import com.g2m.asset.models.repositries.LocalRepositry;

public class AssetInfoViewModel extends ViewModel {
public LiveData<AssetModel>data=new MutableLiveData<>();
    public LiveData<AllDataField> getAssetData(String barcode){
        return LocalRepositry.getAssetData(barcode);
    }
}
