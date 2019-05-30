package com.softwarica.a4thassignment.Activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.softwarica.a4thassignment.Adapter.TabViewadapter;
import com.softwarica.a4thassignment.R;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewpager;
    private TabLayout tab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        settingadapter();
        ActionBar action=getSupportActionBar();
        action.hide();
    }
//initiating tabview and viewpager
    public void init(){
        tab=findViewById(R.id.tabid);
        viewpager=findViewById(R.id.view_pager);
    }
//this will create two fragments i.e. Login and Register in TabView with the help of adapter
    public void settingadapter(){
        TabViewadapter adapter=new TabViewadapter(getSupportFragmentManager());
        adapter.addFragment(new Login(),"Login");
        adapter.addFragment(new Register(),"Register");
        viewpager.setAdapter(adapter);
        tab.setupWithViewPager(viewpager);
    }
}
