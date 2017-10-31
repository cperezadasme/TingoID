package com.example.constanza.tingoidapp.api.model;


public class promocionBody {
    String id_avance;
    String id_promocion;
    String correo;

    public promocionBody(String id_avance, String id_promocion, String correo) {
        this.id_avance = id_avance;
        this.id_promocion = id_promocion;
        this.correo = correo;
    }
}
