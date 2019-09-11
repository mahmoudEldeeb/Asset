package com.g2m.asset.inventory;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.g2m.asset.models.AllInventoryData;
import com.g2m.asset.models.dataModels.AssetOfInventory;
import com.g2m.asset.models.dataModels.InventoryTabel;
import com.g2m.asset.models.dataModels.Result;
import com.g2m.asset.models.repositries.LocalRepositry;
import com.g2m.asset.models.repositries.RemoteRepositry;
import com.g2m.asset.scannDialog.ScannModels;
import com.g2m.asset.util.Helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class InventoryViewModel extends ViewModel {
    LiveData<List<InventoryTabel>> listOfOperation;
     public InventoryViewModel(){
         listOfOperation=new MutableLiveData<>();
     }
     public LiveData<List<InventoryTabel>> getOperations(){
         return LocalRepositry.getAllOperations();

     }
    public void getOperationsOnline(){
        Helper.showDialog();
        RemoteRepositry.getAllInventoryData().subscribeWith(new SingleObserver<AllInventoryData>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(AllInventoryData eggsModel) {
                final List<AssetOfInventory>listAsset=new ArrayList<>();
                for(int i=0;i<eggsModel.asset_code.size();i++){
                    AssetOfInventory model=new AssetOfInventory();
                   model.inv_id= Integer.parseInt(eggsModel.asset_code.get(i).get(0).adjustment_id.get(0));
                    model.asset_id= Integer.parseInt(eggsModel.asset_code.get(i).get(0).asset_id.get(0));
                    model.status=0;
                listAsset.add(model);
                }
                LocalRepositry.saveOperation(eggsModel.getAsset_adjust(),listAsset);

            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }
    public void sendOnlineInventory(int id) {
         Helper.showDialog();
        LocalRepositry.getInventoryNotSend(id).subscribeWith(new SingleObserver<List<AssetOfInventory>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(List<AssetOfInventory> assetOfInventories) {
                assetConfirm(assetOfInventories);
            }

            @Override
            public void onError(Throwable e) {
Helper.dismiss();
            }
        });
    }
    public void assetConfirm(List<AssetOfInventory>list){

        try {
            JSONArray jsonArray=new JSONArray();
            for(int i=0;i<list.size();i++) {
                AssetOfInventory model=list.get(i);
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("asset_id", model.asset_id);
                jsonObj.put("asset_qty",model.status);
                jsonArray.put(jsonObj);

            }
            send(jsonArray.toString(),list.get(0).inv_id);
        } catch (JSONException e) {
            Helper.dismiss();
            e.printStackTrace();
        }
    }
    public void send(String data, final int id){
        RemoteRepositry.assetConfirm(data,id)
                .subscribeWith(new SingleObserver<Result>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Result result) {

                        if(result.status){
                            LocalRepositry.saveInventAsSent(id);
                        }
                        Helper.showToast(result.message);
                            Helper.dismiss();

                    }

                    @Override
                    public void onError(Throwable e) {
                        Helper.showToast(e.getMessage());
                        Helper.dismiss();
                        Log.v("tttttt",e.getMessage());
                    }
                });
    }

    public void delete(int inv_id) {
         LocalRepositry.delete(inv_id);
    }
}
