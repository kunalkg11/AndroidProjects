
/**
 * Author: Kunal Kumar Gupta
 * Last Modified: April 7, 2018
 *
 * This is the Main Activity class for Star Wars Application.
 * The application starts running from this class.
 *
 * References: The AndroidInterestingPicture lab has been utilised to create this project.
 * Also, references from class notes.
 *
 */
package kunalkug.cmu.edu.project4android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    //films button
    private ImageButton films;

    //people button
    private ImageButton people;

    //planets button
    private ImageButton planets;

    //starships button
    private ImageButton starships;

    //onCreate method is the starting point of the application
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //films activity
        films = (ImageButton)findViewById(R.id.imageButton_films);
        films.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFilmsActivity();
            }
        });

        //people activity
        people = (ImageButton)findViewById(R.id.imageButton_people);
        people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPeopleActivity();
            }
        });

        //planets activity
        planets = (ImageButton)findViewById(R.id.imageButton_planets);
        planets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPlanetsActivity();
            }
        });

        //startships activity
        starships = (ImageButton)findViewById(R.id.imageButton_starships);
        starships.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStarshipsActivity();
            }
        });
    }

    //starting films activity
    public void openFilmsActivity (){
        Intent filmsIntent = new Intent(this, FilmsActivity.class);
        startActivity(filmsIntent);
    }

    //starting people activity
    public void openPeopleActivity (){
        Intent peopleIntent = new Intent(this, PeopleActivity.class);
        startActivity(peopleIntent);
    }

    //starting planets activity
    public void openPlanetsActivity (){
        Intent planetsIntent = new Intent(this, PlanetsActivity.class);
        startActivity(planetsIntent);
    }

    //starting starships activity
    public void openStarshipsActivity (){
        Intent starshipsIntent = new Intent(this, StarshipsActivity.class);
        startActivity(starshipsIntent);
    }
}