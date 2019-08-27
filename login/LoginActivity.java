package com.g2m.asset.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.g2m.asset.R;
import com.g2m.asset.assetInfo.AssetInfoActivity;
import com.g2m.asset.databinding.ActivityLoginBinding;
import com.g2m.asset.homePage.HomeActivity;
import com.g2m.asset.interfaces.ClickListener;

public class LoginActivity extends AppCompatActivity implements ClickListener {
ActivityLoginBinding loginBinding;
User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding= DataBindingUtil.setContentView(this,R.layout.activity_login);
        user=new User();
        loginBinding.setUser(user);
        loginBinding.setClickListener(this);
    }

    @Override
    public void onClick() {

//        Log.v("sssss",user.username);
       startActivity(new Intent(LoginActivity.this, HomeActivity.class));
//        if(user.remmber){
//            Log.v("sssss","hhhhhhhhhhhhh");
//        }else Log.v("sssss","qqqqqqqqq");
   }

    @Override
    public void onTextChange() {

    }
}
