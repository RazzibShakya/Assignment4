package com.softwarica.a4thassignment.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.softwarica.a4thassignment.Adapter.ItemRecyclerAdapter;
import com.softwarica.a4thassignment.Model.API;
import com.softwarica.a4thassignment.Model.Item;
import com.softwarica.a4thassignment.Model.Url;
import com.softwarica.a4thassignment.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Dashboard extends AppCompatActivity implements View.OnClickListener {
    RecyclerView rv;
    Button btnAdd;
    List<Item> Clothelist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        rv = findViewById(R.id.recyler);
        getAllItem();
        getSupportActionBar().hide();
        rv.setLayoutManager(new GridLayoutManager(this, 3));
        rv.setAdapter(new ItemRecyclerAdapter(Clothelist, this));
        btnAdd = findViewById(R.id.Addbutton);
        btnAdd.setOnClickListener(this);

    }
//Setting List oF items in Recycler View
    private void getAllItem() {
        Url url = new Url();
        Call<List<Item>> listCall = url.createInstanceofRetrofit().getAllItem();
        listCall.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(Dashboard.this, "Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Item>itemList=response.body();
                ItemRecyclerAdapter itemadapter=new ItemRecyclerAdapter(itemList,Dashboard.this);
                rv.setAdapter(itemadapter);
                rv.setLayoutManager(new LinearLayoutManager(Dashboard.this));
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                Toast.makeText(Dashboard.this, "Code:" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Addbutton:
                Intent intent = new Intent(Dashboard.this, Additem.class);
                startActivity(intent);

                break;
        }
    }
}
