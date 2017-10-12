package com.example.constanza.tingoidapp.api.model;

public class TinketBody {
    private String id_tinket;
    private String empresa;
    private String usuario;

    public TinketBody(String id_tinket, String empresa, String usuario) {
        this.id_tinket = id_tinket;
        this.empresa = empresa;
        this.usuario = usuario;
    }

    public String getId_tinket() {
        return id_tinket;
    }

    public void setId_tinket(String id_tinket) {
        this.id_tinket = id_tinket;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
