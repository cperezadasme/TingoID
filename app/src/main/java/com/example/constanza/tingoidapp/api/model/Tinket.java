package com.example.constanza.tingoidapp.api.model;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class Tinket {
    private String id;
    private String fecha_emision;
    private String fecha_utilizacion;
    private String fecha_expiracion;
    private String valido;
    private String empresa;
    private String detalle;

    public Tinket(String id, String fecha_emision, String fecha_utilizacion, String fecha_expiracion, String valido, String empresa, String detalle) {
        this.id = id;
        this.fecha_emision = fecha_emision;
        this.fecha_utilizacion = fecha_utilizacion;
        this.fecha_expiracion = fecha_expiracion;
        this.valido = valido;
        this.empresa = empresa;
        this.detalle = detalle;
    }

    public String getId() {
        return id;
    }

    public int getId_Tinket(){
        return id.hashCode();
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFecha_emision() {
        return fecha_emision;
    }

    public void setFecha_emision(String fecha_emision) {
        this.fecha_emision = fecha_emision;
    }

    public String getFecha_utilizacion() {
        return fecha_utilizacion;
    }

    public void setFecha_utilizacion(String fecha_utilizacion) {
        this.fecha_utilizacion = fecha_utilizacion;
    }

    public String getFecha_expiracion() {
        return fecha_expiracion;
    }

    public void setFecha_expiracion(String fecha_expiracion) {
        this.fecha_expiracion = fecha_expiracion;
    }

    public String getValido() {
        return valido;
    }

    public void setValido(String valido) {
        this.valido = valido;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getDetalle() {return detalle; }

    public void  setDetalle(String detalle) {this.detalle = detalle;}

    public static Tinket getItem(int id, ArrayList<Tinket> lista){
        for (int i=0; i<lista.size();i++){
            Tinket tinket = lista.get(i);
            if((tinket.getId_Tinket()) == id){
                return  tinket;
            }
        }
        return null;
    }
}
