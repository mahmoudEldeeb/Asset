package com.g2m.asset.oldTransfeer;

import android.app.Activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.g2m.asset.R;
import com.g2m.asset.models.dataModels.Result;
import com.g2m.asset.models.dataModels.TransfeerModel;
import com.g2m.asset.models.repositries.LocalRepositry;
import com.g2m.asset.models.repositries.RemoteRepositry;
import com.g2m.asset.util.Constants;
import com.g2m.asset.util.Helper;
import com.g2m.asset.util.ViewDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class OldTransfeerViewModel extends ViewModel {
    public LiveData<List<OldTransfeerModel>>getAllTransfeer(){
        return LocalRepositry.getAllTransfeer();
    }

    public void sendTransfer(final List<TransfeerModel> list){
        Helper.showDialog();
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
            RemoteRepositry.savTransfeer(jsonArray.toString()).subscribeWith(new SingleObserver<Result>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onSuccess(Result result) {
                    if(result.status){

                        if(Constants.transferFinsh) {
                            Activity activity = (Activity) Constants.context;
                            // activity.finish();
                        }}
                    Helper.showToast(result.message);
                    Helper.dismiss();

                }
                @Override
                public void onError(Throwable e) {
                    Helper.dismiss();
                    ViewDialog.showDialog(Constants.context.getResources().getString(R.string.no_connection_transfer),true);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
