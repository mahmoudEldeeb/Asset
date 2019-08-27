package com.g2m.asset.models.repositries;

import com.g2m.asset.models.EggsModel;
import com.g2m.asset.models.dataModels.InventoryModel;
import com.g2m.asset.models.dataModels.InventoryModelResult;
import com.g2m.asset.models.network.RetrofitConnection;
import com.g2m.asset.util.Helper;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class RemoteRepositry {
    public static Single<ResponseBody>getAllData(){
        return RetrofitConnection.getNetworkConnection().getAllData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public static Single<ResponseBody>savTransfeer(String data){
        return RetrofitConnection.getNetworkConnection().sendTransfer(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public static Single<InventoryModelResult>getoperationsOnline(){
            return RetrofitConnection.getNetworkConnection().getoperations()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
    }

    public static Single<EggsModel>getEggoperations(){
        return RetrofitConnection.getNetworkConnection().getEggoperations()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public static Single<ResponseBody>assetConfirm(String data,int inv_id){
        return RetrofitConnection.getNetworkConnection().assetConfirm(data,inv_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
