package com.example.a13103.medicoexam;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YoutubeActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener,YouTubePlayer.PlaybackEventListener {


    YouTubePlayerView youTubePlayerView;

String link ="azxDhcKYku4";
    String claveYoutube = "AIzaSyChvbQJ9uhM39g31gu2MYPXEJBxsJZ0umk";

Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);






        youTubePlayerView = findViewById(R.id.youtube_view);

        new Handler().post(new Runnable() {
            @Override
            public void run() {

                Bundle mBundle = getIntent().getExtras();
                link = mBundle.getString("Link");

                Play();
            }
        });



    }


    public void Play(){
        youTubePlayerView.initialize(claveYoutube, this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean fueRestaurado) {


        if (!fueRestaurado){
            youTubePlayer.cueVideo(link);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

        if (youTubeInitializationResult.isUserRecoverableError())
        {
            youTubeInitializationResult.getErrorDialog(this,1).show();

        }else {

            String error="Error al Inicializar Youtube"+youTubeInitializationResult.toString();
            Toast.makeText(getApplication(),error , Toast.LENGTH_SHORT).show();

        }

    }

    protected void onActivityResult(int requestCode, int resultcod, Intent data){
        if (resultcod==1)
        {
            getYoutubePlayerProvider().initialize(claveYoutube,this);
        }
    }

    protected YouTubePlayer.Provider getYoutubePlayerProvider()
    {
        return youTubePlayerView;
    }

    @Override
    public void onPlaying() {

    }

    @Override
    public void onPaused() {

    }

    @Override
    public void onStopped() {

    }

    @Override
    public void onBuffering(boolean b) {

    }

    @Override
    public void onSeekTo(int i) {

    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Quieres Salir de la aplicación");
        builder.setTitle("Mensaje...");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
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

    public void VolverDetalle(View view) {

        Intent intent = new Intent(this, TemaDetalleActivity.class);


        startActivity(intent);

        finish();
    }
}