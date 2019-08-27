package com.g2m.asset.roomInfo;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.g2m.asset.models.dataModels.AssetModel;
import com.g2m.asset.models.network.DataModel;
import com.g2m.asset.models.repositries.LocalRepositry;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class RoomViewModel extends ViewModel {
MutableLiveData<List<AssetModel>>assetList=new MutableLiveData<>();

    MutableLiveData<List<DataModel>>catsList=new MutableLiveData<>();

    public void getAssetsFromRoom(String barcode){
        LocalRepositry.getAssetFromRoom(barcode).subscribeWith(new SingleObserver<List<AssetModel>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(List<AssetModel> assetModels) {
                Log.v("rrrr",assetModels.size()+"   nmnm");
                assetList.setValue(assetModels);
                setCategories(assetModels);
                filterAsset(-1);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }
    public void filterAsset(final int i) {
        final List<AssetModel>assetModelList=new ArrayList<>();
        if(i!=-1){
            Observable<AssetModel>filterCategories= Observable.fromIterable(assetList.getValue())
                    .filter(new Predicate<AssetModel>() {
                        @Override
                        public boolean test(AssetModel assetModel) throws Exception {
                            if(assetModel.cat_id==i)
                            return true;
                            else return false;
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            filterCategories.subscribeWith(new Observer<AssetModel>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(AssetModel assetModel) {
                    assetModelList.add(assetModel);

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {
                    assetList.getValue().clear();
                    assetList.setValue(assetModelList);
                }
            });

        }
    }

    public void setCategories(List<AssetModel> assetModels){
        final List<DataModel>catlist=new ArrayList<>();
    Observable<DataModel>filterCategories= Observable.fromIterable(assetModels)
            .map(new Function<AssetModel, DataModel>() {
                @Override
                public DataModel apply(AssetModel assetModel) throws Exception {
                    DataModel dataModel=new DataModel();
                    dataModel.id=assetModel.cat_id;
                    dataModel.name=assetModel.cat_name;
                    return dataModel;
                }
            })

                .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    filterCategories.subscribeWith(new Observer<DataModel>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(DataModel dataModel) {
             catlist.add(dataModel);
            for (int i=0;i<catlist.size()-1;i++){
                if(catlist.get(i).id==dataModel.id)
                    catlist.remove(dataModel);
                    break;
            }
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {
           catsList.setValue(catlist);
        }
    });

}

    public LiveData<String> getDepartment(String code) {
        return LocalRepositry.getDepartment(code);
    }
}

