package com.example.a13103.medicoexam;

public class PreguntasModel {

    boolean isSelected;
    String enunciadoPregunta;
    String enunciadoAlternativa;
    String idAlternativa;
    String idPregunta;
    String resumen;
    int num;

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public PreguntasModel() {
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getEnunciadoPregunta() {
        return enunciadoPregunta;
    }

    public void setEnunciadoPregunta(String enunciadoPregunta) {
        this.enunciadoPregunta = enunciadoPregunta;
    }

    public String getEnunciadoAlternativa() {
        return enunciadoAlternativa;
    }

    public void setEnunciadoAlternativa(String enunciadoAlternativa) {
        this.enunciadoAlternativa = enunciadoAlternativa;
    }

    public String getIdAlternativa() {
        return idAlternativa;
    }

    public void setIdAlternativa(String idAlternativa) {
        this.idAlternativa = idAlternativa;
    }

    public String getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(String idPregunta) {
        this.idPregunta = idPregunta;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
