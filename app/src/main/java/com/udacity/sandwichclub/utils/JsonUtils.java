package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = null;

        try {
            JSONObject jsonSandwich = new JSONObject(json);
            
            if(!jsonSandwich.has("name")) {
                throw new Exception("Key "name" is missing.");
            }
            JSONObject name = jsonSandwich .getJSONObject("name");
            String mainName = name.getString("mainName");
            
            if(!jsonSandwich.has("alsoKnownAs")) {
                throw new Exception("Key "alsoKnownAs" is missing.");
            }
            JSONArray alsoKnownAs = name.getJSONArray("alsoKnownAs");
            
            if(!jsonSandwich.has("placeOfOrigin")) {
                throw new Exception("Key "placeOfOrigin" is missing.");
            }
            String placeOfOrigin = jsonSandwich .getString("placeOfOrigin");
            
            if(!jsonSandwich.has("description")) {
                throw new Exception("Key "description" is missing.");
            }
            String description = jsonSandwich .getString("description");
            
            if(!jsonSandwich.has("image")) {
                throw new Exception("Image URL is missing.");
            }
            String imageUrl = jsonSandwich .getString("image");
            
            if(!jsonSandwich.has("ingredients")) {
                throw new Exception("Key "ingredients" is missing.");
            }
            JSONArray ingredients = jsonSandwich .getJSONArray("ingredients");

            //No exceptions were thrown, so I can create the sandwich object
            sandwich = new Sandwich();
            sandwich.setMainName(mainName);
            sandwich.setPlaceOfOrigin(placeOfOrigin);
            sandwich.setDescription(description);
            sandwich.setImage(imageUrl);

            if (alsoKnownAs.length() > 0) {
                List<String> alsoKnownAsList = new ArrayList<>();
                for(int i=0; i<alsoKnownAs.length(); i++) {
                    alsoKnownAsList.add(alsoKnownAs.getString(i));
                }
                sandwich.setAlsoKnownAs(alsoKnownAsList);
            }

            if (ingredients.length() > 0) {
                List<String> ingredientsList = new ArrayList<>();
                for(int i=0; i<ingredients.length(); i++) {
                    ingredientsList.add(ingredients.getString(i));
                }
                sandwich.setIngredients(ingredientsList);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sandwich;
    }
}
