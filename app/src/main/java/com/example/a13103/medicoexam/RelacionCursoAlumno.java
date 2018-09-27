package com.example.a13103.medicoexam;

class RelacionCursoAlumno {


    private String IdRelacion ;
    private  String IdCurso ;
    private String IdAlumno ;
    private  String NombreCurso ;
    private String DescripcionCurso ;
    private int resumen ;
    private int c ;
    private double Promedio ;

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    public double getPromedio() {
        return Promedio;
    }

    public void setPromedio(double promedio) {
        Promedio = promedio;
    }

    public RelacionCursoAlumno() {
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

    public String getNombreCurso() {
        return NombreCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        NombreCurso = nombreCurso;
    }

    public String getDescripcionCurso() {
        return DescripcionCurso;
    }

    public void setDescripcionCurso(String descripcionCurso) {
        DescripcionCurso = descripcionCurso;
    }

    public int getResumen() {
        return resumen;
    }

    public void setResumen(int resumen) {
        this.resumen = resumen;
    }
}
