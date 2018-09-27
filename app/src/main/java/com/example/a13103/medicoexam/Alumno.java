package com.example.a13103.medicoexam;

class Alumno {

    private String Id ;
    private  String Nombre ;
    private String ApePaterno ;
    private  String ApeMaterno ;
    private String Fecha ;
    private String Correo ;
    private int IdComite ;

    public Alumno() {
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApePaterno() {
        return ApePaterno;
    }

    public void setApePaterno(String apePaterno) {
        ApePaterno = apePaterno;
    }

    public String getApeMaterno() {
        return ApeMaterno;
    }

    public void setApeMaterno(String apeMaterno) {
        ApeMaterno = apeMaterno;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public int getIdComite() {
        return IdComite;
    }

    public void setIdComite(int idComite) {
        IdComite = idComite;
    }
}
