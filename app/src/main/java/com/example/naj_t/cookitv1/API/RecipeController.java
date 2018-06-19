package com.example.naj_t.cookitv1.API;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.widget.TextView;

import com.example.naj_t.cookitv1.API.Interface.RecipeService;
import com.example.naj_t.cookitv1.RecipeAfterLoad;
import com.example.naj_t.cookitv1.RecipeModel;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeController implements Callback<RecipeAfterLoad> {
RecipeModel recipeModel;
String TAG="RecipeController";
    public void start(String recipeId,RecipeModel recipeModel){
        this.recipeModel=recipeModel;
        String baseUrl = "http://api.yummly.com/v1/api/recipe/";
        Gson gson =
                new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().registerTypeAdapterFactory(new itemTypeAdapterFactory()).create();
        Retrofit retrofit= new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        RecipeService service = retrofit.create(RecipeService.class);
        String app_key = "ed74980921254acf7f2a5c637769470c";
        String app_id = "6d2f35dd";
        Call<RecipeAfterLoad> call = service.listRecipes(recipeId,app_id,app_key);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<RecipeAfterLoad> call, Response<RecipeAfterLoad> response) {
        if(response.isSuccessful()) {

            Log.v(TAG, call.request().url().toString());
            RecipeAfterLoad recipeAfterLoad=response.body();

            recipeModel.addData(recipeAfterLoad);
        }else {
            Log.v(TAG, call.request().url().toString());

            Log.v(TAG, "Error bitch");
        }
    }

    @Override
    public void onFailure(Call<RecipeAfterLoad> call, Throwable t) {
        Log.v(TAG, call.request().url().toString());
        Log.v(TAG, "Failed bitch " + t.getMessage());
    }

    //http://api.yummly.com/v1/api/recipe/recipe-id?_app_id=YOUR_ID&_app_key=YOUR_APP_KEY
}
