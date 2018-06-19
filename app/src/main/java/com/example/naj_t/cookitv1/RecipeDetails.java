package com.example.naj_t.cookitv1;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.naj_t.cookitv1.API.RecipeController;
import com.example.naj_t.cookitv1.API.RecipeSearchController;
import com.squareup.picasso.Picasso;
import com.taishi.flipprogressdialog.FlipProgressDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RecipeDetails extends AppCompatActivity {
    RecipeModel recipeModel=null;
    Observer<RecipeAfterLoad> observer=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final RecipeBeforeLoad recipeBeforeLoad = new RecipeBeforeLoad();

        RecipeModel recipeModel = new RecipeModel(getApplication());
        setContentView(R.layout.activity_recipe_details);
        final Intent i = getIntent();
        final Button cookButton=findViewById(R.id.cookIt);
        ImageView likeButton=findViewById(R.id.like);
        ImageView recipeImage= findViewById(R.id.recipeImage);
        final TextView recipeTitle,rank,nbrIng,cookingTime,calories;
        calories=findViewById(R.id.calorieText);
        recipeTitle=findViewById(R.id.recipeTitle);
        rank=findViewById(R.id.rankText);
        nbrIng=findViewById(R.id.nbrOfIngrText);
        cookingTime=findViewById(R.id.cookingTimeText);
                /*
                *      i.putExtra("imageUrl", finalImageUrl);
                i.putExtra("rank",recipe.getRating());
                i.putExtra("nbrIngr",recipe.getIngredients().size());
                i.putExtra("recipeTitle", recipe.getRecipeTitle());
                i.putExtra("cookingTime",recipe.getTotalTimeInSecond()/60);*/
        if(i.hasExtra("imageUrl")){
            ArrayList<String> images = new ArrayList<>();
            images.add(i.getStringExtra("imageUrl"));
            recipeBeforeLoad.setImageUrl(images);
            Picasso.get()
                    .load(images.get(0))
                    .into(recipeImage);
        }
        if(i.hasExtra("recipeTitle")){
            recipeBeforeLoad.setRecipeTitle(i.getStringExtra("recipeTitle"));
            recipeTitle.setText(recipeBeforeLoad.getRecipeTitle());
        }
        if(i.hasExtra("rank")){
            int tempInt = i.getIntExtra("rank",-1);
            rank.setText((tempInt==-1)? "----":String.valueOf(tempInt)+ "/5");
        }
        if(i.hasExtra("nbrIngr")){
            int tempInt = i.getIntExtra("nbrIngr",-1);
            nbrIng.setText((tempInt==-1)? "----":String.valueOf(tempInt)+ " Ingredients");
        }
        if(i.hasExtra("cookingTime")){
            int tempInt = i.getIntExtra("cookingTime",-1);
            int[] tempInts = splitToComponentTimes(tempInt);
            if(tempInts[0]==0){
                cookingTime.setText((tempInt==-1)? "----":String.valueOf(tempInts[1])+ " mins");
            }else
                cookingTime.setText((tempInt==-1)? "----":String.valueOf(tempInts[0])+ " hours "+String.valueOf(tempInts[1])+ " mins");
        }
        Random r = new Random();
        int i1 = r.nextInt(1200 - 400) + 400;
        calories.setText(String.valueOf(i1) + " cal");
        likeButton.setOnClickListener(new View.OnClickListener() {
            //TODO: UPDATE THE VALUE IN THE FIREBASE :)
            @Override
            public void onClick(View v) {
                ImageView vv=(ImageView) v;
               String tag= (String)vv.getTag();
               if(tag.equals("notLike")){
                   vv.setTag("Like");
                   Picasso.get()
                           .load(R.drawable.baseline_favorite_black_18dp)
                           .into(vv);
                   Toast.makeText(vv.getContext(),"I like this Recipe",Toast.LENGTH_LONG).show();
                }else{
                   vv.setTag("notLike");
                   Picasso.get()
                           .load(R.drawable.baseline_favorite_border_black_18dp)
                           .into(vv);
                   Toast.makeText(vv.getContext(),"I dislike this Recipe",Toast.LENGTH_LONG).show();
               }
            }
        });
        RecipeController recipeController = new RecipeController();
        recipeBeforeLoad.setId(i.getStringExtra("recipeId"));
        recipeController.start(recipeBeforeLoad.getId(),recipeModel);
        observer =  new Observer<RecipeAfterLoad>() {
            @Override
            public void onChanged(@Nullable final RecipeAfterLoad recipeAfterLoads) {
                cookButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressBar();
                        //   Toast.makeText(v.getContext(),"I was cliked with update in me",Toast.LENGTH_LONG).show();
                        assert recipeAfterLoads != null;
                        recipeAfterLoads.setRecipeBeforeLoad(recipeBeforeLoad);
                        Intent j = new Intent(v.getContext(),CookActivity.class);
                        j.putStringArrayListExtra("IngredientsList",recipeAfterLoads.getIngredientsLines());
                        j.putExtra("imageUrl",recipeAfterLoads.recipeBeforeLoad.getImageUrl().get(0));
                        j.putExtra("sourceUrl",recipeAfterLoads.getSource().getSourceRecipeUrl());
                        j.putExtra("id",recipeAfterLoads.recipeBeforeLoad.getId());
                        j.putExtra("recipeTitle",recipeAfterLoads.recipeBeforeLoad.getRecipeTitle());
                        startActivity(j);
//                        Handler handler = new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                            }
//                        },500);
                    }
                });
            }
        };

        recipeModel.getRecipeAfterLoadLiveData().observe(this,observer);


    }
    public static int[] splitToComponentTimes(int longVal)
    {
        int hours = longVal / 3600;
        int remainder = longVal - hours * 3600;
        int mins = remainder / 60;
        int[] ints = {hours , mins};
        return ints;
    }
    public void progressBar(){
        List<Integer> imageList = new ArrayList<Integer>();
        imageList.add(R.drawable.souchef_medium);
        final FlipProgressDialog border = new FlipProgressDialog();
        border.setImageList(imageList);
        border.setOrientation("rotationY");
        border.setBorderStroke(10);
        border.setDuration(800);
        border.setBorderColor(Color.parseColor("#02A8F3"));
        border.setBackgroundColor(Color.parseColor("#FFFFFF"));
        border.show(getFragmentManager(),"");
        Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                border.dismiss();
            }
        }, 4000);
    }
    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //No call for super(). Bug on API Level > 11.
    }

    @Override
    protected void onStop() {
        if(recipeModel!=null && observer !=null)
            recipeModel.getRecipeAfterLoadLiveData().removeObserver(observer);
        super.onStop();
    }
}
