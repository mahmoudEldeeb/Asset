package com.g2m.asset.transfeer;


import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.g2m.asset.R;
import com.g2m.asset.databinding.ActivityTransfeerBinding;
import com.g2m.asset.interfaces.ClickListener;
import com.g2m.asset.models.dataModels.AllDataField;
import com.g2m.asset.models.dataModels.AssetModel;
import com.g2m.asset.models.dataModels.LocationModel;
import com.g2m.asset.models.dataModels.RoomModel;
import com.g2m.asset.models.dataModels.SubLocationModel;
import com.g2m.asset.models.dataModels.TransfeerModel;
import com.g2m.asset.util.Constants;
import com.g2m.asset.util.Helper;
import com.g2m.asset.util.ViewDialog;

import java.util.ArrayList;
import java.util.List;

public class TransfeerActivity extends AppCompatActivity implements ClickListener {
ActivityTransfeerBinding transfeerBinding;
TransferViewModel viewModel;
List<LocationModel>locationModelList=new ArrayList<>();
    List<SubLocationModel>subLocationModelList=new ArrayList<>();
    List<RoomModel>roomModelListr=new ArrayList<>();
    AllDataField model=new AllDataField();
    TransfeerModel oldmModel=new TransfeerModel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       transfeerBinding= DataBindingUtil.setContentView(this,R.layout.activity_transfeer);
        getSupportActionBar().setTitle("Asset Transfer");
        Constants.context=this;

       viewModel= ViewModelProviders.of(this).get(TransferViewModel.class);
       transfeerBinding.setClickListener(this);
        Intent intent=getIntent();
        if(intent!=null){
            Constants.transferFinsh=true;
            transfeerBinding.barcode.setText(intent.getStringExtra("barcode"));
            onTextChange();

        }
       viewModel.getLocations().observe(this, new Observer<List<LocationModel>>() {
           @Override
           public void onChanged(List<LocationModel> locationModels) {
               locationModelList.addAll(locationModels);
               transfeerBinding.spinnerMainLocation
                       .setAdapter(getAdapterOfSpinner(SimpelSpinnerAdapter.getStringDataFroomLoc(locationModels)));

              if(oldmModel!=null)
              {for(int i=0;i<locationModels.size();i++){
                   Log.v("rrrrr",locationModelList.get(i).loc_t_id+"  "+oldmModel.location_id_new);

                   if (locationModels.get(i).loc_t_id==oldmModel.location_id_new){
                       Log.v("rrrrr","dffffffffff");
                       transfeerBinding.spinnerMainLocation.setSelection(i+1);
                   }
               }
              }

           }
       });
       transfeerBinding.spinnerMainLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               if(i!=0) {
                   Log.v("qqqqqc",locationModelList.get(i - 1).loc_t_id+ "  ");
                   getsubLocation(locationModelList.get(i - 1).loc_t_id);
               }

           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });
       transfeerBinding.spinnerSubLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               if(i!=0){
                   int locPos=transfeerBinding.spinnerMainLocation.getSelectedItemPosition()-1;
                   getRooms(locationModelList.get(locPos).loc_t_id,subLocationModelList.get(i-1).id);
               }
           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });

       transfeerBinding.save.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
