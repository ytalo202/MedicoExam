package com.example.a13103.medicoexam;

import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.snowdream.android.widget.SmartImageView;

import java.util.ArrayList;

class AdaptadorVideos extends BaseAdapter {



    ArrayList<RelacionTemaAlumno> elementos;

    // Vista
    Context contexto;

    // Permite cargar un layout a una vista (inflar)
    LayoutInflater inflador;

    public AdaptadorVideos(ArrayList<RelacionTemaAlumno> elementos, Context contexto) {
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
        View vista = inflador.inflate(R.layout.cuadrilla_videos,null);

        AdaptadorVideos.PrototipoCelda celda = new AdaptadorVideos.PrototipoCelda();


        celda.tvNum  = vista.findViewById(R.id.txtNum);





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

       //String urlfinal = "http://192.168.56.1/medico/imagenes/producto/video.png";
       String urlfinal = "https://loli202.000webhostapp.com/medico/imagenes/producto/video.png";


        Rect rect = new Rect(celda.smartImage.getLeft(),celda.smartImage.getTop(),celda.smartImage.getRight(),celda.smartImage.getBottom());
        celda.smartImage.setImageUrl(urlfinal,rect);



        celda.tvNum.setText("Video N# "+getItem(posicion).getNumVideo());










        return vista;
    }


    public static class PrototipoCelda {
        TextView tvNum;





        SmartImageView smartImage;



    }
}
