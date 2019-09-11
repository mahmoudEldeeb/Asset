package com.g2m.asset.homePage;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.g2m.asset.R;
import com.g2m.asset.models.dataModels.AllDataField;
import com.g2m.asset.models.dataModels.AssetModel;
import com.g2m.asset.models.dataModels.LocationModel;
import com.g2m.asset.models.dataModels.Result;
import com.g2m.asset.models.dataModels.TransfeerModel;
import com.g2m.asset.models.repositries.LocalRepositry;
import com.g2m.asset.models.repositries.RemoteRepositry;
import com.g2m.asset.models.dataModels.RoomModel;
import com.g2m.asset.models.dataModels.SubLocationModel;
import com.g2m.asset.util.Constants;
import com.g2m.asset.util.Helper;
import com.g2m.asset.util.ViewDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class HomeViewModel extends ViewModel {
LiveData<List<AssetModel>>allAsset=new MediatorLiveData<>();
    public void getAllData(){
        Helper.showDialog();
        RemoteRepositry.getAllData().subscribeWith(new SingleObserver<ResponseBody>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(ResponseBody responseBody) {
                try {
                    //String a="{\"location\":[{\"name\":\"Main building\",\"id\":1,\"barcode\":\"000001\"},{\"name\":\"AUC office\",\"id\":2,\"barcode\":\"0002\"}],\"sub_location\":[{\"name\":\"first floor\",\"id\":1,\"location_id\":[1,\"Main building\"],\"barcode\":\"000001\"},{\"name\":\"Ground floor\",\"id\":2,\"location_id\":[2,\"AUC office\"],\"barcode\":\"00002\"}],\"room\":[{\"barcode\":\"000001\",\"department_id\":[1,\"Finance\"],\"location_id\":[1,\"Main building\"],\"sub_location\":[1,\"first floor\"],\"name\":\"Meeting Room 1\",\"id\":1},{\"barcode\":\"000002\",\"department_id\":[1,\"Finance\"],\"location_id\":[1,\"Main building\"],\"sub_location\":[1,\"first floor\"],\"name\":\"Meeting Room2\",\"id\":2},{\"barcode\":\"000003\",\"department_id\":[1,\"Finance\"],\"location_id\":[1,\"Main building\"],\"sub_location\":[1,\"first floor\"],\"name\":\"Department head office\",\"id\":3},{\"barcode\":\"000004\",\"department_id\":[2,\"IT\"],\"location_id\":[2,\"AUC office\"],\"sub_location\":[2,\"Ground floor\"],\"name\":\"IT Room\",\"id\":4}],\"asset\":[{\"status\":[1,\"damage\"],\"barcode\":\"000001\",\"sub_category\":false,\"room\":[1,\"Meeting Room 1\"],\"location_id\":[1,\"Main building\"],\"desc\":\"\\u0643\\u0631\\u0633\\u0649 \\u0645\\u062a\\u062d\\u0631\\u0643 \",\"name\":\"\\\"\\u0643\\u0631\\u0633\\u0649 \\u0645\\u062a\\u062d\\u0631\\u0643 \\\"\\u062c\\u0644\\u062f\",\"id\":1,\"sub_location\":[1,\"first floor\"],\"category\":[1,\"\\u0643\\u0631\\u0627\\u0633\\u0649\"]},{\"status\":[3,\"good\"],\"barcode\":\"000002\",\"sub_category\":[5,\"\\u0643\\u0631\\u0627\\u0633\\u0649 \\u0645\\u0643\\u062a\\u0628 \\u062b\\u0627\\u0628\\u062a\\u0629\"],\"room\":[3,\"Department head office\"],\"location_id\":[1,\"Main building\"],\"desc\":false,\"name\":\"\\u0643\\u0631\\u0633\\u0649 \\u0645\\u0643\\u062a\\u0628 \\u0628\\u0639\\u062c\\u0644\",\"id\":2,\"sub_location\":[1,\"first floor\"],\"category\":[1,\"\\u0643\\u0631\\u0627\\u0633\\u0649\"]}]}";
                            JSONObject object=new JSONObject(responseBody.string());
                   List<LocationModel>locList=Helper.convertToLocations(object.getJSONArray("location"));
                    List<SubLocationModel>sublist=Helper.convertToSubLocations(object.
                            getJSONArray("sub_location"));
                    List<RoomModel>roomList=Helper.convertToRoom(object.getJSONArray("room"));

                    List<AssetModel>assetList=Helper.convertToAssetModel(object.getJSONArray("asset"));

                    saveData(locList,sublist,roomList,assetList);

                } catch (IOException e) {
                    e.printStackTrace() ;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.v("rrrrrr",e.getMessage());
                Helper.dismiss();
//                try {
//                    String a="{\"location\":[{\"name\":\"Main building\",\"id\":1,\"barcode\":\"000001\"},{\"name\":\"AUC office\",\"id\":2,\"barcode\":\"0002\"}],\"sub_location\":[{\"name\":\"first floor\",\"id\":1,\"location_id\":[1,\"Main building\"],\"barcode\":\"000001\"},{\"name\":\"Ground floor\",\"id\":2,\"location_id\":[2,\"AUC office\"],\"barcode\":\"00002\"}],\"room\":[{\"barcode\":\"000001\",\"department_id\":[1,\"Finance\"],\"location_id\":[1,\"Main building\"],\"sub_location\":[1,\"first floor\"],\"name\":\"Meeting Room 1\",\"id\":1},{\"barcode\":\"000002\",\"department_id\":[1,\"Finance\"],\"location_id\":[1,\"Main building\"],\"sub_location\":[1,\"first floor\"],\"name\":\"Meeting Room2\",\"id\":2},{\"barcode\":\"000003\",\"department_id\":[1,\"Finance\"],\"location_id\":[1,\"Main building\"],\"sub_location\":[1,\"first floor\"],\"name\":\"Department head office\",\"id\":3},{\"barcode\":\"000004\",\"department_id\":[2,\"IT\"],\"location_id\":[2,\"AUC office\"],\"sub_location\":[2,\"Ground floor\"],\"name\":\"IT Room\",\"id\":4}],\"asset\":[{\"status\":[1,\"damage\"],\"barcode\":\"000001\",\"sub_category\":false,\"room\":[1,\"Meeting Room 1\"],\"location_id\":[1,\"Main building\"],\"desc\":\"\\u0643\\u0631\\u0633\\u0649 \\u0645\\u062a\\u062d\\u0631\\u0643 \",\"name\":\"\\\"\\u0643\\u0631\\u0633\\u0649 \\u0645\\u062a\\u062d\\u0631\\u0643 \\\"\\u062c\\u0644\\u062f\",\"id\":1,\"sub_location\":[1,\"first floor\"],\"category\":[1,\"\\u0643\\u0631\\u0627\\u0633\\u0649\"]},{\"status\":[3,\"good\"],\"barcode\":\"000002\",\"sub_category\":[5,\"\\u0643\\u0631\\u0627\\u0633\\u0649 \\u0645\\u0643\\u062a\\u0628 \\u062b\\u0627\\u0628\\u062a\\u0629\"],\"room\":[3,\"Department head office\"],\"location_id\":[1,\"Main building\"],\"desc\":false,\"name\":\"\\u0643\\u0631\\u0633\\u0649 \\u0645\\u0643\\u062a\\u0628 \\u0628\\u0639\\u062c\\u0644\",\"id\":2,\"sub_location\":[1,\"first floor\"],\"category\":[1,\"\\u0643\\u0631\\u0627\\u0633\\u0649\"]}]}";
//                    JSONObject object=new JSONObject(a);
//                    List<LocationModel>locList=Helper.convertToLocations(object.getJSONArray("location"));
//                    List<SubLocationModel>sublist=Helper.convertToSubLocations(object.
//                            getJSONArray("sub_location"));
//                    List<RoomModel>roomList=Helper.convertToRoom(object.getJSONArray("room"));
//
//                    List<AssetModel>assetList=Helper.convertToAssetModel(object.getJSONArray("asset"));
//
//                    saveData(locList,sublist,roomList,assetList);
//
//                }  catch (JSONException c) {
//                    e.printStackTrace();
//                }
            }
        });
    }

    private void saveData(List<LocationModel> locList, List<SubLocationModel> sublist, List<RoomModel> roomList, List<AssetModel> assetList) {
        LocalRepositry.insertItems(locList,sublist,roomList,assetList);
    }

    public LiveData<List<AssetModel>> getAllAsset() {
        return allAsset;

    }




    public void sendData() {
        Helper.showDialog();

    LocalRepositry.getTransfers().subscribeWith(new SingleObserver<List<TransfeerModel>>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onSuccess(List<TransfeerModel> list) {
            saveTransfer(list);
        }

        @Override
        public void onError(Throwable e) {
            Helper.dismiss();
        }
    });
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
            RemoteRepositry.savTransfeer(jsonArray.toString()).subscribeWith(new SingleObserver<Result>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onSuccess(Result result) {

                        if(result.status) {
                            LocalRepositry.updateAssets(list);
                        }
                        Helper.showToast(result.message);
                        Helper.dismiss();

                }
                @Override
                public void onError(Throwable e) {
                    ViewDialog.showDialog(Constants.context.getResources()
                            .getString(R.string.no_connection_transfer),false);
                    Helper.dismiss();
                }
            });
            Log.v("oooooo","      "+jsonArray.toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


public LiveData<Integer> getTransferCount(){
      return LocalRepositry.getTransportType();

}
}
