package com.example.myapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);//버그 방지를 위해 기본적으로 공통 메뉴가 아닌 것은 숨김 상태로 시작.
        if (true) {//피트니스 일 때
            findViewById(R.id.nav_recommand).setVisibility(View.INVISIBLE);
            findViewById(R.id.nav_near).setVisibility(View.INVISIBLE);
            findViewById(R.id.nav_fitness).setVisibility(View.INVISIBLE);

            findViewById(R.id.nav_registered).setVisibility(View.VISIBLE);
            findViewById(R.id.nav_analysis).setVisibility(View.VISIBLE);
        } else {//일반 유저일 때
            findViewById(R.id.nav_recommand).setVisibility(View.VISIBLE);
            findViewById(R.id.nav_near).setVisibility(View.VISIBLE);
            findViewById(R.id.nav_fitness).setVisibility(View.VISIBLE);

            findViewById(R.id.nav_registered).setVisibility(View.INVISIBLE);
            findViewById(R.id.nav_analysis).setVisibility(View.INVISIBLE);
        }
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
        } else if (id == R.id.nav_recommand) {
        } else if (id == R.id.nav_fitness) {
        } else if (id == R.id.nav_near) {
        } else if (id == R.id.nav_registered) {
        } else if (id == R.id.nav_analysis) {
        } else if (id == R.id.nav_setting) {
        } else if (id == R.id.nav_logout) {//버그 방지를 위해 기본적으로 공통 메뉴가 아닌 것은 숨김 상태로.
            findViewById(R.id.nav_recommand).setVisibility(View.INVISIBLE);
            findViewById(R.id.nav_near).setVisibility(View.INVISIBLE);
            findViewById(R.id.nav_fitness).setVisibility(View.INVISIBLE);
            findViewById(R.id.nav_registered).setVisibility(View.INVISIBLE);
            findViewById(R.id.nav_analysis).setVisibility(View.INVISIBLE);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
