package com.example.pasrv;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailPage extends AppCompatActivity {
    Intent pindah;
    Model MovieData;
    TextView tvTitle, tvSinopsis, tvPopularity, tvReleaseDate, tvVoteAverage, tvVote;
    ImageView imageDetail;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page);

        pindah = getIntent();
        MovieData = (Model) pindah.getParcelableExtra("DataMovie");

        imageDetail = findViewById(R.id.imageDetail);
        Glide.with(imageDetail).load( "https://image.tmdb.org/t/p/original"+ MovieData.getPoster()).into(imageDetail);

        tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText(MovieData.getJudul());

        tvSinopsis = findViewById(R.id.tvSinopsis);
        tvSinopsis.setText("Overview : " +
                "" + MovieData.getSinopsis());

        tvPopularity = findViewById(R.id.tvPopularity);
        tvPopularity.setText("Popularity : " + MovieData.getPopularity());

        tvReleaseDate = findViewById(R.id.tvDateRelease);
        tvReleaseDate.setText("Release Date : " + MovieData.getReleaseDate());

        tvVoteAverage = findViewById(R.id.tvVoteAverage);
        tvVoteAverage.setText("Vote Average : " + MovieData.getVoteAverage());

        tvVote = findViewById(R.id.tvVote);
        tvVote.setText("Vote : " + MovieData.getVote());
    }
}