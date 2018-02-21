package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        TextView also_known_tv = findViewById(R.id.also_known_tv);
        TextView origin_tv = findViewById(R.id.origin_tv);
        TextView description_tv = findViewById(R.id.description_tv);
        TextView ingredients_tv = findViewById(R.id.ingredients_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());                               //Set text for MainName attribute

        List<String> alsoKnownAs = sandwich.getAlsoKnownAs();
        /*
        Convert the List type into one single String with elements separated by a comma and a space (", ")
        Also check if the List alsoKnownAs is null or not before the conversion
        */
        String alsoKnownAsString = " ";
        if (alsoKnownAs != null) {
            for (int i = 0; i < alsoKnownAs.size(); i++) {
                alsoKnownAsString += alsoKnownAs.get(i);
                if (i != alsoKnownAs.size() - 1) {
                    alsoKnownAsString += ", ";
                }
            }
        }
        //Now set the final String into the TextView element
        also_known_tv.setText(alsoKnownAsString);                       //Set text for alsoKnownAs attribute

        String placeOfOriginString = " " + sandwich.getPlaceOfOrigin();
        origin_tv.setText(placeOfOriginString);                         //Set text for placeOfOrigin attribute

        String descriptionString = " " + sandwich.getDescription();
        description_tv.setText(descriptionString);                      //Set text for Description attribute

        List<String> ingredients = sandwich.getIngredients();
        /*
        Convert the List type into one single String with elements separated by a comma and a space (", ")
        Also check if the List Ingredients is null or not before the conversion
        */
        String ingredientsString = "";
        if (ingredients != null) {
            for (int i = 0; i < ingredients.size(); i++) {
                ingredientsString += "\u2022 ";
                ingredientsString += ingredients.get(i);
                ingredientsString += "\n";
            }
        }
        //Now set the final String into the TextView element
        ingredients_tv.setText(ingredientsString);                       //Set text for Ingredients attribute
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

    }
}
