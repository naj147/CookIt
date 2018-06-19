package com.example.naj_t.cookitv1;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder> {
private List<RecipeBeforeLoad> mDataset;

// Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
public static class ViewHolder extends RecyclerView.ViewHolder {
    // each data item is just a string in this case
    public TextView recipeTitle;
    public TextView recipeTags;
    public ImageView recipeImage;
    public ViewHolder(View v) {
        super(v);
        recipeImage = v.findViewById(R.id.recipeImage);
        recipeTitle=v.findViewById(R.id.recipeTitle);
        recipeTags=v.findViewById(R.id.recipeTags);
    }
}

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecipesAdapter(ArrayList<RecipeBeforeLoad> myDataset) {
        mDataset = myDataset;
    }

    public void addRecipes(List<RecipeBeforeLoad> recipes){
            if (mDataset==null)
                mDataset=new ArrayList<>();
                this.mDataset.addAll(recipes);
    }
    // Create new views (invoked by the layout manager)
    @Override
    public RecipesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_card, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    private String tagsGenerator(RecipeBeforeLoad recipe){
        StringBuilder tag= new StringBuilder();
        if(recipe.getAttributes().getCourse()!=null) {
            for (String course : recipe.getAttributes().getCourse()) {
                tag.append(course).append(" |");
            }
        }
        if(recipe.getAttributes().getCuisine()!=null) {
            for (String cuisine:recipe.getAttributes().getCuisine()) {
                tag.append(cuisine).append(" | ");
            }
        }
        tag.append(String.valueOf(recipe.totalTimeInSecond/60) + " min |");
        tag.append(" Rank : "+ recipe.getRating() + "/5");

        return tag.toString();
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        final RecipeBeforeLoad recipe = mDataset.get(position);
        holder.recipeTags.setText(tagsGenerator(recipe));
        holder.recipeTitle.setText(recipe.getRecipeTitle());
        String imageUrl= recipe.getImageUrl().get(0);
        int lastString = imageUrl.lastIndexOf("=s90");
        imageUrl=imageUrl.substring(0,(lastString)==-1? imageUrl.length():lastString);
        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.friedlo)
                .into(holder.recipeImage);
        final String finalImageUrl = imageUrl;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(v.getContext(),RecipeDetails.class);
                i.putExtra("imageUrl", finalImageUrl);
                i.putExtra("rank",recipe.getRating());
                i.putExtra("nbrIngr",recipe.getIngredients().size());
                i.putExtra("recipeTitle", recipe.getRecipeTitle());
                i.putExtra("cookingTime",recipe.getTotalTimeInSecond());
                i.putExtra("recipeId",recipe.id);
                holder.itemView.getContext().startActivity(i);
            }
        });
        // .error(R.drawable.user_placeholder_error)

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if(mDataset!=null)
            return mDataset.size();
        return 0;
}

}