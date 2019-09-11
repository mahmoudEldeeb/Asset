package com.g2m.asset.inventory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.g2m.asset.R;
import com.g2m.asset.databinding.ActivityInventoryBinding;
import com.g2m.asset.homePage.HomeActivity;
import com.g2m.asset.models.Prefrences;
import com.g2m.asset.models.dataModels.InventoryModel;
import com.g2m.asset.models.dataModels.InventoryTabel;
import com.g2m.asset.scannDialog.DialogInteract;
import com.g2m.asset.util.Constants;
import com.g2m.asset.util.Helper;
import com.g2m.asset.util.ViewDialog;

import java.util.List;

public class InventoryActivity extends AppCompatActivity implements DialogInteract {
ActivityInventoryBinding inventoryBinding;
InventoryAdapter inventoryAdapter;
InventoryViewModel viewModel;
    MenuItem sendItem ;
    Menu menu;
    boolean sendItemVisibility=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inventoryBinding= DataBindingUtil.setContentView(this, R.layout.activity_inventory);
        getSupportActionBar().setTitle("Inventory");
        inventoryBinding.resInventory.setLayoutManager(new LinearLayoutManager(this));
        Constants.context=this;

        viewModel= ViewModelProviders.of(this).get(InventoryViewModel.class);
        inventoryAdapter=new InventoryAdapter(this,viewModel);
        inventoryBinding.resInventory.setAdapter(inventoryAdapter);

        if(Prefrences.iSFirstInventory()) {
            if(Helper.isNetworkAvailable())
            viewModel.getOperationsOnline();
            else ViewDialog.showDialog(getResources().getString(R.string.no_internet),false);
        }

        viewModel.getOperations().observe(this, new Observer<List<InventoryTabel>>() {
            @Override
            public void onChanged(List<InventoryTabel> inventoryModels) {
                inventoryAdapter.addItems(inventoryModels);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        Constants.context=this;

    }

    @Override
    public void onclick() {
        startActivity(new Intent(InventoryActivity.this, HomeActivity.class));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu=menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        sendItem=menu.findItem(R.id.send);
        sendItem.setVisible(false);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(!Helper.isNetworkAvailable()){
            ViewDialog.showDialog(getResources().getString(R.string.no_internet),false);
        }
        else {
            if(item.getItemId()==R.id.refresh)
            {
             viewModel.getOperationsOnline();
            }
            else {
            }
        }

        return true;
    }
}
