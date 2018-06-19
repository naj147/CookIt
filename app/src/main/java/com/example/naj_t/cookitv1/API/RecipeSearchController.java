package com.example.naj_t.cookitv1.API;

import android.util.Log;

import com.example.naj_t.cookitv1.API.Interface.RecipeSearchService;
import com.example.naj_t.cookitv1.RecipesAdapter;
import com.example.naj_t.cookitv1.RecipeBeforeLoad;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class RecipeSearchController implements Callback<List<RecipeBeforeLoad>> {
    private RecipesAdapter mAdapter;
    private String TAG="My URL";
    public void start(RecipesAdapter adapter) {
        this.mAdapter=adapter;
        Gson gson =
                new GsonBuilder().registerTypeAdapterFactory(new itemTypeAdapterFactory()).create();
        String baseUrl = "http://api.yummly.com/v1/api/";
        Retrofit retrofit= new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        RecipeSearchService service = retrofit.create(RecipeSearchService.class);
        String app_key = "ed74980921254acf7f2a5c637769470c";
        String app_id = "6d2f35dd";
        Call<List<RecipeBeforeLoad>> call = service.listRecipes(app_id, app_key,null,null,"true",null,null);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<List<RecipeBeforeLoad>> call, Response<List<RecipeBeforeLoad>> response) {
        if(response.isSuccessful()){
            Log.v(TAG, call.request().url().toString());
            List<RecipeBeforeLoad> list=response.body();
            mAdapter.addRecipes(list);
            mAdapter.notifyDataSetChanged();
        }else{
            Log.v(TAG, call.request().url().toString());
            Log.v(TAG, "Error bitch");
        }
    }

    @Override
    public void onFailure(Call<List<RecipeBeforeLoad>> call, Throwable t) {

        Log.v(TAG, call.request().url().toString());
        Log.v(TAG, "Miserably failed " + t.getMessage());
    }
}