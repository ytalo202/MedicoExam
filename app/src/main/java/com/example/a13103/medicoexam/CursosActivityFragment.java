package com.example.a13103.medicoexam;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.ArrayList;

public class CursosActivityFragment extends Fragment {



    ListView LSTVW;


    String idUsuario;
    static final String PREFERENCIAS_COMPARTIDAD= "preferenciaCompartida";

    static final String ID_USUARIO ="IdUsuario";

    static final String ID_RELACION_ALUMNO_CURSO ="IdRelacionAlumnoCurso";
    static final String PASSWORD_USUARIO ="PassWordUsuario";
    static final String ID_CURSO ="IdCurso";
    static final String CORREO_USUARIO ="CorreoUsuario";
    static final String ID_COMITE ="IdComite";
    String idRelacion="0";

    ImageView btnSesion;


    // static final  String FLAG = "CAMPEONATADO";


    ArrayList<RelacionCursoAlumno> elementos = new ArrayList<>();

    AdaptadorCursos adaptadorCursos;


    public CursosActivityFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);












        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences configuracion = getContext().getSharedPreferences(PREFERENCIAS_COMPARTIDAD,0);
                idUsuario = configuracion.getString(ID_USUARIO,"0");


               new  TareaDialogo().execute();
            }
        },1000);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


      //  configuracion = getContext().getSharedPreferences(PREFERENCIAS_COMPARTIDAD,0);
        //sel1 = Integer.parseInt(configuracion.getString(SL_P1,"0"));


        View vista = inflater.inflate(R.layout.fragment_cursos, container, false);
        LSTVW = vista.findViewById(R.id.LSTVW);
        btnSesion = vista.findViewById(R.id.btnSesi2);


        btnSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Desea cerrar sesión ...");
                builder.setTitle("Mensaje...");
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent p = new Intent(getContext(),LoginActivity.class);

                        SharedPreferences configuracion =getActivity().getSharedPreferences(PREFERENCIAS_COMPARTIDAD,0);
                        //modificar el archivo utiliso un editor
                        SharedPreferences.Editor editor = configuracion.edit();

                        //ingreso la informacion
                        editor.putString(ID_USUARIO,"0");
                        editor.putString(CORREO_USUARIO,"0");
                        editor.putString(ID_COMITE,"0");
                        editor.putString(PASSWORD_USUARIO,"0");
                        editor.commit();

                        startActivity(p);
                        getActivity() .finish();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });




        return vista;



    }





    public class TareaDialogo extends AsyncTask<String, Void, Void> {

        ProgressDialog progressDialog;


        @Override
        protected Void doInBackground(String... strings) {




                elementos.clear();

                //  String URL = "http://192.168.56.1/medico/CONTROLADOR/RelacionAlumnoCursosControlador.php?op=1&codAlu="+id;
                String URL = "https://loli202.000webhostapp.com/medico/CONTROLADOR/RelacionAlumnoCursosControlador.php?op=1&codAlu="+idUsuario;
                // String URL = "http://192.168.1.3/FUTBOL/CONTROLADOR/CampeonatoControlador.php?op=1";
                RequestQueue queue = Volley.newRequestQueue(getContext());
                StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try
                        {
                            RelacionCursoAlumno pb;
                            JSONArray jsonArray = new JSONArray(response);

                            for(int i=0; i<jsonArray.length(); i++) {

                                pb = new RelacionCursoAlumno();

                                String estado = jsonArray.getJSONObject(0).getString("estado");

                                if (estado.equals("success")) {

                                    pb.setIdRelacion(jsonArray.getJSONObject(i).getString("IdRelacion"));
                                    pb.setIdCurso(jsonArray.getJSONObject(i).getString("Id"));
                                    pb.setNombreCurso(jsonArray.getJSONObject(i).getString("Nombre"));
                                    pb.setResumen(jsonArray.getJSONObject(i).getInt("Resumen"));
                                    pb.setPromedio(jsonArray.getJSONObject(i).getDouble("Promedio"));


                                    elementos.add(pb);

                                }
                                else {
                                    Toast.makeText(getContext(),

                                            "Usted no esta matriculado en Ningun Curso"
                                            , Toast.LENGTH_SHORT).show();
                                }


                            }

                            adaptadorCursos = new AdaptadorCursos(elementos, getActivity());


                            LSTVW.setAdapter(adaptadorCursos);


                            LSTVW.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                          /*  Toast.makeText(getContext(),

                                    //  elementos.get(i).getNombre()
                                    elementos.get(i).getIdCurso()
                                    , Toast.LENGTH_SHORT).show();*/





                                    Intent intent = new Intent(getContext(), TemasActivity.class);



                                    //accedo o creo el archivo de preferencias comprartidas
                                    SharedPreferences configuracion = getContext().getSharedPreferences(PREFERENCIAS_COMPARTIDAD,0);
                                    //modificar el archivo utiliso un editor
                                    SharedPreferences.Editor editor = configuracion.edit();

                                    //ingreso la informacion
                                    editor.putString(ID_CURSO,elementos.get(i).getIdCurso().toString());

                                    editor.putString(ID_RELACION_ALUMNO_CURSO,elementos.get(i).getIdRelacion().toString());

                                    //editor.putBoolean(FLAG,true);

                                    //confirmo el ingreso de informacion
                                    editor.commit();


                                    // intent.putExtra("IdCampeonato", elementos.get(i).getId().toString());
                                    //intent.putExtra("NombreCampeonato", elementos.get(i).getNombre().toString());

                                    startActivity(intent);

                                    getActivity().finish();




                                }
                            });



                        }catch (Exception ex)
                        {
                            Toast.makeText(getContext(), "ERROR: "+ex.getMessage(),Toast.LENGTH_LONG).show();
                        }

                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "ERROR DE CODIGO", Toast.LENGTH_LONG).show();
                    }
                });

                queue.add(stringRequest);

            return null;
        }

        @Override
        protected void onPreExecute() {

            progressDialog = ProgressDialog.show(getActivity(), "",
                    "Cargando...");
        }

        @Override
        protected void onPostExecute(Void s) {

            progressDialog.dismiss();

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }


}
