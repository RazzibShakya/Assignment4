package com.softwarica.a4thassignment.Activity;

import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.softwarica.a4thassignment.Model.Url;
import com.softwarica.a4thassignment.R;

import java.io.InputStream;
import java.net.URL;

public class Description extends AppCompatActivity {
    ActionBar action;
    TextView name,price,des,img;
    ImageView img1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        action=getSupportActionBar();
        action.setDisplayHomeAsUpEnabled(true);
        action.setTitle("Description");
        initiate();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
//initiating buttons and textfield
    public void initiate(){
        name=findViewById(R.id.txtitemName);
        price=findViewById(R.id.txtitemprice);
        des=findViewById(R.id.txtitemdesc);
        img1=findViewById(R.id.imgdisplay);
        Bundle bundle=getIntent().getExtras();
        //setting images and data from Recycler View to Description Activity
        if(bundle!=null){
            String imgpath = Url.BASE_URL + "images/" + bundle.getString("Item_img");
            try {
                URL ur = new URL(imgpath);
                img1.setImageBitmap(BitmapFactory.decodeStream((InputStream) ur.getContent()));

            }catch (Exception e){
                Log.d("desimg", "error"+e);
            }
                name.setText("Name:" + bundle.getString("Item_name"));
                price.setText("Price:" + bundle.getString("Item_price"));
                des.setText("Description:" + bundle.getString("Item_des"));

        }
    }
}
