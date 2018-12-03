package icesi.i2t.cookit.lists;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import icesi.i2t.cookit.R;
import icesi.i2t.cookit.activities.MainActivity;
import icesi.i2t.cookit.fragments.SearchFragment;
import icesi.i2t.cookit.model.Category;
import icesi.i2t.cookit.model.Recipe;

public class RecyclerAdapterCategory extends RecyclerView.Adapter<ViewHolderCategory> {

    private LayoutInflater inflater;
    private ArrayList<Category> categories;
    private Context context;
    private MainActivity main;


    public RecyclerAdapterCategory(Context context, ArrayList<Category> categories){
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public ViewHolderCategory onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.custom_table_row_category, viewGroup, false);
        ViewHolderCategory holder = new ViewHolderCategory(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCategory viewHolder, int i) {
        Category current = categories.get(i);
//        Picasso.get().load(current.getMediumImageUrl()).into(viewHolder.getImage_item());
        viewHolder.getItem_name().setText(current.getName());

        viewHolder.getItem().setOnClickListener(action -> {
            SearchFragment frag = new SearchFragment();
            frag.setCategorie(current.getId());
            main.setFragment(frag);
//            Intent intent = new Intent(context, PlayListView.class);
//            intent.putExtra("listId", current.getId());
//            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public MainActivity getMain() {
        return main;
    }

    public void setMain(MainActivity main) {
        this.main = main;
    }
}
