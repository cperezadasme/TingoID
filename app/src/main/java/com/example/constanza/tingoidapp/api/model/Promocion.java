package com.example.constanza.tingoidapp.api.model;



public class Promocion {
    private String id_avance;
    private String id_promocion;
    private String descripcion;
    private String fecha_expiracion;
    private String empresa;
    private String avance;
    private String meta;

    public Promocion(String id_avance, String id_promocion, String descripcion, String fecha_expiracion,
                     String empresa, String avance, String meta) {
        this.id_avance = id_avance;
        this.id_promocion = id_promocion;
        this.descripcion = descripcion;
        this.fecha_expiracion = fecha_expiracion;
        this.empresa = empresa;
        this.avance = avance;
        this.meta = meta;
    }

    public String getId_avance() {
        return id_avance;
    }

    public void setId_avance(String id_avance) {
        this.id_avance = id_avance;
    }

    public String getId_promocion() {
        return id_promocion;
    }

    public void setId_promocion(String id_promocion) {
        this.id_promocion = id_promocion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha_expiracion() {
        return fecha_expiracion;
    }

    public void setFecha_expiracion(String fecha_expiracion) {
        this.fecha_expiracion = fecha_expiracion;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getAvance() {
        return avance;
    }

    public void setAvance(String avance) {
        this.avance = avance;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }
}
