package com.example.allan.androidweather;
import android.os.Parcelable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;

import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements VilleListener  {

    private List<Ville> mVilles = new ArrayList<>();
    public final static String NOMVILLE = "com.example.allan.androidweather.nomVille";
    public final static String LONGITUDE = "com.example.allan.androidweather.longitude";
    public final static String LATITUDE = "com.example.allan.androidweather.latitude";
    public final static String TEMP_ACTUELLE = "com.example.allan.androidweather.tempActuelle";
    public final static String TEMP_MAX = "com.example.allan.androidweather.tempMax";
    public final static String TEMP_MIN = "com.example.allan.androidweather.tempMin";
    public final static String DESCRIPTION = "com.example.allan.androidweather.description";
    public final static String ICONE = "com.example.allan.androidweather.icone";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Ville angers;
        Meteo meteo = new Meteo(10,10,10,10,10,"meteoAngers","10n");
        angers = new Ville("angers",meteo);
        mVilles.add(angers);
        setContentView(R.layout.activity_main);

        Button boutonAjouter = (Button) findViewById(R.id.ajouterVille);
        boutonAjouter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AjouterVille.class);
                startActivityForResult(intent,0);
            }
        });

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        // Check whether the Activity is using the single panel or multi panels version
        if (findViewById(R.id.fragment_container) != null){

            // If we are being restored from a previous state, we should return in order to avoid overlapping Fragments
            if (savedInstanceState != null){
                return;
            }

            // Create a ContactsListFragment
            VilleListFragment listFragment = new VilleListFragment();
            listFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, listFragment)
                    .commit();
        } else {
            villeOnclick(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public List<Ville> getVilles() {
        return mVilles;
    }

    @Override
    public void villeOnclick(int indexVille) {
        VilleDetailsFragment detailsFragment = (VilleDetailsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.details_fragment);

        if (detailsFragment != null){
            detailsFragment.setVilleDetails(indexVille);
        } else {
            VilleDetailsFragment newDetailsFragment = new VilleDetailsFragment();
            Bundle args = new Bundle();

            args.putInt(VilleDetailsFragment.ITEM_INDEX, indexVille);
            newDetailsFragment.setArguments(args);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            // Replaces the current fragment and add the transaction to the backStack so the User can navigate back
            fragmentTransaction.replace(R.id.fragment_container, newDetailsFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // On vérifie tout d'abord à quel intent on fait référence ici à l'aide de notre identifiant
        if (requestCode == 0) {
            // On vérifie aussi que l'opération s'est bien déroulée
            if (resultCode == RESULT_OK) {
                double latitude = data.getDoubleExtra(LATITUDE,0);
                Log.i("Lat", "LATITUDE: " + latitude);

                Meteo meteo = new Meteo(
                        data.getDoubleExtra(LATITUDE,0),
                        data.getDoubleExtra(LONGITUDE,0),
                        data.getDoubleExtra(TEMP_ACTUELLE,0),
                        data.getDoubleExtra(TEMP_MAX,0),
                        data.getDoubleExtra(TEMP_MIN,0),
                        data.getStringExtra(DESCRIPTION),
                        data.getStringExtra(ICONE));

                String desc = data.getStringExtra(DESCRIPTION);
                Log.i("DESC", "description: " + desc);

                String ic = data.getStringExtra(ICONE);
                Log.i("IC", "icone: " + ic);

                Log.i("METEO", "Meteo: " + meteo.toString());
                Ville ville = new Ville(data.getStringExtra(NOMVILLE),meteo);
                mVilles.add(ville);
            }
        }
    }
}
