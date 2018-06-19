package com.example.naj_t.cookitv1;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RecipeBeforeLoad {

    @SerializedName("ingredients")
    ArrayList<String> ingredients;
    @SerializedName("smallImageUrls")
    ArrayList<String> imageUrl;
    @SerializedName("id")
    String id;
    @SerializedName("totalTimeInSeconds")
    int totalTimeInSecond;
    @Expose
    attributes attributes;
    @SerializedName("recipeName")
    String recipeTitle;

    public String getRecipeTitle() {
        return recipeTitle;
    }

    public void setRecipeTitle(String recipeTitle) {
        this.recipeTitle = recipeTitle;
    }



    public ArrayList<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(ArrayList<String> imageUrl) {
        this.imageUrl = imageUrl;
    }

    public RecipeBeforeLoad(ArrayList<String> ingredients, ArrayList<String> imageUrl, String id, int totalTimeInSecond, RecipeBeforeLoad.attributes attributes, String recipeTitle, int rating) {
        this.ingredients = ingredients;
        this.imageUrl = imageUrl;
        this.id = id;
        this.totalTimeInSecond = totalTimeInSecond;
        this.attributes = attributes;
        this.recipeTitle = recipeTitle;
        this.rating = rating;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public RecipeBeforeLoad() {
        this.ingredients=null;
        this.imageUrl = null;
        this.id = null;
        this.totalTimeInSecond = 0;
        this.attributes = null;
        this.recipeTitle = null;
        this.rating = 0;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public int getTotalTimeInSecond() {
        return totalTimeInSecond;
    }

    public void setTotalTimeInSecond(int totalTimeInSecond) {
        this.totalTimeInSecond = totalTimeInSecond;
    }

    public RecipeBeforeLoad.attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(RecipeBeforeLoad.attributes attributes) {
        this.attributes = attributes;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @SerializedName("rating")
    int rating;

    public class attributes {
        @SerializedName("course")
        ArrayList<String> course;
        @SerializedName("cuisine")
        ArrayList<String> cuisine;

        public List<String> getCuisine() {
            return cuisine;
        }

        public void setCuisine(ArrayList<String> cuisine) {
            this.cuisine = cuisine;
        }

        public List<String> getCourse() {
            return course;
        }

        public attributes(ArrayList<String> course, ArrayList<String> cuisine) {
            this.course = course;
            this.cuisine = cuisine;
        }

        public attributes() {
            this.course =null;
            this.cuisine=null;
        }

        public void setCourse(ArrayList<String> course) {
            this.course = course;
        }
    }

}
