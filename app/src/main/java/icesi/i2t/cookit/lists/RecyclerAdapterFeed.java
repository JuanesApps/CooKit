package icesi.i2t.cookit.lists;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import icesi.i2t.cookit.R;
import icesi.i2t.cookit.activities.RecipeName;
import icesi.i2t.cookit.model.Like;
import icesi.i2t.cookit.model.Recipe;

public class RecyclerAdapterFeed extends RecyclerView.Adapter<MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<Recipe> recipies;
    private FirebaseAuth auth;
    private FirebaseDatabase db;
    private Context context;
    private FirebaseStorage storage;
    String[] a;


    public RecyclerAdapterFeed(Context context, ArrayList<Recipe> recipies) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.recipies = recipies;
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        db.getReference().child("recipes").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                HashMap<String, Object> r = (HashMap) dataSnapshot.getValue();
                Recipe rec = new Recipe();
                rec.setId((String) r.get("id"));
//                rec.setU_id((String) r.get("u_id"));
//                rec.setName((String) r.get("name"));
//                Log.e("-----------a", r.get("name").toString());
//                rec.setSteps((ArrayList<String>) r.get("steps"));
//                rec.setIngredient((HashMap<String, String>) r.get("ingredient"));

                Log.e("-----------a", r.get("id")+"");
                int index = recipies.indexOf(rec);
                HashMap<String, HashMap<String, String>> ls = (HashMap<String, HashMap<String, String>>) r.get("likedby");
                if (ls == null){
                    ls = new HashMap<>();
                }
                HashMap<String, Like> likes = new HashMap<>();
                for (HashMap<String, String> l: ls.values()) {
                    String id = l.get("id");
                    String id_var = l.get("id_var");
                    String id_lkusr = l.get("id_lkusr");
                    Like like = new Like();
                    like.setId(id);
                    like.setId_var(id_var);
                    like.setId_lkusr(id_lkusr);
                    likes.put(id_lkusr, like);
                }
                Log.e("-----------len", recipies.size()+"");
                if(index!=-1){
                    recipies.get(index).setLikedby(likes);;
                }
                notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        a = new String[100];
        for (int i = 0; i < 100; i++) {
            a[i] = "a";
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.custom_table_row_recipe, viewGroup, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int i) {
        Recipe current = recipies.get(i);
        String c = a[i];
        StorageReference ref = storage.getReference().child("recipes").child(current.getId()+".jpg");
        try {
            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(viewHolder.getImage_item());
                }
            });
        } catch (Exception e){

        }


        viewHolder.getItem_name().setText(current.getName());
        viewHolder.getItem_description().setText(current.getDescription());
        viewHolder.setRecipe(current);
        if(current.getLikedby()!=null){
            Log.e("-----------a", current.getLikedby().containsValue(new Like(auth.getCurrentUser().getUid()))+" "
                    + current.getLikedby() + " " +
                    current.getName());
        }
        viewHolder.like(current.getLikedby().containsValue(new Like(auth.getCurrentUser().getUid())));



        viewHolder.getItem().setOnClickListener(action -> {
            Intent intent = new Intent(context, RecipeName.class);
            intent.putExtra("recipeId", current.getId());
            context.startActivity(intent);
        });

    }


    @Override
    public int getItemCount() {
        return recipies.size();
    }

}
