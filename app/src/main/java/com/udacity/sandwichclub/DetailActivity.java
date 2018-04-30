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

        // Main name view
        TextView mainNameView = findViewById(R.id.main_name_tv);
        mainNameView.setText(sandwich.getMainName());

        // Origin view
        TextView originView = findViewById(R.id.origin_tv);
        originView.setText(sandwich.getPlaceOfOrigin());

        // Description view
        TextView descriptionView = findViewById(R.id.description_tv);
        descriptionView.setText(sandwich.getDescription());

        // Ingredients view
        TextView ingredientsView = findViewById(R.id.ingredients_tv);
        List<String> ingredientsList = sandwich.getIngredients();
        ingredientsView.setText(stringifyList(ingredientsList, "ingredients"));

        // Also known As view
        TextView alsoKnownAsView = findViewById(R.id.also_known_tv);
        List<String> alsoknownAsList = sandwich.getAlsoKnownAs();
        alsoKnownAsView.setText(stringifyList(alsoknownAsList, "alsoKnownAs"));

    }

    /**
     * This helper method takes a List and creates a string with elements separated by comma,
     * "and", and period.
     * @param list
     * @return String version of listed items.
     */
    private String stringifyList(List<String> list, String listType) {
        StringBuilder result = new StringBuilder("");

        if(list.size() == 1) {
            result.append(list.get(0));
        }
        else if(list.size() > 1 || list != null) {

            for (int i = 0; i < list.size(); i++) {

                String item = list.get(i);
                // This assures that all ingredients are lower case
                if(i > 0 && listType == "ingredients") item = item.toLowerCase();

                if(i == list.size() -2) {
                    result.append(item + " and ");
                }
                else if(i == list.size() -1) {
                    result.append(item + ".");
                }
                else {
                    result.append(item + ", ");
                }
            }
        }

        return result.toString().trim();
    }
}
