package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static final String Object_Name = "name";
    public static final String Main_Name = "mainName";
    public static final String Also_Known_As = "alsoKnownAs";
    public static final String PlaceOfOrigin = "placeOfOrigin";
    public static final String Description = "description";
    public static final String Image = "image";
    public static final String Ingredients = "ingredients";
    public static ArrayList<String> knownAsList;
    public static ArrayList<String> ingerdientsList;
    public static Sandwich sandwich;



    public static Sandwich parseSandwichJson(String json) throws JSONException {

        try {
            knownAsList = new ArrayList<String>();
            ingerdientsList = new ArrayList<String>();
            JSONObject jsonObject = new JSONObject(json);
            JSONObject JObject = jsonObject.getJSONObject(Object_Name);
            String main_name = JObject.getString(Main_Name);
            JSONArray alsoKnownAs = (JSONArray) JObject.get(Also_Known_As);
            String placeOForigin = jsonObject.getString(PlaceOfOrigin);
            String description = jsonObject.getString(Description);
            String image =  jsonObject.getString(Image);
            JSONArray ingerdients = (JSONArray) jsonObject.get(Ingredients);
            getList(alsoKnownAs, knownAsList);
            getList(ingerdients,ingerdientsList);
            sandwich = new Sandwich(main_name, knownAsList, placeOForigin, description, image, ingerdientsList);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sandwich;
    }


    public  static ArrayList getList(JSONArray jsonArray, ArrayList list) throws JSONException {
        if (jsonArray != null) {
            for (int j = 0; j < jsonArray.length(); j++) {
                list.add(jsonArray.getString(j));
            }
        }
        return list;

    }
}

