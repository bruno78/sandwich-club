package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

        TextView mainNameView = findViewById(R.id.main_name_tv);
        mainNameView.setText(sandwich.getMainName());

        TextView originView = findViewById(R.id.origin_tv);
        originView.setText(sandwich.getPlaceOfOrigin());

        TextView descriptionView = findViewById(R.id.description_tv);
        descriptionView.setText(sandwich.getDescription());

        TextView ingredientsView = findViewById(R.id.ingredients_tv);
        List<String> ingredientsList = sandwich.getIngredients();
        if(ingredientsList.size() == 0) {
            ingredientsView.setVisibility(View.GONE);
        }
        else {
            ingredientsView.setText(stringifyList(ingredientsList));
        }


        TextView alsoKnownAsView = findViewById(R.id.also_known_tv);
        List<String> alsoknownAsList = sandwich.getAlsoKnownAs();
        if(alsoknownAsList.size() == 0) {
            alsoKnownAsView.setVisibility(View.GONE);
        }
        else {
            alsoKnownAsView.setText(stringifyList(alsoknownAsList));
        }


    }

    private String stringifyList(List<String> list) {
        StringBuilder result = new StringBuilder("");

        if(list.size() == 1) {
            result.append(list.get(0));
        }
        else if(list.size() > 1 || list != null) {

            for (int i = 0; i < list.size(); i++) {
                if(i == list.size() -2) {
                    result.append(list.get(i) + " and ");
                }
                else if(i == list.size() -1) {
                    result.append(list.get(i));
                }
                else {
                    result.append(list.get(i) + ", ");
                }
            }
        }

        return result.toString().trim();
    }
}
