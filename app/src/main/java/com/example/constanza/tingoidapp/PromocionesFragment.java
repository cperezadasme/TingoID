package com.example.constanza.tingoidapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.constanza.tingoidapp.api.model.Promocion;

import java.util.ArrayList;

import static android.R.id.list;
import static com.example.constanza.tingoidapp.MainActivity.lista_promociones;
import static com.example.constanza.tingoidapp.MainActivity.usuario;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PromocionesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PromocionesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PromocionesFragment extends ListFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private ArrayAdapter adapter;
    private ListView listView;
    private View rootView;
    private TextView casino;


    private OnFragmentInteractionListener mListener;

    public PromocionesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PromocionesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PromocionesFragment newInstance(String param1, String param2) {
        PromocionesFragment fragment = new PromocionesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        casino =(TextView)rootView.findViewById(R.id.casino_tv);
        String font_path = "fonts/ThrowMyHandsUpintheAirBold.ttf";
        Typeface TF = Typeface.createFromAsset(getContext().getAssets(),font_path) ;
        casino.setTypeface(TF);

        ArrayList<Promocion> lista;

        lista = lista_promociones;

        if (lista.size()>0){
            listView = (ListView)rootView.findViewById(list);
            adapter = new PromocionesAdapter(getActivity().getApplicationContext(),R.layout.item_promocion,lista);
            listView.setAdapter(adapter);

            //MODIFICAR ACA
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Promocion promocion_seleccionada = (Promocion) adapter.getItem(position);
                    Intent intent = new Intent(getActivity().getApplicationContext(),PromoDetalleActivity.class);
                    intent.putExtra("id_avance", promocion_seleccionada.getId_avance());
                    intent.putExtra("id_promocion", promocion_seleccionada.getId_promocion());
                    intent.putExtra("usuario", usuario);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_promociones, container, false);
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    /*private void create_Barra(int actual,int meta){
        if(Barra_layout!=null)
            Barra_layout.removeAllViews();

        barra = new ImageView[100];

        for (int i = 0; i < 100; i++){
            barra[i] = new ImageView(this);
            if (i <= (actual/meta)*100){
                barra[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.promo_avance));
            }
            else {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.promo_restante));
            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0,0,0,0);

            Barra_layout.addView(barra[i],params);
        }

    }*/
}
