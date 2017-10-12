package com.example.constanza.tingoidapp.api.model;

/**
 * Created by Constanza on 12-10-17.
 */

public class detalleBody {
    private String id_tinket;

    public detalleBody(String id_tinket) {
        this.id_tinket = id_tinket;
    }

    public String getId_tinket() {
        return id_tinket;
    }

    public void setId_tinket(String id_tinket) {
        this.id_tinket = id_tinket;
    }
}
