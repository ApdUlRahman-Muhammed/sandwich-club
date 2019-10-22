package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    public static Sandwich sandwich = null;
    TextView mainNameIV;
    ImageView ingredientsIv;
    TextView origin_tv;
    TextView also_known_tv;
    TextView ingredients_tv;
    TextView description_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ingredientsIv = findViewById(R.id.image_iv);
        mainNameIV=(TextView)findViewById(R.id.MainName);
        origin_tv = (TextView) findViewById(R.id.origin_tv);
        also_known_tv = (TextView) findViewById(R.id.OName);
        ingredients_tv = (TextView) findViewById(R.id.ingredients);
        description_tv = (TextView) findViewById(R.id.description);

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
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
            Log.v("SandwitchMainName_log2", sandwich.getMainName());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

populateUI();
//        populateUI();
//        Picasso.with(this)
//                .load(sandwich.getImage())
//                .into(ingredientsIv);
//
//        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {


        origin_tv.setText("- " + sandwich.getMainName());
        description_tv.setText(sandwich.getDescription());
        Log.v("sandwichImage", sandwich.getImage());
        Picasso.with(this).load(sandwich.getImage()).placeholder(R.drawable.placeholder).into(ingredientsIv);
        Log.v("sandwichImage2", sandwich.getImage());
        if (sandwich.getPlaceOfOrigin() != null) {
            origin_tv.setText(sandwich.getPlaceOfOrigin());
        }
        ArrayList ingredients = (ArrayList) sandwich.getIngredients();
        if (ingredients != null) {
            for (int i = 0; i < ingredients.size(); i++) {
                ingredients_tv.append("- " + ingredients.get(i).toString());
                ingredients_tv.append("\n" + '\n');
            }
        }
        ArrayList otherNames = (ArrayList) sandwich.getAlsoKnownAs();
        if (otherNames != null) {
            for (int i = 0; i < otherNames.size(); i++)
                also_known_tv.append("- " + otherNames.get(i).toString());
            also_known_tv.append("\n" + '\n');
        }
    }
}
