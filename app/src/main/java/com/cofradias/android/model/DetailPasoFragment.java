package com.cofradias.android.model;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cofradias.android.R;
import com.cofradias.android.model.adapter.PasoAdapter;
import com.cofradias.android.model.help.Constants;
import com.cofradias.android.model.object.Cofradia;
import com.cofradias.android.model.object.Paso;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Picasso;

public class DetailPasoFragment extends Fragment implements PasoAdapter.PasoClickListener {

    private Cofradia cofradia;

    private RecyclerView mRecyclerView;
    private PasoAdapter mPasoAdapter;

    private ImageView mEscudoDetailPhoto;
    private TextView mNameCofradia;

    View contentView;

    private InterfaceDetailPaso listener;

    public void setCofradia(Cofradia cofradia) {
        this.cofradia = cofradia;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        contentView = inflater.inflate(R.layout.content_paso_detail,container, false);

        configViews();

        String detailImg = cofradia.getImagenEscudo();
        int idDrawable = getResources().getIdentifier(detailImg, "drawable", getContext().getPackageName());

        mEscudoDetailPhoto = (ImageView) contentView.findViewById(R.id.escudoDetailPhotoPaso);
        Picasso.with(getContext()).load(idDrawable).into(mEscudoDetailPhoto);

        Firebase myFirebaseRef = new Firebase(Constants.ConfigFireBase.FIREBASE_URL + Constants.ConfigFireBase.FIREBASE_CHILD_PASOS);
        Query queryRef = myFirebaseRef.orderByChild("id_cofradia").equalTo(cofradia.getId_cofradia());
        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    mPasoAdapter.addPaso(dataSnapshot.getValue(Paso.class));
                    mPasoAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });

        mNameCofradia.setText(cofradia.getNombreCofradia());

        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
    }

    private void configViews() {

        mNameCofradia = (TextView) contentView.findViewById(R.id.cofradiaNamePaso);

        mRecyclerView = (RecyclerView) contentView.findViewById(R.id.recyclerViewPaso);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        mPasoAdapter = new PasoAdapter(this);

        mRecyclerView.setAdapter(mPasoAdapter);

    }

    @Override
    public void onClick(int position) {

        listener.onClick(position, mPasoAdapter);
    }

    public void setListener(InterfaceDetailPaso listener) {
        this.listener = listener;
    }

    public interface InterfaceDetailPaso {

        public void onClick(int position, PasoAdapter mPasoAdapter);
    }
}
