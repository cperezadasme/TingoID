package com.example.constanza.tingoidapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.example.constanza.tingoidapp.api.model.Tinket;

import java.util.ArrayList;

import static android.R.id.list;
import static com.example.constanza.tingoidapp.MainActivity.lista_utilizados;


public class UtilizadasFragment extends ListFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ArrayAdapter adapter;
    private ListView listView;
    private View rootView;

    // TODO: Rename and change types of parameters

    private OnFragmentInteractionListener mListener;

    public UtilizadasFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    /*
    public static EntradasFragment newInstance(String param1, String param2) {
        EntradasFragment fragment = new EntradasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayList<Tinket> lista;
        lista = lista_utilizados;

        if (lista.size()>0){
            listView = (ListView)rootView.findViewById(list);
            adapter = new UtilizadasAdapter(getActivity().getApplicationContext(),R.layout.item_entrada_utilizada,lista);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Tinket tinket_seleccionado = (Tinket) adapter.getItem(position);
                    Intent intent = new Intent(getActivity().getApplicationContext(),DetalleActivity.class);
                    intent.putExtra("id_tinket", tinket_seleccionado.getId());
                    startActivity(intent);
                }
            });
        }

        else {
            TextView usadas = (TextView)rootView.findViewById(R.id.entradas_utilizadas);
            usadas.setText("No tienes entradas utilizadas");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_entradas, container, false);
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
}
