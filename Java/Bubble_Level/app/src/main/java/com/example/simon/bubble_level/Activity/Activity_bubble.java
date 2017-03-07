package com.example.simon.bubble_level.Activity;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.simon.bubble_level.Drawing.Drawing_bubble;
import com.example.simon.bubble_level.R;

public class Activity_bubble extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener{

    private static String TAG = MainActivity.class.getSimpleName();
    private Toolbar mToolbar;
    Drawing_bubble MyDraw;
    private FragmentDrawer drawerFragment;

    boolean test=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_bubble);

        MyDraw = (Drawing_bubble) findViewById(R.id.custView);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_nav_drawer);
        drawerFragment.setUp(R.id.fragment_nav_drawer, (DrawerLayout) findViewById(R.id.drawer_layout_bubble), mToolbar);
        drawerFragment.setDrawerListener(this);
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
    //TODO enlever le code du menu dans l'action bar
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bar_button, menu);
        test=true;
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

    @Override
    protected  void onPause(){
        super.onPause();

        if(MyDraw!=null) {
            MyDraw.releaseSensor();
        }
    }

    @Override
    protected void onResume(){
      super.onResume();
//TODO tester dans la variable test avec une tablette: si fonctionne ajouter à toute les classes

        if(MyDraw!=null && test){
            MyDraw.registerListenerSensor();
        }
    }


}
