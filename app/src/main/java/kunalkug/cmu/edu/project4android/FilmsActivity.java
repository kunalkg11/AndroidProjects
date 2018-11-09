
/**
 * Author: Kunal Kumar Gupta
 * Last Modified: April 7, 2018
 *
 * This is the Films Activity class for Star Wars Application
 *
 */
package kunalkug.cmu.edu.project4android;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class FilmsActivity extends AppCompatActivity {

    //search term to be entered by user
    String searchTerm = "";

    //launches the activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_films);

        /*
         * Find the "submit" button, and add a listener to it
         */
        Button submitButton = (Button) findViewById(R.id.film_submit);

        // Add a listener to the submit button
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View viewParam) {
                searchTerm = ((EditText) findViewById(R.id.searchTerm)).getText().toString();
                new StarWarsFilmSearch().execute(searchTerm);
            }
        });
    }

    //method to search information about the film using AsyncTask to handle UI and business logic separately
    private class StarWarsFilmSearch extends AsyncTask<String, Void, JSONObject> {

        //doInBackground method
        protected JSONObject doInBackground(String... urls) {
            JSONObject result = null;
            try {
                result = search(searchTerm);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return result;
        }

        //onPostExecute method
        protected void onPostExecute(JSONObject jsonObj) {

            try {

                //getting the film title
                String filmTitle = (String) jsonObj.get("filmTitle");

                //getting the film's episode
                int episode_id = (Integer) jsonObj.get("episode");

                //getting the film's opening crawl
                String opening_crawl = (String) jsonObj.get("opening_crawl");

                //getting the film's director
                String filmDirector = (String) jsonObj.get("filmDirector");

                //getting the film's release date
                String filmReleaseDate = (String) jsonObj.get("filmReleaseDate");

                //setting title
                TextView title = findViewById(R.id.title);
                title.setText(filmTitle);
                title.setVisibility(View.VISIBLE);

                //setting episode
                TextView episode = findViewById(R.id.episode);
                String eps = String.valueOf(episode_id);
                episode.setText("Episode: " + eps + "\n");
                episode.setVisibility(View.VISIBLE);

                //setting crawl
                TextView openingcrawl = findViewById(R.id.opening_crawl);
                openingcrawl.setText(opening_crawl + "\n");
                openingcrawl.setVisibility(View.VISIBLE);

                //setting director
                TextView film_Director = findViewById(R.id.director);
                film_Director.setText("Film Director" + filmDirector);
                film_Director.setVisibility(View.VISIBLE);

                //setting release date
                TextView filmRelease = findViewById(R.id.releaseDate);
                filmRelease.setText("Release Date" + filmReleaseDate);
                filmRelease.setVisibility(View.VISIBLE);

                //setting images for posters
                ImageView pictureView = (ImageView)findViewById(R.id.film_poster);

                if (filmTitle.equalsIgnoreCase("The Phantom Menace")){
                    pictureView.setImageResource(R.drawable.star_wars_episode_one_the_phantom_menace);
                }
                else if (filmTitle.contains("Revenge of the Sith")){
                    pictureView.setImageResource(R.drawable.star_wars_episode_three_revenge_of_the_sith);
                }
                else if (filmTitle.equalsIgnoreCase("Attack of the Clones")) {
                    pictureView.setImageResource(R.drawable.star_wars_episode_two_attack_of_the_clones);
                }
                else if (filmTitle.equalsIgnoreCase("A New Hope")) {
                    pictureView.setImageResource(R.drawable.a_new_hope);
                }
                else if (filmTitle.contains("The Empire Strikes Back")) {
                    pictureView.setImageResource(R.drawable.empire_strikes_back);
                }
                else if (filmTitle.contains("The Force Awakens")){
                    pictureView.setImageResource(R.drawable.star_wars_force_awakens);
                }
                else if (filmTitle.equalsIgnoreCase("Return of the Jedi")) {
                    pictureView.setImageResource(R.drawable.return_of_the_jedi);
                }
                pictureView.setVisibility(View.VISIBLE);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //method that talks to the web service on Heroku to fetch information from Star Wars API
        private JSONObject search(String searchTerm) throws JSONException {

            //url for web service
            String connectURL = "https://damp-taiga-15825.herokuapp.com/MongoDBServer?searchType=films&searchWord=" + searchTerm;
            URL url = null;
            try {
                url = new URL(connectURL);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            HttpURLConnection con = null;
            try {
                //getting connection
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
            //get result from web service
            try {
                while ((inputLine = in.readLine()) != null) {
                    resultJSON += inputLine;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            //creating a JSON object from the received data from the Heroku service
            JSONObject jsonObj = new JSONObject(resultJSON);

            //return final object
            return jsonObj;
        }
    }
}
