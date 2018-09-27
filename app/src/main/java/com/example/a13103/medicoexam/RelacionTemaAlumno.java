package com.example.a13103.medicoexam;

public class RelacionTemaAlumno {

    private String IdRelacion ;
    private  String IdCurso ;
    private String IdAlumno ;
    private String IdTema;
    private String nota;

    private  String NombreTema ;
    private String DescripcionCurso ;

    private String Ponente ;

    private String PonenteOp ;

    private String IdLink;
    private String IdLinkOp;



    private int resumen ;
    private double Promedio ;


    private int numVideo ;

    private int c ;


    public RelacionTemaAlumno() {
    }

    public String getIdRelacion() {
        return IdRelacion;
    }

    public void setIdRelacion(String idRelacion) {
        IdRelacion = idRelacion;
    }

    public String getIdCurso() {
        return IdCurso;
    }

    public void setIdCurso(String idCurso) {
        IdCurso = idCurso;
    }

    public String getIdAlumno() {
        return IdAlumno;
    }

    public void setIdAlumno(String idAlumno) {
        IdAlumno = idAlumno;
    }

    public String getIdTema() {
        return IdTema;
    }

    public void setIdTema(String idTema) {
        IdTema = idTema;
    }

    public String getNombreTema() {
        return NombreTema;
    }

    public void setNombreTema(String nombreTema) {
        NombreTema = nombreTema;
    }

    public String getDescripcionCurso() {
        return DescripcionCurso;
    }

    public void setDescripcionCurso(String descripcionCurso) {
        DescripcionCurso = descripcionCurso;
    }

    public String getPonente() {
        return Ponente;
    }

    public void setPonente(String ponente) {
        Ponente = ponente;
    }

    public String getPonenteOp() {
        return PonenteOp;
    }

    public void setPonenteOp(String ponenteOp) {
        PonenteOp = ponenteOp;
    }

    public String getIdLink() {
        return IdLink;
    }

    public void setIdLink(String idLink) {
        IdLink = idLink;
    }

    public String getIdLinkOp() {
        return IdLinkOp;
    }

    public void setIdLinkOp(String idLinkOp) {
        IdLinkOp = idLinkOp;
    }

    public int getResumen() {
        return resumen;
    }

    public void setResumen(int resumen) {
        this.resumen = resumen;
    }

    public double getPromedio() {
        return Promedio;
    }

    public void setPromedio(double promedio) {
        Promedio = promedio;
    }

    public int getNumVideo() {
        return numVideo;
    }

    public void setNumVideo(int numVideo) {
        this.numVideo = numVideo;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }
}
