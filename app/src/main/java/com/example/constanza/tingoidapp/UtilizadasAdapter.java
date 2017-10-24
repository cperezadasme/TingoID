package com.example.constanza.tingoidapp;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.constanza.tingoidapp.api.model.Tinket;

import java.util.ArrayList;

public class UtilizadasAdapter extends ArrayAdapter {
    public UtilizadasAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<?> objects) {
        super(context, resource, objects);
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView==null){
            convertView = inflater.inflate(
                    R.layout.item_entrada_utilizada,
                    parent,
                    false);
        }

        //Referencias UI
        TextView empresa = (TextView) convertView.findViewById(R.id.empresa_utilizada);
        TextView fecha_emision = (TextView) convertView.findViewById(R.id.emison_utilizada);
        TextView fecha_expiracion = (TextView) convertView.findViewById(R.id.fecha_utilizacion);

        //tinket actual
        Tinket item = (Tinket) getItem(position);

        //Setup
        if (item!=null){
            empresa.setText(item.getEmpresa());
            fecha_emision.setText(item.getFecha_emision());
            fecha_expiracion.setText(item.getFecha_utilizacion());
        }

        return convertView;
    }

}