package com.example.constanza.tingoidapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.constanza.tingoidapp.api.model.Tinket;

import java.util.ArrayList;

import static android.R.id.list;
import static com.example.constanza.tingoidapp.MainActivity.lista_tinkets;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View rootView;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TextView tinkets_por_exp =(TextView) rootView.findViewById(R.id.tinkets_por_expirar);
        String font_path = "fonts/ThrowMyHandsUpintheAirBold.ttf";
        Typeface TF = Typeface.createFromAsset(getContext().getAssets(),font_path) ;
        tinkets_por_exp.setTypeface(TF);

        ArrayList<Tinket> lista = new ArrayList<>();
        if (lista_tinkets.size()>=5){
            for (int i = 0; i<5; i++){
                Tinket item = lista_tinkets.get(i);
                lista.add(item);
            }

            ListView listView = (ListView)rootView.findViewById(list);
            final ListaEntradasAdapter adapter = new ListaEntradasAdapter(getActivity().getApplicationContext(),R.layout.item_entrada_disponible,lista);
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

        else if (lista_tinkets.size() < 5 && lista_tinkets.size()>=1){
            for (int i = 0; i < lista_tinkets.size(); i++){
                Tinket item = lista_tinkets.get(i);
                lista.add(item);
            }
            ListView listView = (ListView)rootView.findViewById(list);
            final ListaEntradasAdapter adapter = new ListaEntradasAdapter(getActivity().getApplicationContext(),R.layout.item_entrada_disponible,lista);
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
            tinkets_por_exp.setText("No tienes tinkets por expirar");
        }
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
