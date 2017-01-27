package com.example.allan.androidweather;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.awareness.state.Weather;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

public class AjouterVille extends AppCompatActivity {

    Intent result;
    public final static String NOMVILLE = "com.example.allan.androidweather.nomVille";
    public final static String LONGITUDE = "com.example.allan.androidweather.longitude";
    public final static String LATITUDE = "com.example.allan.androidweather.latitude";
    public final static String TEMP_ACTUELLE = "com.example.allan.androidweather.tempActuelle";
    public final static String TEMP_MAX = "com.example.allan.androidweather.tempMax";
    public final static String TEMP_MIN = "com.example.allan.androidweather.tempMin";
    public final static String DESCRIPTION = "com.example.allan.androidweather.description";
    public final static String ICONE = "com.example.allan.androidweather.icone";
    EditText nom;
    Meteo meteo = null;
    String nomVille;
    private MyAsyncTask mTask;
    private static final String TAG = AjouterVille.class.getSimpleName();
    private static final String YOUR_API_KEY = "f4247bbc67446f6ab4d03f2adfff3383";
    private final String formatUsed = JSON_FORMAT;

    private static final String XML_FORMAT = "xml";
    private static final String JSON_FORMAT = "json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ajouter_ville);

        Button ajouterVille = (Button) findViewById(R.id.ajouterVille);
        ajouterVille.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                nom = (EditText) findViewById(R.id.nom);
                nomVille = nom.getText().toString();
                result = new Intent();
                startDownload();

                result.putExtra(MainActivity.NOMVILLE, nomVille);
                /*setResult(RESULT_OK, result);
                finish();*/
            }
        });



    }

    public void startDownload() {
        if(isConnectionAvailable()) {
            mTask = new MyAsyncTask(this);
            mTask.execute(buildURL(nomVille));
        } else {
            Toast.makeText(this, "No connection available. Request canceled", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isConnectionAvailable() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        return (networkInfo != null && networkInfo.isConnected());
    }

    public void responseReceived(String response) {
        Log.i(TAG, "Response: " + response);


        if(formatUsed.equals(JSON_FORMAT)) {
            meteo = parseJsonData(response);
            // ecrire ici
            result.putExtra(MainActivity.LATITUDE,  meteo.getLatitude());
            result.putExtra(MainActivity.LONGITUDE,  meteo.getLongitude());
            result.putExtra(MainActivity.TEMP_ACTUELLE,  meteo.getTemperatureActuelle());
            result.putExtra(MainActivity.TEMP_MAX,  meteo.getTemperatureMax());
            result.putExtra(MainActivity.TEMP_MIN,  meteo.getTemparatureMinimum());
            result.putExtra(MainActivity.DESCRIPTION,  meteo.getDescription());
            result.putExtra(MainActivity.ICONE,  meteo.getIcone());
            setResult(RESULT_OK, result);
            finish();

        } else if(formatUsed.equals(XML_FORMAT)) {
            meteo = parseXmlData(response);
        }

        Toast.makeText(this, "Ville enregistré", Toast.LENGTH_SHORT).show();
    }

    private URL buildURL(String frenchCity) {
        final String BASE_URL =
                "http://api.openweathermap.org/data/2.5/weather?";
        final String QUERY_PARAM = "q";
        final String FORMAT_PARAM = "mode";
        final String UNITS_PARAM = "units";
        final String APP_ID = "APPID";

        String format = formatUsed; //Either "xml" or "json"
        String units = "metric";

        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAM, frenchCity + ",fr")
                .appendQueryParameter(FORMAT_PARAM, format)
                .appendQueryParameter(UNITS_PARAM, units)
                .appendQueryParameter(APP_ID, YOUR_API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(uri.toString());
            Log.i(TAG, "Request URL: " + uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e(TAG, "Malformed URL");
        }

        return url;
    }

    private Meteo parseJsonData(String jsonFeed) {
        JsonParser parser = new JsonParser();
        return parser.parse(jsonFeed);
    }

    private Meteo parseXmlData(String xmlFeed) {
        Meteo meteo = null;
        XmlParser parser = new XmlParser();

        try {
            InputStream stream = new ByteArrayInputStream(xmlFeed.getBytes("UTF-8"));
            meteo = parser.parse(stream);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return meteo;
    }

}
