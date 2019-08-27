package com.g2m.asset.scannDialog;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.g2m.asset.models.dataModels.AssetModel;
import com.g2m.asset.models.dataModels.AssetOfInventory;
import com.g2m.asset.models.dataModels.TransfeerModel;
import com.g2m.asset.models.repositries.LocalRepositry;
import com.g2m.asset.models.repositries.RemoteRepositry;
import com.g2m.asset.util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

public class ScannViewModel extends ViewModel {

    public LiveData<List<ScannModels>> getAssetsOfInventory(int inv_id){
return LocalRepositry.getAssetsOfInventory(inv_id);

    }

    public void saveWhatScan( int inv_id, int asset_id) {
        AssetOfInventory asset=new AssetOfInventory();
        asset.status=1;
        asset.inv_id=inv_id;
        asset.asset_id=asset_id;
        LocalRepositry.saveWhatScan(asset);
    }

    public void assetConfirm(List<ScannModels>list,int inv_id){

        try {
            JSONArray jsonArray=new JSONArray();
            for(int i=0;i<list.size();i++) {
            ScannModels model=list.get(i);
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("asset_id", model.asset_id);
                jsonObj.put("asset_qty",model.status);
            jsonArray.put(jsonObj);

        }
            send(jsonArray.toString(),inv_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void send(String data,int id){
        RemoteRepositry.assetConfirm(data,id)
                .subscribeWith(new SingleObserver<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(ResponseBody responseBody) {
                        try {
                            Log.v("tttttt",responseBody.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.v("tttttt",e.getMessage());
                    }
                });
    }


}
