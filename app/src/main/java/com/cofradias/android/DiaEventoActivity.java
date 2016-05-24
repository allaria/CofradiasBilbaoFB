package com.cofradias.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.cofradias.android.model.adapter.DiaEventoAdapter;
import com.cofradias.android.model.help.Constants;
import com.cofradias.android.model.object.Cofradia;
import com.cofradias.android.model.object.DiaEvento;
import com.cofradias.android.model.object.Evento;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

/**
 * Created by alaria on 17/05/2016.
 */
public class DiaEventoActivity extends AppCompatActivity implements DiaEventoAdapter.DiaEventoClickListener,
        NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = DiaEventoActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private DiaEventoAdapter mDiaEventoAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento);

        configViews();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_evento);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_evento);
        navigationView.setNavigationItemSelectedListener(this);

        Firebase myFirebaseRef = new Firebase(Constants.ConfigFireBase.FIREBASE_URL + Constants.ConfigFireBase.FIREBASE_CHILD_EVENTOS);
        myFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    mDiaEventoAdapter.addDiaEvento(dataSnapshot.getValue(DiaEvento.class));
                    mDiaEventoAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_cofradias) {
            Intent intentMenu = new Intent(DiaEventoActivity.this, MainActivity.class);
            intentMenu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentMenu);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void configViews() {

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_evento);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        mDiaEventoAdapter = new DiaEventoAdapter(this);

        mRecyclerView.setAdapter(mDiaEventoAdapter);
    }

    @Override
    public void onClick(int position) {
        Evento selectedEvento = mDiaEventoAdapter.getSelectedEvento(position);
        Firebase myFirebaseRef = new Firebase(Constants.ConfigFireBase.FIREBASE_URL + Constants.ConfigFireBase.FIREBASE_CHILD_COFRADIAS);
        Query queryRef = myFirebaseRef.orderByChild("id_cofradia").equalTo(selectedEvento.getId_cofradia());
        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Cofradia selectedCofradia = dataSnapshot.getValue(Cofradia.class);

                    Intent intent = new Intent(DiaEventoActivity.this, DetailActivity.class);
                    intent.putExtra(Constants.REFERENCE.COFRADIA, selectedCofradia);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });
    }
}
