package com.g2m.asset.homePage;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

public class HomeModel  {
    public String title;
    public int image;

    public HomeModel(String title, int image) {
        this.title = title;
        this.image = image;
    }

    @BindingAdapter("imageHomeUrl")
    public static void setimageHomeUrl(ImageView view, int url){
        view.setImageResource(url);
    }
}
