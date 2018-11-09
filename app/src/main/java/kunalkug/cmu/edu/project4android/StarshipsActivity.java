
/**
 * Author: Kunal Kumar Gupta
 * Last Modified: April 7, 2018
 *
 * This is the Starship Activity class for Star Wars Application
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class StarshipsActivity extends AppCompatActivity {

    //search term to be entered by user
    String searchTerm = "";

    //launches acitivity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starships);

        /*
         * Find the "submit" button, and add a listener to it
         */
        Button submitButton = (Button) findViewById(R.id.starship_submit);

        // Add a listener to the submit button
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View viewParam) {
                searchTerm = ((EditText) findViewById(R.id.searchTerm)).getText().toString();
                System.out.println(searchTerm);
                new StarshipsActivity.StarWarsStarshipSearch().execute(searchTerm);
            }
        });
    }

    //searches for the starship
    private class StarWarsStarshipSearch extends AsyncTask<String, Void, JSONObject> {

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

                //getting the starship name
                String starshipName = (String) jsonObj.get("starshipName");

                //getting the starship's model
                String starshipModel = (String) jsonObj.get("starshipModel");

                TextView shipName = findViewById(R.id.starship_name);
                shipName.setText("Starship name: " + starshipName);
                shipName.setVisibility(View.VISIBLE);

                TextView shipModel = findViewById(R.id.starship_model);
                shipModel.setText("\nStarship model is: " + starshipModel + "\n\nBelow is a cool picture of the " + starshipName);
                shipModel.setVisibility(View.VISIBLE);

                ImageView pictureView = (ImageView)findViewById(R.id.starship_image);

                if (starshipName.contains("A-wing")) {
                    pictureView.setImageResource(R.drawable.a_wing);
                }
                else if (starshipName.contains("B-wing")) {
                    pictureView.setImageResource(R.drawable.b_wing);
                }
                else if (starshipName.contains("AA-9 Coruscant")) {
                    pictureView.setImageResource(R.drawable.aa_9_coruscant_freighter);
                }
                else if (starshipName.contains("Banking clan")) {
                    pictureView.setImageResource(R.drawable.banking_clan_frigate);
                }
                else if (starshipName.contains("Belbullab")) {
                    pictureView.setImageResource(R.drawable.belbullab_22_starfighter);
                }
                else if (starshipName.contains("CR90")) {
                    pictureView.setImageResource(R.drawable.cr90_corvette);
                }else if (starshipName.contains("Calamari")) {
                    pictureView.setImageResource(R.drawable.calamari_cruiser);
                }
                else if (starshipName.contains("Droid control")) {
                    pictureView.setImageResource(R.drawable.droid_control_ship);
                }
                else if (starshipName.contains("EF76")) {
                    pictureView.setImageResource(R.drawable.ef76_nebulon_b_escort_frigate);
                }
                else if (starshipName.contains("Executor")) {
                    pictureView.setImageResource(R.drawable.executor);
                }
                else if (starshipName.contains("H-type")) {
                    pictureView.setImageResource(R.drawable.h_type_nubian_yacht);
                }else if (starshipName.contains("Imperial")) {
                    pictureView.setImageResource(R.drawable.imperialshuttle);
                }
                else if (starshipName.contains("J-type diplomatic")) {
                    pictureView.setImageResource(R.drawable.j_type_diplomatic_barge);
                }else if (starshipName.contains("Jedi Interceptor")) {
                    pictureView.setImageResource(R.drawable.jedi_interceptor);
                }
                else if (starshipName.contains("Jedi Starfighter")) {
                    pictureView.setImageResource(R.drawable.jedi_starfighter);
                }else if (starshipName.contains("Millennium Falcon")) {
                    pictureView.setImageResource(R.drawable.millennium_falcon);
                }else if (starshipName.contains("Naboo fighter")) {
                    pictureView.setImageResource(R.drawable.naboo_fighter);
                }
                else if (starshipName.contains("Naboo star")) {
                    pictureView.setImageResource(R.drawable.naboo_star_skiff);
                }else if (starshipName.contains("Naboo Royal")) {
                    pictureView.setImageResource(R.drawable.naboo_royal_starship);
                }
                else if (starshipName.contains("Rebel Transport")) {
                    pictureView.setImageResource(R.drawable.rebel_transport);
                }else if (starshipName.contains("Republic Assault")) {
                    pictureView.setImageResource(R.drawable.republic_assault_ship);
                }else if (starshipName.contains("Republic Cruiser")) {
                    pictureView.setImageResource(R.drawable.republic_cruiser);
                }else if (starshipName.contains("Republic attack")) {
                    pictureView.setImageResource(R.drawable.republic_attack_cruiser);
                }
                else if (starshipName.contains("Scimitar")) {
                    pictureView.setImageResource(R.drawable.scimitar);
                }else if (starshipName.contains("Sentinel")) {
                    pictureView.setImageResource(R.drawable.sentinel);
                }else if (starshipName.contains("Slave")) {
                    pictureView.setImageResource(R.drawable.slave_1);
                }
                else if (starshipName.contains("Solar Sailer")) {
                    pictureView.setImageResource(R.drawable.solar_sailer);
                }else if (starshipName.contains("Star Destroyer")) {
                    pictureView.setImageResource(R.drawable.star_destroyer);
                }else if (starshipName.contains("T-70")) {
                    pictureView.setImageResource(R.drawable.t_70_x_wing_fighter);
                }else if (starshipName.contains("TIE")) {
                    pictureView.setImageResource(R.drawable.tie_advanced_x1);
                }
                else if (starshipName.contains("Theta")) {
                    pictureView.setImageResource(R.drawable.theta_class_t_2c_shuttle);
                }else if (starshipName.contains("Trade Federation")) {
                    pictureView.setImageResource(R.drawable.trade_federation_cruiser);
                }else if (starshipName.contains("V-wing")) {
                    pictureView.setImageResource(R.drawable.v_wing);
                }else if (starshipName.contains("X-wing")) {
                    pictureView.setImageResource(R.drawable.x_wing);
                }else if (starshipName.contains("Y wing")) {
                    pictureView.setImageResource(R.drawable.ywing);
                }else if (starshipName.contains("arc-170")) {
                    pictureView.setImageResource(R.drawable.arc170);
                }else if (starshipName.contains("Death Star")) {
                    pictureView.setImageResource(R.drawable.death_star);
                }
                pictureView.setVisibility(View.VISIBLE);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        //talks to Heroku web service to get results
        private JSONObject search(String searchTerm) throws JSONException {

            String connectURL = "https://damp-taiga-15825.herokuapp.com/MongoDBServer?searchType=starships&searchWord=" + searchTerm;
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
            try {
                responseCode = con.getResponseCode();
            } catch (IOException e) {
                e.printStackTrace();
            }

            BufferedReader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } catch (IOException e) {
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

            //return final result
            return jsonObj;
        }
    }
}
