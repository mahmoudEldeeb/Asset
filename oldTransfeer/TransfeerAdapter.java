package com.g2m.asset.oldTransfeer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.g2m.asset.R;
import com.g2m.asset.databinding.ItemInventoryBinding;
import com.g2m.asset.databinding.ItemTransferBinding;
import com.g2m.asset.interfaces.ClickListener;
import com.g2m.asset.inventory.InventoryViewModel;
import com.g2m.asset.models.dataModels.InventoryTabel;
import com.g2m.asset.models.dataModels.TransfeerModel;
import com.g2m.asset.scannDialog.ScannRoomDialog;
import com.g2m.asset.transfeer.TransfeerActivity;
import com.g2m.asset.util.Helper;
import com.g2m.asset.util.ViewDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TransfeerAdapter extends RecyclerView.Adapter<TransfeerAdapter.ViewHolder> {
    List<OldTransfeerModel> list;
    Context context;
    OldTransfeerViewModel viewModel;
    public TransfeerAdapter(Context cxt, OldTransfeerViewModel viewModel) {
        list=new ArrayList<>();
        this.viewModel=viewModel;
        context=cxt;
    }

    @NonNull
    @Override
    public TransfeerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemTransferBinding itemTransferBinding=
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_transfer,
                        viewGroup,false);
        ViewHolder viewHolder=new ViewHolder(itemTransferBinding);
        itemTransferBinding.setClickListener(viewHolder);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull TransfeerAdapter.ViewHolder viewHolder, final int i) {
      //  viewHolder.itemPageBinding.setHomeModel(list.get(i));
        viewHolder.itemTransferBinding.setInventoryModel(list.get(i));
        viewHolder.itemTransferBinding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("tttt","dddddd");
                if(!Helper.isNetworkAvailable())
                    ViewDialog.showDialog(context.getResources().getString(R.string.no_internet),false);
                else {List<TransfeerModel>list2=new ArrayList<>();
                list2.add(list.get(i).transfeerModel);
                viewModel.sendTransfer(list2);
                }

            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItems(List<OldTransfeerModel> transfeerModels) {
        list.clear();
        list.addAll(transfeerModels);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements ClickListener {
        ItemTransferBinding itemTransferBinding;
        public ViewHolder(ItemTransferBinding itemview) {
            super(itemview.getRoot());
            itemTransferBinding=itemview;

        }

        @Override
        public void onClick() {
            Intent intent=new Intent(context, TransfeerActivity.class);
            intent.putExtra("transfeerObject", (Serializable) list.get(getAdapterPosition()).transfeerModel);
            context.startActivity(intent);

        }

        @Override
        public void onTextChange() {

        }
    }
}