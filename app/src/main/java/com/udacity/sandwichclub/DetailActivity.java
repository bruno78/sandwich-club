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

import org.w3c.dom.Text;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

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

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        TextView origin = findViewById(R.id.origin_tv);
        origin.setText(sandwich.getPlaceOfOrigin());

        TextView description = findViewById(R.id.description_tv);
        description.setText(sandwich.getDescription());

//        TextView ingredients = findViewById(R.id.ingredients_tv);
//        ingredients.setText(stringifyList(sandwich.getIngredients()));
//
//        TextView alsoKnownAs = findViewById(R.id.also_known_tv);
//        alsoKnownAs.setText(stringifyList(sandwich.getAlsoKnownAs()));

    }

    private String stringifyList(List<String> list) {
        StringBuilder result = new StringBuilder("");

        if(list.size() > 0 || list != null) {
            for (int i = 0; i < list.size(); i++) {
                result.append(list.get(i) + " ");
            }
        }

        return list.toString().trim();
    }
}
