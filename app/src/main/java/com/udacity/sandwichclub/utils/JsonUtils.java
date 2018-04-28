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

        Sandwich sandwich = new Sandwich();

        try {

            JSONObject sandwichJsonObject  = new JSONObject(json);
            JSONObject name = sandwichJsonObject .getJSONObject("name");

            sandwich.setMainName( name.getString("mainName"));
            JSONArray aka = name.getJSONArray("alsoKnownAs");
            sandwich.setAlsoKnownAs(jsonArrayToList(aka));

            sandwich.setPlaceOfOrigin( sandwichJsonObject.getString("placeOfOrigin"));
            sandwich.setDescription( sandwichJsonObject.getString("description"));
            sandwich.setImage( sandwichJsonObject.getString("image"));
            JSONArray ingredients = sandwichJsonObject.getJSONArray("ingredients");
            sandwich.setIngredients(jsonArrayToList(ingredients));

        }
        catch (JSONException e) {
            Log.e("JsonUtils", "Problem parsing the sandwich JSON results", e);
        }

        return sandwich;
    }

    /**
     *  Helper method to convert JSONArray to list
     * @param jsonArray to be converted
     * @return List of Strings
     * @throws JSONException
     */
    private static List<String> jsonArrayToList(JSONArray jsonArray) throws JSONException {

        List<String> list = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getString(i));
        }

        return list;
    }
}
