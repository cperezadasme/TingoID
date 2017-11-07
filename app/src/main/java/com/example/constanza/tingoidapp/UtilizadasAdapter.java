package com.example.constanza.tingoidapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
        TextView tipo = (TextView) convertView.findViewById(R.id.detalle_usada);
        TextView uso = (TextView) convertView.findViewById(R.id.ocupada);
        TextView fecha_uso = (TextView) convertView.findViewById(R.id.fecha_utilizacion);
        ImageView imagen = (ImageView) convertView.findViewById(R.id.imagen_usada);

        //tinket actual
        Tinket item = (Tinket) getItem(position);

        //Setup
        if (item!=null){
            /*String base = item.getImagen();
            byte[] decodedString = Base64.decode(base, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            imagen.setImageBitmap(decodedByte);*/

            imagen.setImageResource(R.drawable.empresa1);
            empresa.setText(item.getEmpresa());
            tipo.setText(item.getDetalle());
            LinearLayout bg = (LinearLayout)convertView.findViewById(R.id.bg_utilizada);

            if ((item.getValido()).equals("Expirado")) {
                uso.setText("Expiró el ");
                fecha_uso.setText(item.getFecha_expiracion());
                bg.setBackgroundResource(R.drawable.ticketdettailexpired);
            } else {
                // Lo cambia la primera vez, por eso debes setear tanto la ida como la vuelta
                uso.setText("Ocupado el ");
                fecha_uso.setText(item.getFecha_utilizacion());
                bg.setBackgroundResource(R.drawable.ticketdettailorange);
            }
        }

        return convertView;
    }

}