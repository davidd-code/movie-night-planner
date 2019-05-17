package com.example.assignment_1.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.widget.ImageView;

import com.example.assignment_1.R;
import com.example.assignment_1.model.MovieImpl;

import static com.example.assignment_1.model.EventModel.movies;

public class ViewMoviePosterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_movie_poster);

        Intent i = getIntent();
        int index = i.getIntExtra("ITEM_INDEX", -1);
        ImageView img = findViewById(R.id.posterImageView);

        if(index > -1){
            int pic = getImg(index);
            scaleImg(img, pic);
        }else{
            scaleImg(img, R.drawable.image_not_available);
        }
    }

    private int getImg(int index){

        MovieImpl selectedMovie = movies.get(index);
        return selectedMovie.getImgResource();
    }

    private void scaleImg(ImageView img, int pic){

        Display screen = getWindowManager().getDefaultDisplay();
        BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), pic, options);

        int imgWidth = options.outWidth;
        int screenWidth = screen.getWidth();

        if(imgWidth>screenWidth){
            int ratio = Math.round((float)imgWidth/(float)screenWidth);
            options.inSampleSize = ratio;
        }

        options.inJustDecodeBounds = false;
        Bitmap scaledImg = BitmapFactory.decodeResource(getResources(), pic, options);
        img.setImageBitmap(scaledImg);
    }
}
