package com.example.naj_t.cookitv1;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RecipeAfterLoad {
    RecipeBeforeLoad recipeBeforeLoad;
      @SerializedName("ingredientLines")
      @Expose
    ArrayList<String> ingredientsLines;
      @SerializedName("numberOfServings")
      @Expose
    int nbrOfServings;

    public RecipeAfterLoad(ArrayList<String> ingredientsLines, int nbrOfServings, Source source, RecipeBeforeLoad recipeBeforeLoad) {
        this.ingredientsLines = ingredientsLines;
        this.nbrOfServings = nbrOfServings;
        this.source = source;
        this.recipeBeforeLoad = recipeBeforeLoad;
    }
    public RecipeAfterLoad(ArrayList<String> ingredientsLines, int nbrOfServings, Source source) {
        this.ingredientsLines = ingredientsLines;
        this.nbrOfServings = nbrOfServings;
        this.source = source;
        this.recipeBeforeLoad = null;
    }

    public RecipeAfterLoad(RecipeBeforeLoad recipeBeforeLoad, ArrayList<String> ingredientsLines, int nbrOfServings) {
        this.recipeBeforeLoad = recipeBeforeLoad;
        this.ingredientsLines = ingredientsLines;
        this.nbrOfServings = nbrOfServings;
    }

    public RecipeAfterLoad() {
    }

    public ArrayList<String> getIngredientsLines() {
        return ingredientsLines;
    }

    public void setIngredientsLines(ArrayList<String> ingredientsLines) {
        this.ingredientsLines = ingredientsLines;
    }

    public int getNbrOfServings() {
        return nbrOfServings;
    }

    public void setNbrOfServings(int nbrOfServings) {
        this.nbrOfServings = nbrOfServings;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public RecipeBeforeLoad getRecipeBeforeLoad() {
        return recipeBeforeLoad;
    }

    public void setRecipeBeforeLoad(RecipeBeforeLoad recipeBeforeLoad) {
        this.recipeBeforeLoad = recipeBeforeLoad;
    }
    @SerializedName("source")
    @Expose
      Source source;

      public class Source {
          @SerializedName("sourceDisplayName")
          @Expose
          String sourceDisplayName;
          @SerializedName("sourceSiteUrl")
          @Expose
          String sourceSiteUrl;
          @SerializedName("sourceRecipeUrl")
          @Expose
          String sourceRecipeUrl;

          public Source(String sourceDisplayName, String sourceSiteUrl, String sourceRecipeUrl) {
              this.sourceDisplayName = sourceDisplayName;
              this.sourceSiteUrl = sourceSiteUrl;
              this.sourceRecipeUrl = sourceRecipeUrl;
          }
          public Source() {
              this.sourceDisplayName = null;
              this.sourceSiteUrl = null;
              this.sourceRecipeUrl = null;
          }

          public String getSourceDisplayName() {
              return sourceDisplayName;
          }

          public void setSourceDisplayName(String sourceDisplayName) {
              this.sourceDisplayName = sourceDisplayName;
          }

          public String getSourceSiteUrl() {
              return sourceSiteUrl;
          }

          public void setSourceSiteUrl(String sourceSiteUrl) {
              this.sourceSiteUrl = sourceSiteUrl;
          }

          public String getSourceRecipeUrl() {
              return sourceRecipeUrl;
          }

          public void setSourceRecipeUrl(String sourceRecipeUrl) {
              this.sourceRecipeUrl = sourceRecipeUrl;
          }
      }

}
//new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();