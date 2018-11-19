package icesi.i2t.cookit.activities;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

import icesi.i2t.cookit.R;
import icesi.i2t.cookit.model.MyViewHolder;
import icesi.i2t.cookit.model.Recipe;
import icesi.i2t.cookit.model.Recipie;

public class RecyclerAdapterFeed extends RecyclerView.Adapter<MyViewHolder> {

    private LayoutInflater inflater;
    private List<Recipe> recipies;
    private Context context;
    String[] a;

    public RecyclerAdapterFeed(Context context, List<Recipe> recipies){
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.recipies = recipies;
        a = new String[100];
        for (int i = 0 ; i<100; i++){
            a[i] = "a";
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.custom_table_row, viewGroup, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int i) {
        Recipe current = recipies.get(i);
        String c = a[i];
//        Picasso.get().load(current.getMediumImageUrl()).into(viewHolder.getImage_item());
        viewHolder.getItem_name().setText(current.getName());
        viewHolder.getItem_description().setText(current.getDescription());

//        viewHolder.getItem().setOnClickListener(action -> {
//            Intent intent = new Intent(context, PlayListView.class);
//            intent.putExtra("listId", current.getId());
//            context.startActivity(intent);
//        });

    }

    @Override
    public int getItemCount() {
        return recipies.size();
    }

}
