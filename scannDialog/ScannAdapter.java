package com.g2m.asset.scannDialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.g2m.asset.R;
import com.g2m.asset.assetInfo.AssetInfoActivity;
import com.g2m.asset.databinding.ItemScannBinding;
import com.g2m.asset.homePage.HomeModel;
import com.g2m.asset.interfaces.ClickListener;
import com.g2m.asset.models.dataModels.AssetModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ScannAdapter extends RecyclerView.Adapter<ScannAdapter.ViewHolder> {
    List<AssetModel> list;
    List<ScannModels>scannModelsList;
    Context context;
    public ScannAdapter(Context cxt) {
        list=new ArrayList<>();
        scannModelsList=new ArrayList<>();
        context=cxt;
    }

    @NonNull
    @Override
    public ScannAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemScannBinding itemPageBinding=
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_scann,
                        viewGroup,false);
        ViewHolder viewHolder=new ViewHolder(itemPageBinding);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ScannAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.itemPageBinding.setScannModels(scannModelsList.get(i));
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return scannModelsList.size();
    }

    public void addItems(List<ScannModels> assetModels) {
    scannModelsList.clear();
    scannModelsList.addAll(assetModels);
    notifyDataSetChanged();


    }
public int check(String code){
        for (int i=0;i<scannModelsList.size();i++){
            if(scannModelsList.get(i).barcode.equals(code)){
                scannModelsList.get(i).status=1;
              //  notifyDataSetChanged();
                notifyItemChanged(i);
                return scannModelsList.get(i).asset_id;
            }

        }

        return -1;
}

    public class ViewHolder extends RecyclerView.ViewHolder  {
        ItemScannBinding itemPageBinding;
        public ViewHolder(ItemScannBinding itemview) {
            super(itemview.getRoot());
            itemPageBinding=itemview;
        }

    }
}