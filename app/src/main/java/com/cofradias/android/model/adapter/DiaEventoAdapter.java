package com.cofradias.android.model.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cofradias.android.R;
import com.cofradias.android.model.object.DiaEvento;
import com.cofradias.android.model.object.Evento;
import com.cofradias.android.model.object.EventoCabecera;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alaria on 25/04/2016.
 */
public class DiaEventoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final String TAG = DiaEventoAdapter.class.getSimpleName();
    private final DiaEventoClickListener mListener;
    private List<DiaEvento> mDiaEventos;
    private List<Object> mNewObject;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;


    public DiaEventoAdapter(DiaEventoClickListener listener) {

        mDiaEventos = new ArrayList<>();
        mNewObject = new ArrayList<>();
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == TYPE_HEADER)
        {
            View rowCabecera = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_evento_cabecera, null, false);
            return new HolderCabecera(rowCabecera);
        }
        else if(viewType == TYPE_ITEM)
        {
            View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_evento, parent, false);
            return new HolderItem(rowItem);
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Object currObject = mNewObject.get(position);

        if(holder instanceof HolderCabecera) {

            EventoCabecera currEventoCabecera = (EventoCabecera) mNewObject.get(position);
            HolderCabecera VHheader = (HolderCabecera)holder;
            String titulo = currEventoCabecera.getFechaCabecera() + " - " + currEventoCabecera.getDiaCabecera();
            VHheader.mTitle.setText(titulo);
        }else if(holder instanceof HolderItem){

            Evento currEvento = (Evento) mNewObject.get(position);
            HolderItem VHitem = (HolderItem)holder;

            String escudoPhoto = currEvento.getImagenOrganizador();
            int idDrawable = VHitem.itemView.getContext().getResources().getIdentifier(escudoPhoto, "drawable", VHitem.itemView.getContext().getPackageName());
            Picasso.with(VHitem.itemView.getContext()).load(idDrawable).into(VHitem.mImagenOrganizadorEvento);

            VHitem.mOrganizadorEvento.setText(currEvento.getOrganizador());

            String imgProcesion = currEvento.getImagenVista();
            int idDrawableVista = VHitem.itemView.getContext().getResources().getIdentifier(imgProcesion, "drawable", VHitem.itemView.getContext().getPackageName());
            Picasso.with(VHitem.itemView.getContext()).load(idDrawableVista).into(VHitem.mImagenVista);

            VHitem.mDescripcionEvento.setText(currEvento.getTextoEvento());
        }

        //Picasso.with(holder.itemView.getContext()).load("https://raw.githubusercontent.com/allaria/CofradiasBilbao/master/app/src/main/res/imagenes/verMapa.png").into(holder.mPhoto);
        //Picasso.with(holder.itemView.getContext()).load(R.drawable.e_santa_vera_cruz).into(holder.mPhoto);
    }

    @Override
    public int getItemCount() {
        return mNewObject.size();
    }

    public void addDiaEvento(DiaEvento diaEvento) {

        mNewObject.add(new EventoCabecera(diaEvento.getDia(), diaEvento.getFecha()));

        for (int i=0; i<diaEvento.getEvento().size();i++){
            mNewObject.add(diaEvento.getEvento().get(i));
        }

        notifyDataSetChanged();
    }

    public Evento getSelectedEvento(int position) {
        return (Evento) mNewObject.get(position);
    }

    @Override
    public int getItemViewType(int position) {

        if (mNewObject.get(position) instanceof EventoCabecera){
            return TYPE_HEADER;
        } else {
            return TYPE_ITEM;
        }
    }

    public class HolderCabecera extends RecyclerView.ViewHolder{

        TextView mTitle;
        public HolderCabecera(View cabeceraView) {
            super(cabeceraView);
            mTitle = (TextView)cabeceraView.findViewById(R.id.cabeceraEvento);
        }
    }

    public class HolderItem extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView mDescripcionEvento, mOrganizadorEvento;
        ImageView mImagenOrganizadorEvento, mImagenVista;

        public HolderItem(View itemView) {
            super(itemView);
            mDescripcionEvento = (TextView)itemView.findViewById(R.id.descripcionEvento);
            mImagenOrganizadorEvento = (ImageView)itemView.findViewById(R.id.imagenOrganizadorEvento);
            mImagenVista = (ImageView)itemView.findViewById(R.id.imagenVista);
            mOrganizadorEvento = (TextView) itemView.findViewById(R.id.organizadorEvento);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onClick(getLayoutPosition());
        }
    }

    public interface DiaEventoClickListener {
        void onClick(int position);
    }
}
