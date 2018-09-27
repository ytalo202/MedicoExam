package com.example.a13103.medicoexam;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ConfiguracionesActivityFragment extends Fragment  {

    String fechaNacimiento="0";
    Calendar c = Calendar.getInstance();

    private ArrayList IdComite  = new ArrayList();
    private ArrayList NombreComite  = new ArrayList();
    int caso=0;
    Spinner spnCategoria;
    Button editar,btnFecha,btnNewClave,guardar;
    ImageView btnSesion;
    EditText edNombre,edApePaterno,edApeMaterno,edCorreo,edFecha;
    String idUsuario;
    int idComite;
    static final String PREFERENCIAS_COMPARTIDAD = "preferenciaCompartida";
    SharedPreferences configuracion;
    static final String ID_COMITE ="IdComite";

    static final String ID_USUARIO = "IdUsuario";

    static final String CORREO_USUARIO ="CorreoUsuario";

    static final String PASSWORD_USUARIO ="PassWordUsuario";

    String nom,apePaterno,apeMaterno,fecha,idComit,codigoComite;

    ArrayList<Alumno> elementos = new ArrayList<>();

    AdaptadorCursos adaptadorCursos;







    public ConfiguracionesActivityFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);






        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                configuracion = getContext().getSharedPreferences(PREFERENCIAS_COMPARTIDAD, 0);
                idUsuario = configuracion.getString(ID_USUARIO, "0");
                idComite = Integer.parseInt( configuracion.getString(ID_COMITE, "0"));
                new CargarDa().execute();

                ObtenerDatosSpinner();
            }
        },1000);





    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View vista = inflater.inflate(R.layout.fragment_configure, container, false);
        editar = vista.findViewById(R.id.btnEditarDatos);
        guardar = vista.findViewById(R.id.btnGuardarDatos);
        edNombre = vista.findViewById(R.id.etNombre);
        edApePaterno = vista.findViewById(R.id.etApePaterno);
        edApeMaterno = vista.findViewById(R.id.etApeMaterno);
        edFecha = vista.findViewById(R.id.showDate2);
        btnFecha = vista.findViewById(R.id.btnfragfecha);
        btnSesion = vista.findViewById(R.id.btnSe);
        edCorreo = vista.findViewById(R.id.etCorreo);
        btnNewClave = vista.findViewById(R.id.btnNewClave);
        spnCategoria = vista.findViewById(R.id.spnCategoria2);

        btnFecha.setVisibility(View.GONE);
        btnNewClave.setVisibility(View.GONE);
        guardar.setVisibility(View.GONE);

        spnCategoria.setEnabled(false);


        btnNewClave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent p = new Intent(getContext(),NewClave.class);
                startActivity(p);

                getActivity().finish();
            }
        });
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
        btnFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenerFecha();


            }
        });


        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                    btnFecha.setVisibility(View.VISIBLE);
                    btnNewClave.setVisibility(View.VISIBLE);
                guardar.setVisibility(View.VISIBLE);

                    edNombre.setEnabled(true);
                    edApePaterno.setEnabled(true);
                    edApeMaterno.setEnabled(true);
                    edNombre.setEnabled(true);
                    spnCategoria.setEnabled(true);

                    //edNombre.setFocusable(true);

                    editar.setVisibility(View.GONE);

                   //getActivity(). recreate();







            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Desea guardar los cambios");
                builder.setTitle("Mensaje...");
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        new updateUsuario().execute();




                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        Intent p = new Intent(getActivity(),MainActivity.class);
                        startActivity(p);
                        Toast.makeText(getContext(), "Operación cancelada", Toast.LENGTH_SHORT).show();


                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        return vista;


    }



    public void obtenerFecha(){

        final int mes = c.get(Calendar.MONTH);
        final int dia = c.get(Calendar.DAY_OF_MONTH);
        final int anio = c.get(Calendar.YEAR);

        DatePickerDialog recogerFecha = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                int mesActual = month + 1;
                String diaFormateado = (dayOfMonth < 10)? "0" + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                String mesFormateado = (mesActual < 10)? "0" + String.valueOf(mesActual):String.valueOf(mesActual);

                edFecha.setText( year+"-"+mesFormateado+"-"+diaFormateado);

            }
        },anio, mes, dia);

        recogerFecha.show();





    }




    public String ObtenerNombre() {
        return edNombre.getText().toString();
    }
    public String ObtenerApePaterno() {
        return edApePaterno.getText().toString();
    }

    public String ObtenerApeMaterno() {

        return edApeMaterno.getText().toString();
    }
    public String ObtenerFecha() {
        return edFecha.getText().toString();
    }



    public void ObtenerDatosSpinner()
    {
        IdComite.clear();
        NombreComite.clear();

       // String URL = "http://192.168.56.1/medico/CONTROLADOR/ComiteControlador.php?op=1";
        String URL = "https://loli202.000webhostapp.com/medico/CONTROLADOR/ComiteControlador.php?op=1";


        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {


                    JSONArray jsonArray = new JSONArray(response);

                    for(int i=0; i<jsonArray.length(); i++) {

                        IdComite.add(jsonArray.getJSONObject(i).getString("Id"));
                        NombreComite.add(jsonArray.getJSONObject(i).getString("Nombre"));


                    }


                    spnCategoria.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.spinner_item, NombreComite));
                    spnCategoria.setSelection(idComite);

                    spnCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            codigoComite = String.valueOf(IdComite.get(i));

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });


                }catch (Exception ex)
                {
                    Toast.makeText(getContext(), "ERROR: "+ex.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "ERROR DE CODIGO", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(stringRequest);
    }








    private class updateUsuario extends AsyncTask<String, Void, Void> {

        ProgressDialog progressDialog;


        @Override
        protected Void doInBackground(String... strings) {








                String URL = "https://loli202.000webhostapp.com/medico/CONTROLADOR/UsuarioControlador.php?op=5"
                        + "&CodUsu="+idUsuario
                        +"&ApePaterno="+ObtenerApePaterno()
                        +"&ApeMaterno="+ ObtenerApeMaterno()
                        +"&IdComite="+codigoComite
                        +"&Nombre="+ObtenerNombre()
                        +"&fecha="+ObtenerFecha();



                RequestQueue queue = Volley.newRequestQueue(getContext());
                StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try
                        {
                            JSONArray jsonArray = new JSONArray(response);
                            String estado = jsonArray.getJSONObject(0).getString("estado");

                            if (estado.equals("success")) {


                                Intent p = new Intent(getContext(),MainActivity.class);
                                Toast.makeText(getContext(), "Cambios Guardados", Toast.LENGTH_SHORT).show();



                                //modificar el archivo utiliso un editor
                                SharedPreferences.Editor editor = configuracion.edit();

                                //ingreso la informacion

                                editor.putString(ID_COMITE,codigoComite);

                                editor.commit();

                                startActivity(p);
                                getActivity().finish();

                            }else
                            {
                                Toast.makeText(getContext(), "Sin Cambios", Toast.LENGTH_SHORT).show();
                            }

                        }catch (Exception ex)
                        {
                            Toast.makeText(getContext(), "ERROR: "+ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "ERROR DE CODIGO", Toast.LENGTH_SHORT).show();
                    }
                });

                queue.add(stringRequest);


            return null;
        }

        @Override
        protected void onPreExecute() {

            progressDialog = ProgressDialog.show(getContext(), "",
                    "Cargando...");
        }

        @Override
        protected void onPostExecute(Void s) {
            progressDialog.dismiss();
            //tvResultado.setText(s);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }










    public class CargarDa extends AsyncTask<String, Void, Void> {

        ProgressDialog progressDialog;


        @Override
        protected Void doInBackground(String... strings) {





                elementos.clear();


                //  String URL = "http://192.168.56.1/medico/CONTROLADOR/UsuarioControlador.php?op=4&CodUsu="+id;
                String URL = "https://loli202.000webhostapp.com/medico/CONTROLADOR/UsuarioControlador.php?op=4&CodUsu="+idUsuario;


                RequestQueue queue = Volley.newRequestQueue(getContext());
                StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try
                        {
                            Alumno pb;
                            JSONArray jsonArray = new JSONArray(response);



                            for(int i=0; i<jsonArray.length(); i++) {

                                pb = new Alumno();


                                String estado = jsonArray.getJSONObject(0).getString("estado");




                                if (estado.equals("success")) {


                                    pb.setId(jsonArray.getJSONObject(i).getString("Id"));
                                    pb.setNombre(jsonArray.getJSONObject(i).getString("Nombre"));
                                    pb.setApePaterno(jsonArray.getJSONObject(i).getString("ApePaterno"));
                                    pb.setApeMaterno(jsonArray.getJSONObject(i).getString("ApeMaterno"));
                                    pb.setFecha(jsonArray.getJSONObject(i).getString("FechaNacimiento"));
                                    pb.setCorreo(jsonArray.getJSONObject(i).getString("Correo"));
                                    pb.setIdComite(jsonArray.getJSONObject(i).getInt("IdComite"));
                                    elementos.add(pb);

                                }
                                else {
                                    Toast.makeText(getContext(),

                                            "Consulte al Administrador"
                                            , Toast.LENGTH_SHORT).show();
                                }





                                edNombre.setText(elementos.get(0).getNombre());
                                edApePaterno.setText(elementos.get(0).getApePaterno());
                                edApeMaterno.setText(elementos.get(0).getApeMaterno() );
                                edFecha.setText(elementos.get(0).getFecha() );
                                edCorreo.setText(elementos.get(0).getCorreo() );




                            }



                        }
                        catch (Exception ex)
                        {
                            Toast.makeText(getContext(), "ERROR: "+ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "ERROR DE CODIGO", Toast.LENGTH_SHORT).show();
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


