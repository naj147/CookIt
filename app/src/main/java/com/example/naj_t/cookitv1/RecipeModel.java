package com.example.naj_t.cookitv1;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class RecipeModel extends AndroidViewModel {
    private  final MutableLiveData<RecipeAfterLoad> recipeAfterLoadLiveData = new MutableLiveData<RecipeAfterLoad>();

    public MutableLiveData<RecipeAfterLoad> getRecipeAfterLoadLiveData() {
        return recipeAfterLoadLiveData;
    }

    public void addData(RecipeAfterLoad recipeList) {
        recipeAfterLoadLiveData.postValue(recipeList);
    }

    RecipeModel(@NonNull Application application) {
        super(application);
    }
}
