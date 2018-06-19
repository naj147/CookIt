package com.example.naj_t.cookitv1;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ShoppingListAdapter  extends RecyclerView.Adapter<ShoppingListAdapter.ViewHolder> {
    private ArrayList<String> ingredients=null;
    ArrayList<String> checked=null;

    public ShoppingListAdapter(ArrayList<String> ingredients) {
        if(ingredients!=null && ingredients.size()>0){
            this.addIngredients(ingredients);
            if(checked==null){
                checked=new ArrayList<>();
            }
            this.checked.addAll(this.ingredients);
        }

    }

    public ArrayList<String> getChecked() {
        return checked;
    }
    public void setChecked(ArrayList<String> checked) {
        this.checked = checked;
    }
    private void addChecked(String checkedIngr){
        if(checked==null)
            checked=new ArrayList<>();
        if(!checked.contains(checkedIngr))
            checked.add(checkedIngr);
    }
    private void deleteChecked(String checkedIngr){
        int index = -1;
        if(checked !=null ){
            index = checked.indexOf(checkedIngr);
            if(index>-1)
            checked.remove(index);
        }
    }
    protected void addIngredients(ArrayList<String> ingrs){
        for(String ingr : ingrs){
            addIngredient(ingr);
        }
    }
protected void clean(){
        ingredients.clear();
        checked.clear();
        notifyDataSetChanged();
}
    protected void addIngredient(String ingredient){
        if(ingredients==null)
            ingredients= new ArrayList<>();
        if(!this.ingredients.contains(ingredient)){
            ingredients.add(ingredient);
            if(checked==null)
                checked=new ArrayList<>();
            checked.add(ingredient);
            notifyDataSetChanged();

        }
    }
    @NonNull
    @Override
    public ShoppingListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shoppinglist_card, parent, false);
        return  new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ShoppingListAdapter.ViewHolder holder, int position) {
        final String ingredient = ingredients.get(position);
        holder.ingredients.setText(ingredient);
        if(checked!=null && checked.contains(ingredient)){
            holder.plus.setTag("deactivated");
            holder.minus.setTag("activated");
            Picasso.get()
                    .load(R.drawable.minus_activated)
                    .into(holder.minus);
            Picasso.get()
                    .load(R.drawable.plus_deactivated)
                    .into(holder.plus);
            holder.ingredients.setTextColor(Color.BLACK);
        }
        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView vv= (ImageView) v;

                if(vv.getTag().equals("activated")){
                    Picasso.get()
                            .load(R.drawable.plus_deactivated)
                            .into(vv);
                    Picasso.get()
                            .load(R.drawable.minus_activated)
                            .into(holder.minus);
                    holder.minus.setTag("activated");
                    vv.setTag("deactivated");
                    addChecked(ingredient);
                    holder.ingredients.setTextColor(vv.getContext().getResources().getColor(R.color.black));
                    Toast.makeText(holder.itemView.getContext(),"Item Added",Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView vv= (ImageView) v;

                if(vv.getTag().equals("activated")){
                    Picasso.get()
                            .load(R.drawable.minus_deactivated)
                            .into(vv);
                    Picasso.get()
                            .load(R.drawable.plus_activated)
                            .into(holder.plus);
                    holder.plus.setTag("activated");
                    vv.setTag("deactivated");
                    deleteChecked(ingredient);
                    holder.ingredients.setTextColor(vv.getContext().getResources().getColor(R.color.gray));
                    Toast.makeText(holder.itemView.getContext(),"Item Removed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(ingredients==null)
            return 0;
        return ingredients.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView minus,plus;
        TextView ingredients;
        public ViewHolder(View itemView) {
            super(itemView);
            minus= itemView.findViewById(R.id.minusImage);
            plus = itemView.findViewById(R.id.plusImage);
            ingredients= itemView.findViewById(R.id.ingredient);
        }
    }
}
