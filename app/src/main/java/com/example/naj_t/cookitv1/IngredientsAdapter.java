package com.example.naj_t.cookitv1;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.util.ArrayList;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder>{

    ArrayList<String> ingredients=null;
    ArrayList<String> checked=null;

    public ArrayList<String> getChecked() {
        return checked;
    }

    public  IngredientsAdapter(ArrayList<String> ingredients){
        addIngredients(ingredients);
    }
    public void setChecked(ArrayList<String> checked) {
        this.checked = checked;
    }

    public void addIngredients(ArrayList<String> ingrs){
        for(String ingr : ingrs){
            addIngredient(ingr);
        }
    }

    public void addIngredient(String ingredient){
        if(ingredients==null)
            ingredients= new ArrayList<>();
        if(!this.ingredients.contains(ingredient)){
            ingredients.add(ingredient);
        }
    }
    public ArrayList<String> getIngredients() {
        return ingredients;
    }
    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }
    @NonNull
    @Override
    public IngredientsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredients_card, parent, false);
        return  new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull IngredientsAdapter.ViewHolder holder, int position) {
        final String ingredient = ingredients.get(position);
        if(ingredient != null){
            holder.ingredient.setText(ingredient);
            holder.ingredient.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox c = (CheckBox) v;
                    if(!c.isChecked()){
                        if(checked!= null && checked.contains(ingredient)){
                            checked.remove(ingredient);
                        }
                    }
                    else
                    {
                        c.setChecked(true);
                        if(checked==null){
                            checked=new ArrayList<>();
                        }
                        checked.add(ingredient);
                    }
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder {
        public CheckBox ingredient;
        public ViewHolder(View itemView) {
            super(itemView);
            ingredient = itemView.findViewById(R.id.ingredient);
        }

    }
}
