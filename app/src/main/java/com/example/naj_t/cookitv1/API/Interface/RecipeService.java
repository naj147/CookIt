package com.example.naj_t.cookitv1.API.Interface;

import com.example.naj_t.cookitv1.RecipeAfterLoad;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RecipeService {
    @GET("{recipeId}")
        //=cuisine^cuisine-NAME OF THE CUISINE
    Call<RecipeAfterLoad> listRecipes(@Path("recipeId") String recipeId,@Query("_app_id") String app_id, @Query("_app_key") String app_key);
}
