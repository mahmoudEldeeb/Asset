package com.g2m.asset.homePage;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.g2m.asset.R;
import com.g2m.asset.assetInfo.AssetInfoActivity;
import com.g2m.asset.databinding.ItemPageBinding;
import com.g2m.asset.interfaces.ClickListener;
import com.g2m.asset.inventory.InventoryActivity;
import com.g2m.asset.login.User;
import com.g2m.asset.oldTransfeer.OldTransfeerActivity;
import com.g2m.asset.roomInfo.RoomActivity;
import com.g2m.asset.transfeer.TransfeerActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter  extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    List<HomeModel> list;
    Context context;
    public HomeAdapter(Context cxt) {
        list=new ArrayList<>();
        context=cxt;
        HomeModel model=new HomeModel("Asset Info",R.drawable.assetinfo);
        list.add(model);

        HomeModel model1=new HomeModel("Room Info",R.drawable.roominfo);
        list.add(model1);
        HomeModel model2=new HomeModel("Transfer ",R.drawable.asset_transfer);
        list.add(model2);
        HomeModel model3=new HomeModel("Inventory",R.drawable.roominfo);
        list.add(model3);

    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemPageBinding itemPageBinding=
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_page,
                        viewGroup,false);
        ViewHolder viewHolder=new ViewHolder(itemPageBinding);
        itemPageBinding.setClickListener(viewHolder);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.itemPageBinding.setHomeModel(list.get(i));
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements ClickListener {
        ItemPageBinding itemPageBinding;
        public ViewHolder(ItemPageBinding itemview) {
            super(itemview.getRoot());
            itemPageBinding=itemview;
        }
        @Override
        public void onClick() {
            if(getAdapterPosition()==0){
                context.startActivity(new Intent(context, AssetInfoActivity.class));
            }
            else if (getAdapterPosition()==1){
                context.startActivity(new Intent(context, RoomActivity.class));
            }
            else if (getAdapterPosition()==3){
                context.startActivity(new Intent(context, InventoryActivity.class));
            }
            else if (getAdapterPosition()==2){
                context.startActivity(new Intent(context, OldTransfeerActivity.class));
            }

        }

        @Override
        public void onTextChange() {

        }
    }
}