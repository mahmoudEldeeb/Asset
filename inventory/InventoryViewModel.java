package com.g2m.asset.inventory;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.g2m.asset.models.EggsModel;
import com.g2m.asset.models.dataModels.AssetOfInventory;
import com.g2m.asset.models.dataModels.InventoryModel;
import com.g2m.asset.models.dataModels.InventoryModelResult;
import com.g2m.asset.models.dataModels.InventoryTabel;
import com.g2m.asset.models.repositries.LocalRepositry;
import com.g2m.asset.models.repositries.RemoteRepositry;
import com.g2m.asset.util.Helper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class InventoryViewModel extends ViewModel {
     MutableLiveData<List<InventoryTabel>> listOfOperation
            ;
     public InventoryViewModel(){
         listOfOperation=new MutableLiveData<>();
     }

     public void getOperations(){

         LocalRepositry.getAllOperations().subscribeWith(new SingleObserver<List<InventoryTabel>>() {
             @Override
             public void onSubscribe(Disposable d) {

             }

             @Override
             public void onSuccess(List<InventoryTabel> inventoryModels) {
                    listOfOperation.setValue(inventoryModels);
             }

             @Override
             public void onError(Throwable e) {

             }
         });

     }


    public void getOperationsOnline(){
        Helper.showDialog();
        RemoteRepositry.getEggoperations().subscribeWith(new SingleObserver<EggsModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(EggsModel eggsModel) {
                Log.v("pppp",eggsModel.asset_code.get(0).get(0).adjustment_id.get(0)+"");
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



//        RemoteRepositry.getoperationsOnline().subscribeWith(new SingleObserver<InventoryModelResult>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onSuccess(InventoryModelResult inventoryModelResult) {
//                if (inventoryModelResult.isStatus())
//                LocalRepositry.saveOperation(inventoryModelResult.getAsset_adjust());
//                Log.v("nnnnn","qdweerurbrd");
//                Helper.dismiss();
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.v("nnnnn",e.getMessage());
//                Helper.dismiss();
//            }
//        });

    }




}
