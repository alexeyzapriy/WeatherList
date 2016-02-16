package com.example.roman.weatherlist;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String[] dataSet = { "Qaawws", "Bkxcsf", "sdfksskvuksjv fvjufvidfub", "sdfsdfs", "dfsdfsdf", "sfhsbfbsv", "sdfhsfhsmhfdhm",
            "111111111", "2222222222", "3333333333", "444444444", "55555555555", "6666666666", "77777777777777",
            "888888", "999999999", "1010101010110", "111111111111", "12121212121212", "13131313131313131313",
            "14141414141414", "151515151515", "16161616161616", "17171771717171", "1818181818818181" };

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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        FrameLayout main_container = (FrameLayout) findViewById(R.id.main_container);
        main_container.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);
        int id = item.getItemId();

        if (id == R.id.your_city) {
            RecyclerView list = (RecyclerView) inflater.inflate(R.layout.recycler_view, null);
            LinearLayoutManager manager = new LinearLayoutManager(this);
            list.setLayoutManager(manager);
            CardsRecyclerAdapter adapter = new CardsRecyclerAdapter(this, dataSet);
            list.setAdapter(adapter);
            main_container.addView(list);
        } else if (id == R.id.manage_cities) {

        } else if (id == R.id.settings) {

        } else if (id == R.id.info) {
            RelativeLayout rl = (RelativeLayout)inflater.inflate(R.layout.info_view, null);
            main_container.addView(rl);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
