package com.cofradias.android.model;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cofradias.android.R;
import com.cofradias.android.model.adapter.GaleriaAdapter;
import com.cofradias.android.model.help.Constants;
import com.cofradias.android.model.object.Cofradia;
import com.cofradias.android.model.object.ImagenGaleria;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Picasso;

public class DetailGaleriaFragment extends Fragment implements GaleriaAdapter.GaleriaClickListener {

    private Cofradia cofradia;

    private RecyclerView mRecyclerView;
    private GaleriaAdapter mImagenGaleriaAdapter;

    private ImageView mEscudoDetailPhoto;
    private TextView mNombreCofradia;

    View contentView;

    public void setCofradia(Cofradia cofradia) {
        this.cofradia = cofradia;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        contentView = inflater.inflate(R.layout.content_galeria_detail,container, false);

        configViews();

        String detailImg = cofradia.getImagenEscudo();
        int idDrawable = getResources().getIdentifier(detailImg, "drawable", getContext().getPackageName());

        mEscudoDetailPhoto = (ImageView) contentView.findViewById(R.id.escudoDetailPhotoGaleria);
        Picasso.with(getContext()).load(idDrawable).into(mEscudoDetailPhoto);

        Firebase myFirebaseRef = new Firebase(Constants.ConfigFireBase.FIREBASE_URL + Constants.ConfigFireBase.FIREBASE_CHILD_GALERIA);
        Query queryRef = myFirebaseRef.orderByChild("id_cofradia").equalTo(cofradia.getId_cofradia());
        myFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    mImagenGaleriaAdapter.addImagenGaleria(dataSnapshot.getValue(ImagenGaleria.class));
                    mImagenGaleriaAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });

        mNombreCofradia.setText(cofradia.getNombreCofradia());

        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
    }

    private void configViews() {

        mNombreCofradia = (TextView) contentView.findViewById(R.id.cofradiaNameGaleria);

        mRecyclerView = (RecyclerView) contentView.findViewById(R.id.recyclerViewGaleria);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        mImagenGaleriaAdapter = new GaleriaAdapter(this);

        mRecyclerView.setAdapter(mImagenGaleriaAdapter);
    }

    @Override
    public void onClick(int position) {

        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.content_galeria_imagen);
        dialog.setTitle("Galería de Fotos");

        TextView text = (TextView) dialog.findViewById(R.id.text);
        text.setText(mImagenGaleriaAdapter.getSelectedImagenGaleria(position).getCaption());
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        Picasso.with(getContext()).load(mImagenGaleriaAdapter.getSelectedImagenGaleria(position).getImage()).into(image);

        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        dialogButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
