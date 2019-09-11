package com.g2m.asset.oldTransfeer;

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
import com.g2m.asset.databinding.ActivityOldTransfeerBinding;
import com.g2m.asset.transfeer.TransfeerActivity;
import com.g2m.asset.transfeer.TransferViewModel;
import com.g2m.asset.util.Constants;
import com.g2m.asset.util.Helper;
import com.g2m.asset.util.ViewDialog;

import java.util.List;

public class OldTransfeerActivity extends AppCompatActivity {
ActivityOldTransfeerBinding oldTransfeerBinding;
TransfeerAdapter adapter;
OldTransfeerViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        oldTransfeerBinding= DataBindingUtil.setContentView(this,R.layout.activity_old_transfeer);
        Constants.context=this;
        oldTransfeerBinding.resTransfeer.setLayoutManager(new LinearLayoutManager(this));
        viewModel= ViewModelProviders.of(this).get(OldTransfeerViewModel.class);
        adapter=new TransfeerAdapter(this,viewModel);
        oldTransfeerBinding.resTransfeer.setAdapter(adapter);

        viewModel.getAllTransfeer().observe(this, new Observer<List<OldTransfeerModel>>() {
            @Override
            public void onChanged(List<OldTransfeerModel> oldTransfeerModels) {
                adapter.addItems(oldTransfeerModels);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.transfeer_menu, menu);

        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

            if(item.getItemId()==R.id.add){
                startActivity(new Intent(OldTransfeerActivity.this, TransfeerActivity.class));
            }



        return true;
    }
}
