package com.example.constanza.tingoidapp.api.model;

import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class JsonParserTinket {

    public List<Tinket> readJson (InputStream inputStream) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream,"UTF-8"));
        try {
            //Leer Array
            return leerArray(reader);
        } finally {
            reader.close();
        }
    }

    private List<Tinket> leerArray(JsonReader reader) throws IOException {
        List<Tinket> tinkets = new ArrayList<>();
        reader.beginArray();

        while (reader.hasNext()){
            tinkets.add(leerTinket(reader));
        }
        reader.endArray();
        return tinkets;
    }

    private Tinket leerTinket(JsonReader reader) throws IOException {
        String id = null;
        String fecha_emision = null;
        String fecha_utilizacion = null;
        String fecha_expiracion = null;
        String valido = null;
        String empresa = null;

        reader.beginObject();
        while (reader.hasNext()){
            String name = reader.nextName();
            switch (name){
                case "id":
                    id = reader.nextString();
                    break;
                case "fecha_emision":
                    fecha_emision = reader.nextString();
                    break;
                case "fecha_utilizacion":
                    fecha_utilizacion = reader.nextString();
                    break;
                case "fecha_expiracion":
                    fecha_expiracion = reader. nextString();
                    break;
                case "valido":
                    valido = reader.nextString();
                    break;
                case "empresa":
                    empresa = reader.nextString();
                    break;
            }
        }
        reader.endObject();
        return new Tinket(id,fecha_emision,fecha_utilizacion,fecha_expiracion,valido,empresa);
    }
}
