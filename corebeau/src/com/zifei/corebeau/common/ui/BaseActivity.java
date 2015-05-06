//package com.zifei.corebeau.common.ui;
//
//
//import android.os.Bundle;
//import android.support.v7.app.ActionBarActivity;
//import android.support.v7.widget.Toolbar;
//import android.content.res.Configuration;
//import android.support.v4.widget.DrawerLayout;
//import android.support.v7.app.ActionBarActivity;
//import android.os.Bundle;
//import android.support.v7.app.ActionBarDrawerToggle;
//import android.support.v7.widget.Toolbar;
//import android.view.Menu;
//import android.view.MenuItem;
//
//import com.zifei.corebeau.R;
//
//public abstract class BaseActivity extends ActionBarActivity {
//
//    private Toolbar toolbar;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(getLayoutResource());
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        if (toolbar != null) {
////            setSupportActionBar(toolbar);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }
//    }
//
//    protected abstract int getLayoutResource();
//
//    protected void setActionBarIcon(int iconRes) {
//        toolbar.setNavigationIcon(iconRes);
//    }
//}