package com.cofradias.android.model;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cofradias.android.R;
import com.cofradias.android.model.adapter.ProcesionAdapter;
import com.cofradias.android.model.help.Constants;
import com.cofradias.android.model.object.Cofradia;
import com.cofradias.android.model.object.Procesion;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Picasso;

public class DetailCofradiaFragment extends Fragment implements ProcesionAdapter.ProcesionClickListener {

    private Cofradia cofradia;

    private RecyclerView mRecyclerView;
    private ProcesionAdapter mProcesionAdapter;

    private ImageView mEscudoDetailPhoto, mDetailPhoto;
    private TextView mNombreCofradia, mFundacion, mSede, mPasos, mTexto, mHernamoAbad, mTunica, mNumeroProcesiones, mProcesion;

    View contentView;

    private InterfaceDetailCofradia listener;

    public void setCofradia(Cofradia cofradia) {
        this.cofradia = cofradia;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        contentView = inflater.inflate(R.layout.content_cofradia_detail,container, false);

        configViews();

//        String detailImg = cofradia.getImgagenDetalle();
//        //int idDrawable = getResources().getIdentifier(detailImg, "drawable", getApplicationContext().getPackageName());
//        int idDrawable = getResources().getIdentifier(detailImg, "drawable", getContext().getPackageName());

//        mDetailPhoto = (ImageView) contentView.findViewById(R.id.detailPhoto);
//        //Picasso.with(getApplicationContext()).load(idDrawable).into(mDetailPhoto);
//        Picasso.with(getContext()).load(idDrawable).into(mDetailPhoto);

        String detailImg = cofradia.getImagenEscudo();
        int idDrawable = getResources().getIdentifier(detailImg, "drawable", getContext().getPackageName());

        mEscudoDetailPhoto = (ImageView) contentView.findViewById(R.id.escudoDetailPhoto);
        Picasso.with(getContext()).load(idDrawable).into(mEscudoDetailPhoto);

        Firebase myFirebaseRef = new Firebase(Constants.ConfigFireBase.FIREBASE_URL + Constants.ConfigFireBase.FIREBASE_CHILD_PROCESIONES);
        Query queryRef = myFirebaseRef.orderByChild("id_cofradia").equalTo(cofradia.getId_cofradia());
        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    mProcesionAdapter.addProcesion(dataSnapshot.getValue(Procesion.class));
                    mProcesionAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });

        mNombreCofradia.setText(cofradia.getNombreCofradia());
        mFundacion.setText(String.valueOf(cofradia.getFundacion()));
        mSede.setText(cofradia.getSede());
        //mPasos.setText(String.valueOf(cofradia.getNumeroPasos()));
        //mTexto.setText(cofradia.getTextoDetalle());
        mHernamoAbad.setText(cofradia.getAbad());
        mTunica.setText(cofradia.getVestidura());
        mNumeroProcesiones.setText(String.valueOf(cofradia.getNumeroProcesiones()));

        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
    }

    private void configViews() {

        mNombreCofradia = (TextView) contentView.findViewById(R.id.cofradiaName);
        mFundacion = (TextView) contentView.findViewById(R.id.cofradiaFundacion);
        mSede = (TextView) contentView.findViewById(R.id.cofradiaSede);
        //mPasos = (TextView) contentView.findViewById(R.id.cofradiaPasos);
        //mTexto = (TextView) contentView.findViewById(R.id.cofradiaTexto);
        mHernamoAbad = (TextView) contentView.findViewById(R.id.cofradiaAbad);
        mTunica = (TextView) contentView.findViewById(R.id.cofradiaVestidura);
        mNumeroProcesiones = (TextView) contentView.findViewById(R.id.cofradiaProcesiones);

        mRecyclerView = (RecyclerView) contentView.findViewById(R.id.recyclerViewDetail);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        mProcesionAdapter = new ProcesionAdapter(this);

        mRecyclerView.setAdapter(mProcesionAdapter);
    }

    @Override
    public void onClick(int position) {

        listener.onClick(position, mProcesionAdapter);
    }

    public void setListener(InterfaceDetailCofradia listener) {
        this.listener = listener;
    }

    public interface InterfaceDetailCofradia {

        public void onClick(int position, ProcesionAdapter mProcesionAdapter);
    }


}
