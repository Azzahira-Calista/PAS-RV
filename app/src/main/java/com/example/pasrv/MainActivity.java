package com.example.pasrv;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterRC.AdapterListener{

    RecyclerView rvList;
    ArrayList<Model> listMovie;
    public AdapterRC adapterList;

    public void getAPIOnline(){
        ProgressBar pbLoadBar = findViewById(R.id.pbLoadBar);

        String url = "https://api.themoviedb.org/3/movie/popular?api_key=a73f31bb3aff541ff59ebf91e4108ce7";
        AndroidNetworking.get(url)
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        pbLoadBar.setVisibility(View.GONE);

                        try {
                            JSONArray jsonArrayMovie = jsonObject.getJSONArray("results");

                            for (int i = 0; i < jsonArrayMovie.length(); i++) {
                                Model myList = new Model();
                                JSONObject DataMovie = jsonArrayMovie.getJSONObject(i);

                                myList.setJudul(DataMovie.getString("original_title"));
                                myList.setSinopsis(DataMovie.getString("overview"));
                                myList.setPopularity(DataMovie.getString("popularity"));
                                myList.setReleaseDate(DataMovie.getString("release_date"));
                                myList.setVoteAverage(DataMovie.getString("vote_average"));
                                myList.setVote(DataMovie.getString("vote_count"));
                                myList.setPoster(DataMovie.getString("poster_path"));

                                listMovie.add(myList);
                            }


                                adapterList = new AdapterRC(getApplicationContext(), listMovie, MainActivity.this);
                                rvList = findViewById(R.id.rvList);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                rvList.setHasFixedSize(true);
                                rvList.setLayoutManager(mLayoutManager);
                                rvList.setAdapter(adapterList);


                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }


                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("error", "onError: " + anError.toString());
                    }
                });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.logOut) {
            // Handle search action
            logoutUser();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logoutUser() {
        // Clear user session or perform any other necessary logout actions

        // Example: Clearing user session using SharedPreferences
        SharedPreferences preferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();

        Intent loginIntent = new Intent(MainActivity.this, LoginPage.class);
        startActivity(loginIntent);
        finish();
        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listMovie = new ArrayList<>();
        getAPIOnline();
    }

    @Override
    public void onMovieSelected(Model myList) {
        Intent detail = new Intent(MainActivity.this, DetailPage.class);
        detail.putExtra("DataMovie", myList);
        startActivity(detail);
    }

    @Override
    public void onDataLongClicked(Model myList) {
        showDeleteMenu(myList);
    }

    //buat nge apus
    private void showDeleteMenu(Model myMovie) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Item");
        builder.setMessage("Are you sure you want to delete this item?");

        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Lakukan operasi penghapusan item di sini
                deleteItem(myMovie);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteItem(Model mySport) {
        listMovie.remove(mySport);
        adapterList.notifyDataSetChanged();
    }

}