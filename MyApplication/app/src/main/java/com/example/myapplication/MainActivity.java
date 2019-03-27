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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);//버그 방지로 공통 메뉴가 아닌 건 안 보이게 시작.
        if (true) {//피트니스 일 때
            changeMenu(navigationView, 0);
        } else {//일반 유저일 때
            changeMenu(navigationView, 1);
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
        } else if (id == R.id.nav_logout) {
            changeMenu(null, -1);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void changeMenu(NavigationView nv, int i) {//-1:다 안보이게 0:피트니스 1:유저
        if (nv == null) {
            nv = (NavigationView) findViewById(R.id.nav_view);
        }
        if (i == -1) {
            nv.getMenu().findItem(R.id.nav_recommand).setVisible(false);
            nv.getMenu().findItem(R.id.nav_near).setVisible(false);
            nv.getMenu().findItem(R.id.nav_fitness).setVisible(false);

            nv.getMenu().findItem(R.id.nav_registered).setVisible(false);
            nv.getMenu().findItem(R.id.nav_analysis).setVisible(false);
        } else if (i == 0) {
            nv.getMenu().findItem(R.id.nav_recommand).setVisible(false);
            nv.getMenu().findItem(R.id.nav_near).setVisible(false);
            nv.getMenu().findItem(R.id.nav_fitness).setVisible(false);

            nv.getMenu().findItem(R.id.nav_registered).setVisible(true);
            nv.getMenu().findItem(R.id.nav_analysis).setVisible(true);
        } else {
            nv.getMenu().findItem(R.id.nav_recommand).setVisible(true);
            nv.getMenu().findItem(R.id.nav_near).setVisible(true);
            nv.getMenu().findItem(R.id.nav_fitness).setVisible(true);

            nv.getMenu().findItem(R.id.nav_registered).setVisible(false);
            nv.getMenu().findItem(R.id.nav_analysis).setVisible(false);
        }
    }
}
