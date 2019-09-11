package com.g2m.asset.models.repositries;

import android.util.Log;
import android.widget.LinearLayout;

import androidx.lifecycle.LiveData;

import com.g2m.asset.assetInfo.AseetModel;
import com.g2m.asset.models.Prefrences;
import com.g2m.asset.models.dataModels.AllDataField;
import com.g2m.asset.models.dataModels.AssetModel;
import com.g2m.asset.models.dataModels.AssetOfInventory;
import com.g2m.asset.models.dataModels.InventoryModel;
import com.g2m.asset.models.dataModels.InventoryModelResult;
import com.g2m.asset.models.dataModels.InventoryTabel;
import com.g2m.asset.models.dataModels.LocationModel;
import com.g2m.asset.models.dataModels.RoomModel;
import com.g2m.asset.models.dataModels.SubLocationModel;
import com.g2m.asset.models.dataModels.TransfeerModel;
import com.g2m.asset.models.databases.DataDao;
import com.g2m.asset.models.databases.LocalDatabase;
import com.g2m.asset.oldTransfeer.OldTransfeerModel;
import com.g2m.asset.scannDialog.ScannModels;
import com.g2m.asset.util.Helper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.operators.flowable.FlowableFromCallable;
import io.reactivex.schedulers.Schedulers;

public class LocalRepositry {

    private static DataDao dataDao;

    public static DataDao getDao(){
        LocalDatabase db = LocalDatabase.getDatabase();
        return db.dao();
    }

    public static void insertItems(final List<LocationModel>locationModels, final List<SubLocationModel>subLocationModels
    , final List<RoomModel>roomModels, final List<AssetModel>assetModels){

        final Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                getDao().insertAllLocs(locationModels);
                getDao().insertAllSubLocs(subLocationModels);
                getDao().insertAllRoom(roomModels);
                getDao().insertAllAsset(assetModels);
                Prefrences.changeFirstTime();
                Helper.dismiss();

            }
        });
    }
    public static LiveData<List<AssetModel>>getAllAssets(){
        return getDao().getAllAsset();
    }

    public static LiveData<AllDataField>getAssetData(String barcode){
        return getDao().getAssetData(barcode);

    }
    public static Single<List<AssetModel>> getAssetFromRoom(String barcode){
        return getDao().getAsetOfRoom(barcode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    public static LiveData<RoomModel> getDepartment(String code) {
        return getDao().getDepartment(code);
    }
    public static LiveData<List<LocationModel>>getLocations(){
        return getDao().getLocations();
    }
    public static LiveData<List<SubLocationModel>>getSubLocations(int id){
        return getDao().getSubLocations(id);
    }

    public static LiveData<List<RoomModel>> getRooms(int idLoc, int idsubLoc) {
        return getDao().getRooms(idLoc,idsubLoc);
    }

    public static void saveTransfer(final List<TransfeerModel> list) {
        final Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                getDao().insertTransfer(list);
            }
        });
    }

    public static Single<List<TransfeerModel>> getTransfers() {
        return getDao().getTransfers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Single<List<AssetModel>> getAllData() {
        return getDao().getAllData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static void update(final int loc, final int sub_loc, final int room, final String barcode){

        getDao().getAssetModel(barcode).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).
                subscribeWith(new SingleObserver<AssetModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(final AssetModel assetModel) {

                assetModel.loc_id=loc;
                assetModel.sub_loc_id=sub_loc;
                assetModel.room_id=room;

            updateModel(assetModel);

            }

            @Override
            public void onError(Throwable e) {
Log.v("vvvvv",e.getMessage());
            }
        })
        ;
    }

    private static void updateModel(final AssetModel assetModel) {
        final Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                getDao().updateAsset(assetModel);
                getDao().updateAssetTransferModel(false,assetModel.barcode);
            }
        });

    }

    public static void updateAssets(List<TransfeerModel>list){
        Observable<TransfeerModel>
                observable= Observable.fromIterable(list)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                ;
        observable.subscribeWith(new Observer<TransfeerModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(TransfeerModel transfeerModel) {
                update(transfeerModel.location_id_new,transfeerModel.sub_location_new
                        ,transfeerModel.room_new,transfeerModel.barcode);
                Log.v("sssss","ddddddd");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        })
        ;
    }

    public static LiveData<Integer> getTransportType(){
        return getDao().getTransfersCount(true);
    }


    public static LiveData<List<InventoryTabel>> getAllOperations() {
        return  getDao().getAllOperations();
    }

    public static void saveOperation(final List<InventoryModel> asset_adjust, final List<AssetOfInventory> listAsset) {


        final List<InventoryTabel>list=new ArrayList<>();
        for(int i=0;i<asset_adjust.size();i++){
            list.add(new InventoryTabel(asset_adjust.get(i).getId(),asset_adjust.get(i).getDisplay_name()
                    ,asset_adjust.get(i).getDate()));

        }

        final Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                getDao().insertOperations(list);
                getDao().insertAssetOfOperations(listAsset);
                Helper.dismiss();
                Prefrences.changeFirstInventory();
            }
        });


    }


    public static LiveData<List<ScannModels>>getAssetsOfInventory(int inv_id){
        return getDao().getAssetsOfInventory(inv_id);
    }

    public static void checkScannedOrNot(String barcode) {
         getDao().checkScannedOrNot();
    }

    public static void saveWhatScan(final AssetOfInventory asset) {

        final Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                getDao().saveWhatScan(asset);
            }
        });
    }


    public static Single<List<AssetOfInventory>> getInventoryNotSend(int id) {
        return getDao().getInventoryIdNotSent(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static void saveInventAsSent(final int id) {
        final Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                getDao().setInventoryAsSent(true,id);
            }
        });
    }

    public static void delete(final int inv_id) {
        final Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                getDao().delete(inv_id);
                getDao().deleteAsset(inv_id);
            }
        });
    }

    public static LiveData<List<OldTransfeerModel>>getAllTransfeer(){
        return getDao().getAllTransfeer();
    }
}
