package com.cofradias.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.cofradias.android.model.object.Cofradia;
import com.cofradias.android.model.DetailCofradiaFragment;
import com.cofradias.android.model.object.Procesion;
import com.cofradias.android.model.adapter.ProcesionAdapter;
import com.cofradias.android.model.adapter.ViewPagerDetailAdpter;
import com.cofradias.android.model.help.Constants;

/**
 * Created by alaria on 21/04/2016.
 */
public class DetailActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DetailCofradiaFragment.InterfaceDetailCofradia {

    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();

        Cofradia cofradia = (Cofradia) intent.getSerializableExtra(Constants.REFERENCE.COFRADIA);
        Cofradia[] cofradiaList = new Cofradia[1];
        cofradiaList[0]=cofradia;

        toolbar = (Toolbar) findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_detail);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_detail);
        navigationView.setNavigationItemSelectedListener(this);

        viewPager = (ViewPager) findViewById(R.id.viewpager_detail);

        final ViewPagerDetailAdpter viewPagerDetailAdpter = new ViewPagerDetailAdpter(this,getSupportFragmentManager(),this.getApplicationContext(), cofradiaList);
        viewPager.setAdapter(viewPagerDetailAdpter);

        tabLayout = (TabLayout) findViewById(R.id.tablayout_detail);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_detail);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_cofradias) {
            Intent intentMenu = new Intent(DetailActivity.this, MainActivity.class);
            intentMenu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentMenu);
        } else if (id == R.id.nav_calendario) {
            Intent intentMenu = new Intent(DetailActivity.this, DiaEventoActivity.class);
            intentMenu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentMenu);
        } else if (id == R.id.nav_gallery_detail) {

        } else if (id == R.id.nav_slideshow_detail) {

        } else if (id == R.id.nav_manage_detail) {

        } else if (id == R.id.nav_share_detail) {

        } else if (id == R.id.nav_send_detail) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_detail);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(int position, ProcesionAdapter mProcesionAdapter) {

        //Toast.makeText(getApplicationContext(), "Selected Date:", Toast.LENGTH_LONG).show();

        Procesion selectedProcesion = mProcesionAdapter.getSelectedProcesion(position);

        Intent intent = new Intent(DetailActivity.this, MapsActivity.class);
        intent.putExtra(Constants.REFERENCE.PROCESION, selectedProcesion);
        startActivity(intent);
    }
}