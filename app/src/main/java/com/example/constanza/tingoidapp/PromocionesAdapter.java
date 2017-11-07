package com.example.constanza.tingoidapp;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.constanza.tingoidapp.api.model.Promocion;
import com.example.constanza.tingoidapp.api.model.Tinket;

import java.util.ArrayList;

public class PromocionesAdapter extends ArrayAdapter {
    //private LinearLayout Barra_layout;
    //private ImageView[] barra;

    public PromocionesAdapter (@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<?> objects) {
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

        if (convertView == null) {
            convertView = inflater.inflate(
                    R.layout.item_promocion,
                    parent,
                    false);

            //convertView = mInflater.inflate(R.layout.item_entrada_disponible,parent,false);
        }


        Promocion promocion = (Promocion) getItem(position);
        LinearLayout bg = (LinearLayout)convertView.findViewById(R.id.bg_promo);
        if (promocion.getAvance().equals(promocion.getMeta())){
            bg.setBackgroundResource(R.drawable.ticketdettailcyan);
        }
        else{
            bg.setBackgroundResource(R.drawable.ticketdettailorange);
        }

/*
        if (promocion.getAvance().equals(promocion.getMeta())){
            convertView = inflater.inflate(
                    R.layout.item_promocion,
                    parent,
                    false);
        }
        else {
            convertView = inflater.inflate(
                    R.layout.item_promocion_incompleta,
                    parent,
                    false);
        }
            // convertView = mInflater.inflate(R.layout.item_entrada_disponible,parent,false);
*/
        //Referencias UI

        TextView nombre_promo = (TextView) convertView.findViewById(R.id.nombre_promo);
        TextView avance_promo = (TextView) convertView.findViewById(R.id.avance_promo);
        TextView fecha_expiracion = (TextView) convertView.findViewById(R.id.fecha_exp_promo);
        LinearLayout barra_progreso = (LinearLayout) convertView.findViewById(R.id.progreso);
        //create_Barra(Integer.valueOf(promocion.getAvance()),Integer.valueOf(promocion.getMeta()));



        //Setup
        if (promocion!=null){
            nombre_promo.setText(promocion.getDescripcion());
            avance_promo.setText(promocion.getAvance()+"/"+promocion.getMeta());
            fecha_expiracion.setText(promocion.getFecha_expiracion());
            create_Barra(barra_progreso,Integer.valueOf(promocion.getAvance()),Integer.valueOf(promocion.getMeta()));
        }

        return convertView;
    }

    private void create_Barra(LinearLayout layout, int actual, int meta) {

        if(layout!=null)
            layout.removeAllViews();

        ImageView[] barra = new ImageView[102];
        if (actual<meta) {
            for (int i = 0; i < 102; i++) {

                barra[i] = new ImageView(getContext().getApplicationContext());
                if (i == 0) {
                    barra[i].setImageDrawable(ContextCompat.getDrawable(getContext().getApplicationContext(), R.drawable.promo_inicio));
                } else if (i == 101) {
                    barra[i].setImageDrawable(ContextCompat.getDrawable(getContext().getApplicationContext(), R.drawable.promo_final));
                } else if (i < (actual * 100 / meta)) {
                    barra[i].setImageDrawable(ContextCompat.getDrawable(getContext().getApplicationContext(), R.drawable.promo_avance));
                } else if (i == (actual * 100 / meta)) {
                    barra[i].setImageDrawable(ContextCompat.getDrawable(getContext().getApplicationContext(), R.drawable.promo_tope));
                } else {
                    barra[i].setImageDrawable(ContextCompat.getDrawable(getContext().getApplicationContext(), R.drawable.promo_restante));
                }
                //barra[i].setImageDrawable(ContextCompat.getDrawable(getContext().getApplicationContext(),R.drawable.promo_avance));

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 0, 0, 0);

                layout.addView(barra[i], params);
            }
        }
        else {
            for (int i = 0; i < 102; i++) {

                barra[i] = new ImageView(getContext().getApplicationContext());
                if (i == 0) {
                    barra[i].setImageDrawable(ContextCompat.getDrawable(getContext().getApplicationContext(), R.drawable.promo_inicio_fill));
                } else if (i == 101) {
                    barra[i].setImageDrawable(ContextCompat.getDrawable(getContext().getApplicationContext(), R.drawable.promo_final_fill));
                } else {
                    barra[i].setImageDrawable(ContextCompat.getDrawable(getContext().getApplicationContext(), R.drawable.promo_fill));
                }
                //barra[i].setImageDrawable(ContextCompat.getDrawable(getContext().getApplicationContext(),R.drawable.promo_avance));

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 0, 0, 0);

                layout.addView(barra[i], params);
            }
        }
    }

}
