package ru.rambler.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import ru.rambler.rate.Configuration;
import ru.rambler.rate.RamblerRate;
import ru.rambler.rate.Util;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Util.resetRamblerRate(this);
        Configuration configuration = Configuration.newInstance(this)
                .setDelayDays(0).setTitle("Оцените приложение")
                .setMessage("Если вы кайфуете от нашего приложения, поставьте ему пять звездочек ;)");

        RamblerRate.initialize(configuration);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RamblerRate.startForResult(MainActivity.this);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        RamblerRate.onActivityResult(requestCode, resultCode, data, new RamblerRate.Callback() {
            @Override
            public void rated(float stars) {
                Toast.makeText(MainActivity.this, "Stars: " + stars, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void delayed() {
                Toast.makeText(MainActivity.this, "Delayed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void canceled() {
                Toast.makeText(MainActivity.this, "Canceled", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
