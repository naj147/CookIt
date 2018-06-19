package com.example.naj_t.cookitv1.API.Interface;

import com.example.naj_t.cookitv1.RecipeBeforeLoad;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RecipeSearchService {
    //recipes?_app_id="+"6d2f35dd"+"&_app_key="+"ed74980921254acf7f2a5c637769470c
    @GET("recipes?")
    //=cuisine^cuisine-NAME OF THE CUISINE
    Call<List<RecipeBeforeLoad>> listRecipes(@Query("_app_id") String app_id, @Query("_app_key") String app_key, @Query("q") String query, @Query("&allowedCuisine[]") String  cuisine , @Query("requirePictures") String requirePictures, @Query("allowedIngredient[]") String allowedIngredients, @Query("allowedCourse[][]") String allowedCourse);


}
