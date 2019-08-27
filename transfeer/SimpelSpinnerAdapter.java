package com.g2m.asset.transfeer;

import android.util.Log;

import com.g2m.asset.models.dataModels.LocationModel;
import com.g2m.asset.models.dataModels.RoomModel;
import com.g2m.asset.models.dataModels.SubLocationModel;

import java.util.ArrayList;
import java.util.List;

public class SimpelSpinnerAdapter {
    public List<String>list=new ArrayList<>();

    public static List<String> getStringDataFroomLoc(List<LocationModel> locationModels) {
        List<String>stringList=new ArrayList<>();
        stringList.add("choose ...");
        Log.v("qqq",locationModels.size()+"    n,n");
        for (int i=0;i<locationModels.size();i++){
            stringList.add(locationModels.get(i).loc_name);
        }
        return stringList;
    }
    public static List<String> getStringDataFromRoom(List<RoomModel> locationModels) {
        List<String>stringList=new ArrayList<>();
        stringList.add("choose ...");
        Log.v("qqq",locationModels.size()+"    n,n");
        for (int i=0;i<locationModels.size();i++){
            stringList.add(locationModels.get(i).name);
        }
        return stringList;
    }
    public static List<String> getStringDataFromSub(List<SubLocationModel> locationModels) {
        List<String>stringList=new ArrayList<>();
        stringList.add("choose ...");
        Log.v("qqq",locationModels.size()+"    n,n");
        for (int i=0;i<locationModels.size();i++){
            stringList.add(locationModels.get(i).name);
        }
        return stringList;
    }

//    public SimpelSpinnerAdapter(List<LocationModel>lis){
//        for (int i=0;i<lis.size();i++){
//            list.add(lis.get(i).name);
//        }
//    }
}
