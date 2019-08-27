package com.g2m.asset.roomInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.g2m.asset.R;
import com.g2m.asset.databinding.ActivityRoomBinding;
import com.g2m.asset.interfaces.ClickListener;
import com.g2m.asset.models.dataModels.AssetModel;
import com.g2m.asset.models.network.DataModel;

import java.util.ArrayList;
import java.util.List;

public class RoomActivity extends AppCompatActivity implements ClickListener {
ActivityRoomBinding  roomBinding;
RoomAdapter roomAdapter;
RoomViewModel viewModel;
List<DataModel>categoryList=new ArrayList<>();
     ArrayAdapter<String> catsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        roomBinding= DataBindingUtil.setContentView(this,R.layout.activity_room);
        getSupportActionBar().setTitle("Room Information");
        roomBinding.setClickListener(this);
        getSpinersData();
        viewModel= ViewModelProviders.of(this).get(RoomViewModel.class);
        roomBinding.resAsset.setLayoutManager(new LinearLayoutManager(this));
        roomAdapter=new RoomAdapter(this);
        roomBinding.resAsset.setAdapter(roomAdapter);
        viewModel.catsList.observe(this, new Observer<List<DataModel>>() {
            @Override
            public void onChanged(List<DataModel> dataModels) {
                categoryList.clear();
                categoryList.addAll(dataModels);

                getSpinersData();
                            }
        });
        viewModel.assetList.observe(this, new Observer<List<AssetModel>>() {
            @Override
            public void onChanged(List<AssetModel> assetModels) {
                roomAdapter.list.clear();
                roomAdapter.list.addAll(assetModels);
                roomAdapter.notifyDataSetChanged();
            }
        });


    }

    public void getSpinersData(){
        List<String >catsList=new ArrayList<>();
        catsList.add("الكل");
        for(int i=0;i<categoryList.size();i++){
            catsList.add(categoryList.get(i).name);
        }
       catsAdapter= new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, catsList);
        catsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roomBinding.spinnerCats.setAdapter(catsAdapter);
        roomBinding.spinnerCats.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            if(i!=0)
                viewModel.filterAsset(categoryList.get(i-1).id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onClick() {

    }

    @Override
    public void onTextChange() {
        String code =roomBinding.barcode.getText().toString();
        if(code.length()==6) {
            viewModel.getAssetsFromRoom(code);
            viewModel.getDepartment(code).observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    roomBinding.dep.setText(s);
                }
            });
        }
    }
}
