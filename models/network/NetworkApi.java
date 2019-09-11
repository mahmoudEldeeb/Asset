package com.g2m.asset.models.network;

import com.g2m.asset.models.AllInventoryData;
import com.g2m.asset.models.dataModels.InventoryModelResult;
import com.g2m.asset.models.dataModels.Result;

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
    Single<Result> sendTransfer(@Field("data") String data);

    @GET("asset_adjust.php")
    Single<InventoryModelResult> getoperations();

    @GET("asset_adjust.php")
    Single<AllInventoryData> getAllInventoryData();

    @POST("asset_adjust_confirm.php")
    @FormUrlEncoded
    Single<Result> assetConfirm(@Field("data") String data,@Field("order_id") int order_id);

}