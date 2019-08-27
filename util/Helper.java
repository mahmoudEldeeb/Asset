package com.g2m.asset.util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.g2m.asset.models.dataModels.AssetModel;
import com.g2m.asset.models.dataModels.LocationModel;
import com.g2m.asset.models.dataModels.RoomModel;
import com.g2m.asset.models.dataModels.SubLocationModel;
import com.g2m.asset.models.network.DataModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Helper {


    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) Constants.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static List<LocationModel> convertToLocations(JSONArray location) {
        List<LocationModel> locationModels = new ArrayList<>();
        for (int i = 0; i < location.length(); i++) {
            try {
                LocationModel model = new LocationModel();
                JSONObject object = location.getJSONObject(i);
                model.loc_barcode = object.getString("barcode");
                model.loc_t_id = object.getInt("id");
                model.loc_name = object.getString("name");
                Log.v("aaaaa", "fffddd");
                locationModels.add(model);
            } catch (JSONException e) {
                Log.v("aaaaa", e.getMessage());
                e.printStackTrace();
            }

        }
        return locationModels;
    }

    public static List<SubLocationModel> convertToSubLocations(JSONArray location) {
        List<SubLocationModel> locationModels = new ArrayList<>();
        for (int i = 0; i < location.length(); i++) {
            try {
                SubLocationModel model = new SubLocationModel();
                JSONObject object = location.getJSONObject(i);
                model.barcode = object.getString("barcode");
                model.id = object.getInt("id");
                model.name = object.getString("name");
                model.loc_id = getData(object.getJSONArray("location_id")).id;
                Log.v("aaaaa", "submodel");
                locationModels.add(model);
            } catch (JSONException e) {
                Log.v("aaaaa", e.getMessage());
                e.printStackTrace();
            }

        }
        return locationModels;
    }

    public static List<RoomModel> convertToRoom(JSONArray location) {
        List<RoomModel> locationModels = new ArrayList<>();
        for (int i = 0; i < location.length(); i++) {
            try {
                RoomModel model = new RoomModel();
                JSONObject object = location.getJSONObject(i);
                model.barcode = object.getString("barcode");
                model.id = object.getInt("id");
                model.name = object.getString("name");
                model.loc_id = getData(object.getJSONArray("location_id")).id;
                model.sub_loc_id = getData(object.getJSONArray("sub_location")).id;
                model.department = getData(object.getJSONArray("department_id")).name;
                Log.v("aaaaa", "room");
                locationModels.add(model);
            } catch (JSONException e) {
                Log.v("aaaaa", e.getMessage());
                e.printStackTrace();
            }

        }
        return locationModels;
    }

    public static List<AssetModel> convertToAssetModel(JSONArray location) {
        List<AssetModel> list = new ArrayList<>();
        for (int i = 0; i < location.length(); i++) {
            try {
                AssetModel model = new AssetModel();
                JSONObject object = location.getJSONObject(i);
                model.barcode = object.getString("barcode");
                model.id = object.getInt("id");
                model.name = object.getString("name");
                model.describtion = object.getString("desc");
                model.cat_id = getData(object.getJSONArray("category")).id;
                model.cat_name = getData(object.getJSONArray("category")).name;
                DataModel dataModel = getData(object.getJSONArray("location_id"));

                model.loc_id = dataModel.id;

                dataModel = getData(object.getJSONArray("sub_location"));
                model.sub_loc_id = dataModel.id;

                dataModel = getData(object.getJSONArray("room"));
                model.room_id = dataModel.id;
                model.statues = getData(object.getJSONArray("status")).name;

                Log.v("aaaaa", "asset");
                list.add(model);
            } catch (JSONException e) {
                Log.v("aaaaa", e.getMessage());
                e.printStackTrace();
            }

        }
        return list;
    }

    public static DataModel getData(JSONArray array) {
        DataModel model = new DataModel();

        try {
            model.id = array.getInt(0);
            model.name = array.getString(1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return model;
    }

    static ProgressDialog pd;

    public static void showDialog() {
        pd = new ProgressDialog(Constants.context);
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage("loading ......");
        pd.show();
    }

    public static void dismiss() {
        pd.dismiss();
    }

    public static void showNoInternet() {
        Toast.makeText(Constants.context,"no internet",Toast.LENGTH_LONG).show();
    }
}