package com.example.simon.bubble_level.Activity;

import android.support.v4.widget.DrawerLayout;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.simon.bubble_level.R;


public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener{

    private static String TAG = MainActivity.class.getSimpleName();
    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_nav_drawer);
        drawerFragment.setUp(R.id.fragment_nav_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        // Affichage du premier item du drawer au lancement de l'app
        //displayView(0);
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate le menu et affiche les éléments sur l'action bar
        getMenuInflater().inflate(R.menu.bar_button, menu);
        return true;
    }


        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            //Gère les clicks sur l'action bar et affiche les activitées sélectionnées par l'utilisateur
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

    public void startBubbleLevel(View v)
    {
        Intent myIntent= new Intent(this, Activity_bubble.class);
        startActivity(myIntent);
    }

    public void startSpiritLevel(View v)
    {
        Intent myIntent= new Intent(this, ActivitySpiritLevel.class);
        startActivity(myIntent);
    }

    public void startStudDetector(View v)
    {
        Intent myIntent= new Intent(this, Activity_stud.class);
        startActivity(myIntent);
    }

    public void startInclinaison(View v)
    {
        Intent myIntent= new Intent(this, Activity_inclinaison.class);
        startActivity(myIntent);
    }

    public void startFils(View v)
    {
        Intent myIntent= new Intent(this, Activity_Fils.class);
        startActivity(myIntent);
    }

    public void startTuyau(View v)
    {
        Intent myIntent= new Intent(this, Activity_Tuyaux.class);
        startActivity(myIntent);
    }

    //TODO Arranger les icone poujr les différentes densité d'écrans

    //TODO Gérer les landscapes
}