Helper.showDialog();
               try {
                   if (model.getAssetModel().barcode == null) {
                       ViewDialog.showDialog("please fill all data right ", false);
                       Helper.dismiss();
                   } else {
                       viewModel.saveOfline(getData());

                   }


               } catch (ArrayIndexOutOfBoundsException e) {
                   Helper.dismiss();
                   ViewDialog.showDialog("please fill all data right", false);
               }
           }

       });

        fillData();
    }

    private void fillData() {

        Intent intent=getIntent();
        if(intent!=null){
            try {
                oldmModel = (TransfeerModel) intent.getSerializableExtra("transfeerObject");
                transfeerBinding.barcode.setText(oldmModel.barcode);
                onTextChange();
                Log.v("rrrrr", "bbbbbbbbbbb");
            }catch (NullPointerException y){}



        }
    }

    private void getRooms(int idLoc, int idsubLoc) {
        viewModel.getRooms(idLoc,idsubLoc).observe(this, new Observer<List<RoomModel>>() {
            @Override
            public void onChanged(List<RoomModel> roomModels) {
                roomModelListr.clear();
                roomModelListr.addAll(roomModels);
                transfeerBinding.spinnerEmployeeLocation
                        .setAdapter(getAdapterOfSpinner(SimpelSpinnerAdapter.getStringDataFromRoom(roomModels)));
                if(oldmModel!=null)
                {for(int i=0;i<roomModels.size();i++){


                    if (roomModels.get(i).id==oldmModel.room_new){
                        Log.v("rrrrr","dffffffffff");
                        transfeerBinding.spinnerEmployeeLocation.setSelection(i+1);
                    }
                }
                }

            }
        });
    }

    private void getsubLocation(int id) {
        Log.v("qqqqq",id+"   b");
        viewModel.getSubLoc(id).observe(this, new Observer<List<SubLocationModel>>() {
            @Override
            public void onChanged(List<SubLocationModel> subLocationModels) {
                Log.v("qqqqq",subLocationModels.size()+"   b");
                subLocationModelList.clear();
                subLocationModelList.addAll(subLocationModels);
                transfeerBinding.spinnerSubLocation
                        .setAdapter(getAdapterOfSpinner(SimpelSpinnerAdapter.getStringDataFromSub(subLocationModels)));
                if(oldmModel!=null)
                {for(int i=0;i<subLocationModels.size();i++){

                    if (subLocationModels.get(i).id==oldmModel.sub_location_new){
                        Log.v("rrrrr","dffffffffff");
                        transfeerBinding.spinnerSubLocation.setSelection(i+1);
                    }
                }
                }


            }
        });
    }


    public ArrayAdapter<String> getAdapterOfSpinner(List<String> catsList){
        ArrayAdapter<String>arrayAdapter= new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, catsList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
return arrayAdapter;
    }

    @Override
    public void onClick() {
        Helper.showDialog();
       try {
           if(model.getAssetModel().barcode==null){
               ViewDialog.showDialog("please fill all data right ",false);
               Helper.dismiss();
           }
           else {

               if (Helper.isNetworkAvailable())
                   viewModel.saveTransfer(getData());
               else {
                   ViewDialog.showDialog(getResources().getString(R.string.no_internet_transfer), true);
                   viewModel.saveOfline(getData());

               }
           }

       }catch (ArrayIndexOutOfBoundsException e){Helper.dismiss();
       ViewDialog.showDialog("please fill all data right",false);
       }

    }

    public List<TransfeerModel>getData(){
        TransfeerModel transfeerModel = new TransfeerModel();
        transfeerModel.asset_id_old = model.getAssetModel().id;
        transfeerModel.barcode = model.getAssetModel().barcode;
        transfeerModel.location_id_old = model.getLocationModel().loc_t_id;
        transfeerModel.sub_location_old = model.getSubLocationModel().id;
        transfeerModel.room_old = model.getRoomModel().id;
        transfeerModel.location_id_new = locationModelList.
                get(transfeerBinding.spinnerMainLocation.getSelectedItemPosition() - 1).loc_t_id;
        transfeerModel.sub_location_new = subLocationModelList.
                get(transfeerBinding.spinnerSubLocation.getSelectedItemPosition() - 1).id;
        transfeerModel.room_new = roomModelListr.
                get(transfeerBinding.spinnerEmployeeLocation.getSelectedItemPosition() - 1).id;
        transfeerModel.status = true;
        List<TransfeerModel> list = new ArrayList<>();
        list.add(transfeerModel);
return list;
    }
    @Override
    public void onTextChange() {
        String code =transfeerBinding.barcode.getText().toString();
        if(code.length()==6) {
            Log.v("qqqqq",code);
            getAssetData(code);
        }
    }
    public  void getAssetData(String code){
        viewModel.getAssetLocation(code).observe(this, new Observer<AllDataField>() {
            @Override
            public void onChanged(AllDataField allDataField) {
                 model=allDataField;
                  transfeerBinding.setAssetModel(model);
            }
        });





    }
}
