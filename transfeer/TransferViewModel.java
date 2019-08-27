package com.g2m.asset.transfeer;

import android.app.Activity;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.g2m.asset.R;
import com.g2m.asset.models.dataModels.AllDataField;
import com.g2m.asset.models.dataModels.AssetModel;
import com.g2m.asset.models.dataModels.LocationModel;
import com.g2m.asset.models.dataModels.RoomModel;
import com.g2m.asset.models.dataModels.SubLocationModel;
import com.g2m.asset.models.dataModels.TransfeerModel;
import com.g2m.asset.models.repositries.LocalRepositry;
import com.g2m.asset.models.repositries.RemoteRepositry;
import com.g2m.asset.util.Constants;
import com.g2m.asset.util.Helper;
import com.g2m.asset.util.ViewDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

public class TransferViewModel extends ViewModel {
    public LiveData<AllDataField> getAssetLocation(String code){
       return LocalRepositry.getAssetData(code);
    }

    public LiveData<List<LocationModel>> getLocations() {
        return LocalRepositry.getLocations();
    }

    public LiveData<List<SubLocationModel>> getSubLoc(int id) {
        Log.v("qqqqq",id+"   n");
        return LocalRepositry.getSubLocations(id);
    }

    public LiveData<List<RoomModel>> getRooms(int idLoc, int idsubLoc) {
        return LocalRepositry.getRooms(idLoc,idsubLoc);
    }

public void saveTransfer(final List<TransfeerModel> list){

    JSONArray jsonArray=new JSONArray();

    try {
        for(int i=0;i<list.size();i++) {
            TransfeerModel model=list.get(i);
            JSONObject jsonObj = new JSONObject();
            jsonObj.put(Constants.barcode, model.barcode);
            jsonObj.put(Constants.asset_id_old,model.asset_id_old);

            jsonObj.put(Constants.location_id_old,model.location_id_old);
            jsonObj.put(Constants.sub_location_old,model.sub_location_old);
            jsonObj.put(Constants.room_old,model.room_old);
            jsonObj.put(Constants.location_id_new,model.location_id_new);
            jsonObj.put(Constants.sub_location_new,model.sub_location_new);
            jsonObj.put(Constants.room_new,model.room_new);
            jsonArray.put(jsonObj);
        }

        //  jsonObj1.put("asd",jsonArray);
        RemoteRepositry.savTransfeer(jsonArray.toString()).subscribeWith(new SingleObserver<ResponseBody>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(ResponseBody responseBody) {
                try {
                    Log.v("oooo",responseBody.string());
                 LocalRepositry.updateAssets(list);
                    if(Constants.transferFinsh) {
                        Activity activity = (Activity) Constants.context;
                        activity.finish();
                    }
                 Helper.dismiss();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable e) {
                saveOfline(list);
                ViewDialog.showDialog(Constants.context.getResources().getString(R.string.no_connection_transfer),true);
            }
        });

    } catch (JSONException e) {
        e.printStackTrace();
    }
}

    public void saveOfline(List<TransfeerModel> list) {
        LocalRepositry.saveTransfer(list);
        Helper.dismiss();
        if(Constants.transferFinsh) {
            Activity activity = (Activity) Constants.context;
            activity.finish();
        }

    }
}
