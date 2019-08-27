package com.g2m.asset.models.network;

import com.g2m.asset.models.EggsModel;
import com.g2m.asset.models.dataModels.InventoryModelResult;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface NetworkApi {

    @GET("assets.php")
    Single<ResponseBody> getAllData();

    @POST("asset_transfer.php")
    @FormUrlEncoded
    Single<ResponseBody> sendTransfer(@Field("data") String data);

    @GET("asset_adjust.php")
    Single<InventoryModelResult> getoperations();

    @GET("asset_adjust.php")
    Single<EggsModel> getEggoperations();



    @POST("asset_adjust_confirm.php")
    @FormUrlEncoded
    Single<ResponseBody> assetConfirm(@Field("data") String data,@Field("order_id") int order_id);

}