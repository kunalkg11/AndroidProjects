
/**
 * Author: Kunal Kumar Gupta
 * Last Modified: April 7, 2018
 *
 * This is the People Activity class for Star Wars Application
 *
 */
package kunalkug.cmu.edu.project4android;

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

public class PeopleActivity extends AppCompatActivity {

    //search term to be entered by user
    String searchTerm = "";

    //launches this activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);

        /*
         * Find the "submit" button, and add a listener to it
         */
        Button submitButton = (Button) findViewById(R.id.people_submit);

        // Add a listener to the submit button
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View viewParam) {
                searchTerm = ((EditText) findViewById(R.id.searchTerm)).getText().toString();
                new StarWarsPeopleSearch().execute(searchTerm);
            }
        });
    }

    //searches the people from API
    private class StarWarsPeopleSearch extends AsyncTask<String, Void, JSONObject> {

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

                //getting the character's name
                String name = (String) jsonObj.get("characterName");

                //getting the character's homeworld
                String homeworld = (String) jsonObj.get("homeworld");

                //getting the films associated with the character
                JSONArray films = (JSONArray) jsonObj.get("films");

                TextView character = findViewById(R.id.character_name);
                character.setText("Character Name: " + name + "\n\n" + "Below are the movies in which this character is present!");
                character.setVisibility(View.VISIBLE);

                ImageView posterView1 = (ImageView)findViewById(R.id.poster1);
                ImageView posterView2 = (ImageView)findViewById(R.id.poster2);
                ImageView posterView3 = (ImageView)findViewById(R.id.poster3);
                ImageView posterView4 = (ImageView)findViewById(R.id.poster4);
                ImageView posterView5 = (ImageView)findViewById(R.id.poster5);
                ImageView posterView6 = (ImageView)findViewById(R.id.poster6);
                ImageView posterView7 = (ImageView)findViewById(R.id.poster7);

                for (int i = 0; i < films.length(); i++) {
                    String film = films.getString(i);
                    if (film.contains("1")) {
                        posterView1.setImageResource(R.drawable.star_wars_episode_one_the_phantom_menace);
                        posterView1.setVisibility(View.VISIBLE);
                    }
                    else if (film.contains("2")){
                        posterView2.setImageResource(R.drawable.star_wars_episode_two_attack_of_the_clones);
                        posterView2.setVisibility(View.VISIBLE);
                    }
                    else if (film.contains("3")){
                        posterView3.setImageResource(R.drawable.star_wars_episode_three_revenge_of_the_sith);
                        posterView3.setVisibility(View.VISIBLE);
                    }
                    else if (film.contains("4")){
                        posterView4.setImageResource(R.drawable.a_new_hope);
                        posterView4.setVisibility(View.VISIBLE);
                    }else if (film.contains("5")){
                        posterView5.setImageResource(R.drawable.empire_strikes_back);
                        posterView5.setVisibility(View.VISIBLE);
                    }else if (film.contains("6")){
                        posterView6.setImageResource(R.drawable.return_of_the_jedi);
                        posterView6.setVisibility(View.VISIBLE);
                    }else if (film.contains("7")){
                        posterView7.setImageResource(R.drawable.star_wars_force_awakens);
                        posterView7.setVisibility(View.VISIBLE);
                    }
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //method to talk to Heroku web service
        private JSONObject search(String searchTerm) throws JSONException {
            //url to web service
            String connectURL = "https://damp-taiga-15825.herokuapp.com/MongoDBServer?searchType=people&searchWord=" + searchTerm;
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

            //read result
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
