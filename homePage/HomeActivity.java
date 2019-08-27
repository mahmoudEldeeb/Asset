package com.g2m.asset.homePage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.g2m.asset.R;
import com.g2m.asset.databinding.ActivityHomeBinding;
import com.g2m.asset.models.Prefrences;
import com.g2m.asset.models.dataModels.AllDataField;
import com.g2m.asset.models.dataModels.AssetModel;
import com.g2m.asset.util.Constants;
import com.g2m.asset.util.Helper;
import com.g2m.asset.util.ViewDialog;

import java.util.List;

public class HomeActivity extends AppCompatActivity {
ActivityHomeBinding homeBinding;
HomeAdapter homeAdapter;
    HomeViewModel viewModel;
    MenuItem sendItem ;
    Menu menu;
    boolean sendItemVisibility=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        homeBinding= DataBindingUtil.setContentView(this,R.layout.activity_home);
       // Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


        Constants.context=this;
        homeBinding.resHome.setLayoutManager(new GridLayoutManager(this,2));
        homeAdapter=new HomeAdapter(this);
        homeBinding.resHome.setAdapter(homeAdapter);
        viewModel= ViewModelProviders.of(this).get(HomeViewModel.class);

if(Prefrences.iSfirstTime()){
    if(!Helper.isNetworkAvailable())
        ViewDialog.showDialog(getResources().getString(R.string.no_internet),false);
    else viewModel.getAllData();
}




    viewModel.getTransferCount()
        .observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (sendItem != null) {
                    if (integer > 0) {
                        homeBinding.setUpdateButtonVisibl(true);
                        sendItem.setVisible(true);
                    } else sendItem.setVisible(false);

                }else {
                    if(integer>0)sendItemVisibility=true;
                    else sendItemVisibility=false;
                }
            }

        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        Constants.context=this;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu=menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        sendItem=menu.findItem(R.id.send);
        sendItem.setVisible(sendItemVisibility);
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

            if(!Helper.isNetworkAvailable()){
                ViewDialog.showDialog(getResources().getString(R.string.no_internet),false);
            }
            else {
                if(item.getItemId()==R.id.refresh)
                viewModel.getAllData();
                else viewModel.sendData();
            }

        return true;
    }


}
