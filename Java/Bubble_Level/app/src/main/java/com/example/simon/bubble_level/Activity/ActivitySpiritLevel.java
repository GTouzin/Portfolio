package com.example.simon.bubble_level.Activity;

import android.content.Intent;
import android.hardware.SensorManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.OrientationEventListener;
import android.view.View;

import com.example.simon.bubble_level.Drawing.Drawing_spirit_level;
import com.example.simon.bubble_level.R;

public class ActivitySpiritLevel extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener{

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    OrientationEventListener mOrientationListener;
    String DEBUG_TAG = "Test";
    Drawing_spirit_level MyDraw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_spirit_level);

        MyDraw= (Drawing_spirit_level) findViewById(R.id.custView2);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_nav_drawer);
        drawerFragment.setUp(R.id.fragment_nav_drawer, (DrawerLayout) findViewById(R.id.drawer_layout_spirit), mToolbar);
        drawerFragment.setDrawerListener(this);

        mOrientationListener = new OrientationEventListener(this,
                SensorManager.SENSOR_DELAY_NORMAL) {

            @Override
            public void onOrientationChanged(int orientation) {
                Log.v(DEBUG_TAG,
                        "Orientation changed to " + orientation);
                MyDraw.portrait=orientation;
            }

        };

        if (mOrientationListener.canDetectOrientation() == true) {
            Log.v(DEBUG_TAG, "Can detect orientation");
            mOrientationListener.enable();
        } else {
            Log.v(DEBUG_TAG, "Cannot detect orientation");
            mOrientationListener.disable();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mOrientationListener.disable();
    }


    @Override
    public void onDrawerItemSelected(View view, int position) {
        //Affiche l'élément choisi dans le drawer
        displayView(position);
    }

    private void displayView(int position) {

        String title = getString(R.string.app_name);
        Intent myIntent;
        switch (position) {
            case 0:
                myIntent= new Intent(this, MainActivity.class);
                startActivity(myIntent);
                title = getString(R.string.titre_accueil);
                break;
            case 1:
                myIntent= new Intent(this, ActivitySpiritLevel.class);
                startActivity(myIntent);
                title = getString(R.string.titre_niveau);
                break;
            case 2:
                myIntent= new Intent(this, Activity_bubble.class);
                startActivity(myIntent);
                title = getString(R.string.titre_nivelle);
                break;
            case 3:
                myIntent= new Intent(this, Activity_stud.class);
                startActivity(myIntent);
                title = getString(R.string.titre_montant);
                break;
            case 4:
                myIntent= new Intent(this, Activity_Fils.class);
                startActivity(myIntent);
                title = getString(R.string.titre_montant);
                break;
            case 5:
                myIntent= new Intent(this, Activity_Tuyaux.class);
                startActivity(myIntent);
                title = getString(R.string.titre_tuyaux);
                break;
            case 6:
                myIntent= new Intent(this, Activity_inclinaison.class);
                startActivity(myIntent);
                title = getString(R.string.titre_angle);
                break;

            default:
                break;
        }
        // set the toolbar title
        getSupportActionBar().setTitle(title);

    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bar_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent myIntent;
        switch (item.getItemId()) {
            case R.id.action_list:
                return true;
            case R.id.item_main:
                myIntent= new Intent(this, MainActivity.class);
                startActivity(myIntent);
                return true;
            case R.id.item_niveau:
                myIntent= new Intent(this, ActivitySpiritLevel.class);
                startActivity(myIntent);
                return true;
            case R.id.item_nivelle:
                myIntent= new Intent(this, Activity_bubble.class);
                startActivity(myIntent);
                return true;
            case R.id.item_montant:
                myIntent= new Intent(this, Activity_stud.class);
                startActivity(myIntent);
                return true;
            case R.id.item_fil:
                myIntent= new Intent(this, Activity_Fils.class);
                startActivity(myIntent);
                return true;
            case R.id.item_tuyaux:
                myIntent= new Intent(this, Activity_Tuyaux.class);
                startActivity(myIntent);
                return true;
            case R.id.item_angle:
                myIntent= new Intent(this, Activity_inclinaison.class);
                startActivity(myIntent);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
    */
}
