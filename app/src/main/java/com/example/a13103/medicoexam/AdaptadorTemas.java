package com.example.a13103.medicoexam;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.snowdream.android.widget.SmartImageView;

import java.util.ArrayList;

class AdaptadorTemas extends BaseAdapter {

    ArrayList<RelacionTemaAlumno> elementos;

    // Vista
    Context contexto;

    double suma =0;

    // Permite cargar un layout a una vista (inflar)
    LayoutInflater inflador;

    public AdaptadorTemas(ArrayList<RelacionTemaAlumno> elementos, Context contexto) {
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
    public RelacionTemaAlumno getItem(int posicion) {
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
        View vista = inflador.inflate(R.layout.cuadrilla_temas,null);

        AdaptadorTemas.PrototipoCelda celda = new AdaptadorTemas.PrototipoCelda();


        celda.tvNombre = vista.findViewById(R.id.txtNombre);


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

       // String urlfinal = "http://192.168.56.1/medico/imagenes/producto/icoTema.png";
        String urlfinal = "https://loli202.000webhostapp.com/medico/imagenes/producto/icoTema.png";

        Rect rect = new Rect(celda.smartImage.getLeft(),celda.smartImage.getTop(),celda.smartImage.getRight(),celda.smartImage.getBottom());
        celda.smartImage.setImageUrl(urlfinal,rect);



        celda.tvNombre.setText(getItem(posicion).getNombreTema());

        if (getItem(posicion).getC()==0){

            celda.tvResultado.setText("--");

        }else {
        String Nota="";

            if (Integer.parseInt(getItem(posicion).getNota( ) )>15){
                Nota = "<font color=#28EA11>"+getItem(posicion).getNota( )+"</font> ";
            }
            else if(Integer.parseInt(getItem(posicion).getNota( ) ) >10 ){
                Nota = "<font color=#F1A41B>"+getItem(posicion).getNota( )+"</font> ";
            }
            else {
                Nota = "<font color=#F42B0B>"+getItem(posicion).getNota( )+"</font> ";
            }

            celda.tvResultado.setText(Html.fromHtml(Nota));
        }








        return vista;
    }


    public static class PrototipoCelda {
        TextView tvNombre;



        TextView tvResultado;

        SmartImageView smartImage;



    }







}
