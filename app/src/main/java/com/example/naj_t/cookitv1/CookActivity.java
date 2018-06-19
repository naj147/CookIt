package com.example.naj_t.cookitv1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class CookActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook);
        String recipeTitle="";
        ImageView imageView = findViewById(R.id.recipeImage);
        TextView textView = findViewById(R.id.recipeTitle);
        Button addToShoppingList=findViewById(R.id.addShoppingList);
        Button next = findViewById(R.id.next);
        String sourceUrl=null,id=null;
        Intent i = getIntent();
        ArrayList<String> ingredients=null;
        if(i.hasExtra("IngredientsList"))
            ingredients=i.getStringArrayListExtra("IngredientsList");
        if(i.hasExtra("imageUrl")){
            Picasso.get()
                    .load(i.getStringExtra("imageUrl"))
                    .into(imageView);
        }
        if(i.hasExtra("sourceUrl"))
            sourceUrl=i.getStringExtra("sourceUrl");
        if(i.hasExtra("id"))
            id=i.getStringExtra("id");
        if(i.hasExtra("recipeTitle")){
            recipeTitle=i.getStringExtra("recipeTitle");
            textView.setText(recipeTitle);
        }
//            textView.setText(i.getStringExtra("recipeTitle"));
        /* j.putExtra("imageUrl",recipeAfterLoads.recipeBeforeLoad.getImageUrl());
                        j.putExtra("sourceUrl",recipeAfterLoads.getSource().getSourceRecipeUrl());
                        j.putExtra("id",recipeAfterLoads.recipeBeforeLoad.getId());
                        j.putExtra("recipeTitle",recipeAfterLoads.recipeBeforeLoad.getRecipeTitle());
*/
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        final IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(ingredients);
        mRecyclerView.setAdapter(ingredientsAdapter);
        final ArrayList<String> finalIngredients = ingredients;
        final String finalRecipeTitle = recipeTitle;
        addToShoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO : send this to the shoppingList SQL DB

            Toast.makeText(v.getContext(),"Ingredients added to your shopping list",Toast.LENGTH_LONG).show();
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(v.getContext());
                new AsyncSave(ingredientsAdapter.checked,sharedPref).execute();
//                finalIngredients.add(0, finalRecipeTitle);

            }
        });
        final String finalSourceUrl = sourceUrl;
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(v.getContext(),NextActivity.class);
                j.putExtra("sourceUrl", finalSourceUrl);
                startActivity(j);
            }
        });
    }
    private static class AsyncSave extends AsyncTask<Void, Void, Boolean> {
        ArrayList<String> favNamesList;
        SharedPreferences sharedPref ;
        AsyncSave(ArrayList<String> favNamesList, SharedPreferences sharedPref ) {
            this.favNamesList = favNamesList;
            this.sharedPref= sharedPref;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            HashSet<String> strings = new HashSet<String>(favNamesList);
            SharedPreferences.Editor edit = sharedPref.edit();
            edit.clear();
            edit.putStringSet("ingredients", strings);
            edit.commit();
            return null;
        }
    }

}
