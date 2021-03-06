package com.example.nalaka;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;


public class Arvostelusivu extends AppCompatActivity implements View.OnClickListener {

    ArvosteluClass arvostelutiedot;
    String otsikko;
    String arvosteluteksti;
    String tahdet;
    String kuvaURL;
    String videoURL;
    String kaupunki;
    String ravintola;

    VideoView videoPlayer;
    ImageView imageViewer;
    //int i = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arvostelusivu);


        videoPlayer = (VideoView) findViewById(R.id.videoView);
        imageViewer = (ImageView) findViewById(R.id.imageView);
        TextView textOtsikko = findViewById(R.id.textViewOtsikko);
        TextView textArvostelu = findViewById(R.id.textViewArvostelu);
        RatingBar arvostelutahdet = findViewById(R.id.ratingBar);
        TextView textRavintola = findViewById(R.id.textViewRavintola);
        TextView textKaupunki = findViewById(R.id.textViewKaupunki);
        
        findViewById(R.id.search_img_btn).setOnClickListener(this);
        findViewById(R.id.logo_btn).setOnClickListener(this);
        findViewById(R.id.menu_img_btn).setOnClickListener(this);

        arvostelutiedot = (ArvosteluClass) getIntent().getSerializableExtra("Arvostelu");
        otsikko = arvostelutiedot.getOtsikko();
        arvosteluteksti = arvostelutiedot.getArvosteluTeksti();
        tahdet = arvostelutiedot.getPisteet();
        kuvaURL = arvostelutiedot.getKuvaUrl();
        videoURL = arvostelutiedot.getViedoUrl();
        kaupunki = arvostelutiedot.getKaupunki();
        ravintola = arvostelutiedot.getRavintola();

        textOtsikko.setText(otsikko);
        textArvostelu.setText(arvosteluteksti);
        arvostelutahdet.setRating(Float.parseFloat(tahdet));
        imageViewer.setVisibility(View.INVISIBLE);
        videoPlayer.setVisibility(View.INVISIBLE);
        textRavintola.setText(ravintola);
        textKaupunki.setText("|    " + kaupunki);

        choosePlayer();

        /*
        String videoUrl = videoURL;
        //String videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/VolkswagenGTIReview.mp4";

        Uri viUri = Uri.parse(videoUrl);
        videoPlayer.setVideoURI(viUri);

        MediaController mediaController = new MediaController(this);
        videoPlayer.setMediaController(mediaController);
        mediaController.setAnchorView(videoPlayer);
        */
    }

    class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String picUrl = urls[0];
            Bitmap picImage = null;
            try {
                InputStream in = new java.net.URL(picUrl).openStream();
                picImage = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error to load image", e.getMessage());
                e.printStackTrace();
            }
            return picImage;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    public void choosePlayer() {

        if (kuvaURL != null && !kuvaURL.isEmpty()) {
            new DownloadImageTask(imageViewer)
                    .execute(kuvaURL);
            imageViewer.setVisibility(View.VISIBLE);

        }
        else if (videoURL != null && !videoURL.isEmpty()) {
            Uri viUri = Uri.parse(videoURL);
            videoPlayer.setVideoURI(viUri);

            MediaController mediaController = new MediaController(this);
            videoPlayer.setMediaController(mediaController);
            mediaController.setAnchorView(videoPlayer);

            //videoPlayer.seekTo(100);
            videoPlayer.setVisibility(View.VISIBLE);
            videoPlayer.start();
        }

    }


    /*public void changeView(View v) {
        i++;
        if (i == 1) {
            videoPlayer.setVisibility(View.VISIBLE);
            imageViewer.setVisibility(View.INVISIBLE);
        }

        if (i == 2) {
            i = 0;
            videoPlayer.setVisibility(View.INVISIBLE);
            imageViewer.setVisibility(View.VISIBLE);
        }

    }*/

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.search_img_btn) {
            Intent intentHakuActivity = new Intent(this, HakuActivity.class);
            startActivity(intentHakuActivity);
        }
        if (v.getId() == R.id.logo_btn){
            Intent intentPaasivu = new Intent (this, Paasivu.class);
            startActivity(intentPaasivu);
        }
        if (v.getId() == R.id.menu_img_btn) {
            PopupMenu popup = new PopupMenu(Arvostelusivu.this, findViewById(R.id.menu_img_btn));
            popup.getMenuInflater().inflate(R.menu.menu, popup.getMenu());
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    switch(item.getItemId()) {
                        case R.id.three:
                            Toast.makeText(Arvostelusivu.this, "Farewell", Toast.LENGTH_SHORT).show();
                            finishAffinity();
                            return true;
                        case R.id.two:
                            Toast.makeText(Arvostelusivu.this, "Nothing here sorry", Toast.LENGTH_LONG).show();
                            return true;
                        case R.id.one:
                            Intent intentPaasivu = new Intent (Arvostelusivu.this, Paasivu.class);
                            startActivity(intentPaasivu);
                            Toast.makeText(Arvostelusivu.this, "Pääsivu", Toast.LENGTH_SHORT).show();
                            return true;
                    }
                    return false;
                }
            });
            popup.show();
        }
    }

}


