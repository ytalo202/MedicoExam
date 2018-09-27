package com.example.a13103.medicoexam;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.snowdream.android.widget.SmartImageView;

import java.text.DecimalFormat;
import java.util.ArrayList;

class AdaptadorCursos extends BaseAdapter {


    ArrayList<RelacionCursoAlumno> elementos;

    // Vista
    Context contexto;

    // Permite cargar un layout a una vista (inflar)
    LayoutInflater inflador;

    public AdaptadorCursos(ArrayList<RelacionCursoAlumno> elementos, Context contexto) {
        this.elementos = elementos;
        this.contexto = contexto;
        this.inflador = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    // Cantidad de filas a mostrar
    @Override
    public int getCount() {
        return elementos.size();
    }

    // Devuelve el elemento en un fila dada
    @Override
    public RelacionCursoAlumno getItem(int posicion) {
        return elementos.get(posicion);
    }

    // Devuelve el identificador
    @Override
    public long getItemId(int posicion) {
        return posicion;
    }

    // Devuelve la vista para cada una de las filas
    @Override
    public View getView(int posicion, View convertView, ViewGroup parent) {


        // La vista debe estar asociada al layout celda_prototipo
        View vista = inflador.inflate(R.layout.cuadrilla_cursos,null);

        AdaptadorCursos.PrototipoCelda celda = new AdaptadorCursos.PrototipoCelda();


        celda.tvNombre = vista.findViewById(R.id.txtNombre);

        celda.tvEstado = vista.findViewById(R.id.txtEstado);
        celda.tvResultado = vista.findViewById(R.id.txtResultado);


        celda.smartImage= vista.findViewById(R.id.imagenCam);

/*
        //*LTL-15/07/2018
        String Idcamp;
        String Clave;
        String format;
        String Urlcamp;

        Idcamp  = getItem(posicion).getId();
        Clave   = "CAMPEONATO";
        format  = ".jpg";
        Urlcamp = "http://192.168.1.3/FUTBOL/imagenes/CAMPEONATOS/";


        Urlcamp  = Urlcamp + Clave + Idcamp + format;
        String urlfinal = Urlcamp;
        //****


*/

        //String urlfinal = "http://192.168.56.1/medico/imagenes/producto/icoCurso.png";
        String urlfinal = "https://loli202.000webhostapp.com/medico/imagenes/producto/icoCurso.png";

        Rect rect = new Rect(celda.smartImage.getLeft(),celda.smartImage.getTop(),celda.smartImage.getRight(),celda.smartImage.getBottom());
        celda.smartImage.setImageUrl(urlfinal,rect);



        celda.tvNombre.setText(getItem(posicion).getNombreCurso());

        if (getItem(posicion).getResumen() == 1){


            DecimalFormat df = new DecimalFormat("0.00");


            celda.tvEstado.setTextColor(Color.GREEN);
            celda.tvEstado.setText("En Curso");

            celda.tvResultado.setText(df.format(getItem(posicion).getPromedio())   +"");
        }
        else {
            celda.tvEstado.setText("Finalizado");
            celda.tvEstado.setTextColor(Color.RED);

            if(getItem(posicion).getPromedio()<12){
                celda.tvResultado.setTextColor(Color.RED);
            }
            else if(getItem(posicion).getPromedio()>=12 && getItem(posicion).getPromedio()<=15){
                celda.tvResultado.setTextColor(Color.YELLOW);
            }
            else {
                celda.tvResultado.setTextColor(Color.GREEN);
            }
            celda.tvResultado.setText("Nota: "+getItem(posicion).getPromedio());
        }






        return vista;
    }


    private String twoDigits(String n) {
        return (Double.parseDouble( n)<1) ? ("0"+n) : String.valueOf(n);
    }


    public static class PrototipoCelda {
        TextView tvNombre;

        TextView tvEstado;

        TextView tvResultado;

        SmartImageView smartImage;



    }
}

