package com.example.roman.weatherlist;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.roman.weatherlist.models.WeatherModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public RequestQueue queue;
    private ArrayList<WeatherModel> data = new ArrayList<>();
    private FrameLayout mMainContainer;
    private Cities cities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        queue = Volley.newRequestQueue(this);

        String[] arrCities = {"lviv", "london", "rome", "kharkiv", "zmiiv"};
        cities = new Cities(this);
        cities.setMyCity("zmiiv");
        cities.setCities(arrCities);

        mMainContainer = (FrameLayout) findViewById(R.id.main_container);
        mMainContainer.removeAllViews();

        makeWeatherObj(cities.getCities());

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        mMainContainer = (FrameLayout) findViewById(R.id.main_container);
        mMainContainer.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);
        int id = item.getItemId();

        if (id == R.id.your_city) {
            data.clear();
            makeWeatherObj(cities.getMyCity());
        } else if (id == R.id.settings) {

        } else if (id == R.id.info) {
            RelativeLayout rl = (RelativeLayout) inflater.inflate(R.layout.info_view, null);
            mMainContainer.addView(rl);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void makeWeatherObj(final String[] cities) {
        final Gson gson = new GsonBuilder().serializeNulls().create();
        for (int i = 0; i < cities.length; i++) {
            String url = String.format(Consts.WEATHER_SERVICE_URL, cities[i]);
            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.i("Weather Response", response);
                    try {
                        WeatherModel weather = gson.fromJson(response, WeatherModel.class);
                        data.add(weather);
                        if (data.size() == cities.length) onDataReceived();
                    } catch (Exception e) {
                        Log.e("SimpleWeather", "One or more fields not found in the JSON data");
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });

            MySingleton.getInstance(this).addToRequestQueue(request);
        }

    }

    private void makeWeatherObj(final String city) {
        String url = String.format(Consts.WEATHER_SERVICE_URL, city);
        final Gson gson = new GsonBuilder().serializeNulls().create();

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i("Weather Response", response);
                try {
                    WeatherModel weather = gson.fromJson(response, WeatherModel.class);
                    data.add(weather);
                    onDataReceived();
                } catch (Exception e) {
                    Log.e("SimpleWeather", "One or more fields not found in the JSON data");
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        MySingleton.getInstance(this).addToRequestQueue(request);
    }

    private void onDataReceived() {
        RecyclerView list = (RecyclerView) LayoutInflater.from(this).inflate(R.layout.recycler_view, null);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        list.setLayoutManager(manager);
        CardsRecyclerAdapter adapter = new CardsRecyclerAdapter(this, data);
        list.setAdapter(adapter);
        mMainContainer.addView(list);
    }
}
