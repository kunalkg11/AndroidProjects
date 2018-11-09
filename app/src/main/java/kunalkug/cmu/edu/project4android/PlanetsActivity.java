
/**
 * Author: Kunal Kumar Gupta
 * Last Modified: April 7, 2018
 *
 * This is the Planets Activity class for Star Wars Application
 *
 */
package kunalkug.cmu.edu.project4android;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class PlanetsActivity extends AppCompatActivity {

    //search term entered by user
    String searchTerm = "";

    //launches the activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planets);

        /*
         * Find the "submit" button, and add a listener to it
         */
        Button submitButton = (Button) findViewById(R.id.planet_submit);

        // Add a listener to the submit button
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View viewParam) {
                searchTerm = ((EditText) findViewById(R.id.searchTerm)).getText().toString();
                System.out.println(searchTerm);
                new PlanetsActivity.StarWarsPlanetSearch().execute(searchTerm);
            }
        });
    }

    //searches for planet information
    private class StarWarsPlanetSearch extends AsyncTask<String, Void, JSONObject> {

        //doInBackground
        protected JSONObject doInBackground(String... urls) {
            JSONObject result = null;
            try {
                result = search(searchTerm);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return result;
        }

        //onPostExecute
        protected void onPostExecute(JSONObject jsonObj) {
            try {

                //getting the planet's name
                String planetName = (String) jsonObj.get("planetName");

                //getting the planet's orbital period
                String planetOrbitalPeriod = (String) jsonObj.get("planetOrbitalPeriod");

                //getting the planet's population
                String planetPopulation = (String) jsonObj.get("planetPopulation");

                TextView planet = findViewById(R.id.planet_name);
                planet.setText("\nFollowing are some facts about planet " + planetName);
                planet.setVisibility(View.VISIBLE);

                TextView population = findViewById(R.id.planet_population);
                population.setText("\nPopulation of the planet: " + planetPopulation + "\n");
                population.setVisibility(View.VISIBLE);

                TextView orbitalPeriod = findViewById(R.id.orbital_period);
                orbitalPeriod.setText("Orbital Period: " + planetOrbitalPeriod + "\n");
                orbitalPeriod.setVisibility(View.VISIBLE);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }

        }

        //method to talk to the Heroku web service
        private JSONObject search(String searchTerm) throws JSONException {
            String connectURL = "https://damp-taiga-15825.herokuapp.com/MongoDBServer?searchType=planets&searchWord=" + searchTerm;
            URL url = null;
            try {
                url = new URL(connectURL);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            HttpURLConnection con = null;
            try {
                con = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // optional default is GET
            try {
                con.setRequestMethod("GET");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }

            //add request header
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            int responseCode = 0;
            try{
                responseCode = con.getResponseCode();
            } catch (IOException e) {
                e.printStackTrace();
            }
            BufferedReader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } catch (IOException e){
                e.printStackTrace();
            }
            String inputLine;
            String resultJSON = "";
            try {
                while ((inputLine = in.readLine()) != null) {
                    resultJSON += inputLine;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            //creating a JSON object from the received data from the Heroku service
            JSONObject jsonObj = new JSONObject(resultJSON);

            //return result
            return jsonObj;
        }
    }
}
