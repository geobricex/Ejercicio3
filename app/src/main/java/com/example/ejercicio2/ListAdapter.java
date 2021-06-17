package com.example.ejercicio2;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ejercicio2.models.ListElement;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<ListElement> Data;
    private LayoutInflater inflater;
    private Context context;


    public ListAdapter(List<ListElement> itemList, Context context) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.Data = itemList;
    }

    @Override
    public int getItemCount() {
        return Data.size();
    }

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_element, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position) {
        holder.bindData(Data.get(position));
    }

    public void setItem(List<ListElement> item) {
        Data = item;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView doi, fecha, anio, titulo, volumen;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.idImg);
            doi = itemView.findViewById(R.id.idDOI);
            fecha = itemView.findViewById(R.id.idFecha);
            anio = itemView.findViewById(R.id.idAnio);
            titulo = itemView.findViewById(R.id.idTitulo);
            volumen = itemView.findViewById(R.id.idVolumen);
        }

        void bindData(final ListElement item) {
//            imageView.setColorFilter(Color.parseColor(item.getColor()), PorterDuff.Mode.SRC_IN);// To put color
            Picasso.get().load(item.getImg()).placeholder(R.drawable.outline_dns_black_48).error(R.drawable.outline_dns_black_48).into(imageView);
//            Glide.with(context).load(item.getImg()).into(imageView);
            doi.setText(item.getDoi());
            fecha.setText(item.getFecha());
            anio.setText(item.getAnio());
            titulo.setText(item.getTitulo());
            volumen.setText(item.getVolumen());
        }
    }

}
