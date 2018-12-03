package icesi.i2t.cookit.activities;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import icesi.i2t.cookit.R;
import icesi.i2t.cookit.model.Ingredient;
import icesi.i2t.cookit.model.Like;
import icesi.i2t.cookit.model.Recipe;

public class RecipeName extends AppCompatActivity {

    private ImageView foto;
    private ImageView like;
    private TextView nombre;
    private TextView ingredients;
    private TextView steps;
    private Recipe recipe;
    private FirebaseDatabase db;
    private FirebaseAuth auth;
    private FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_name);

        foto = findViewById(R.id.foto);
        nombre = findViewById(R.id.recipe_name);
        ingredients = findViewById(R.id.txt_ingredients);
        steps = findViewById(R.id.txt_steps);
        like = findViewById(R.id.btn_like);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        String recipeId = getIntent().getExtras().getString("recipeId");

        buscarInfo(recipeId);

        like.setOnClickListener(e -> {
            Like like = new Like(auth.getCurrentUser().getUid());
            if(recipe.getLikedby().containsValue(like)){
                for (Like l: recipe.getLikedby().values()) {
                    if (l.equals(like)){
                        like = l;
                        break;
                    }
                }
                Log.e("----------->", recipe.getLikedby().size() + "");
                recipe.getLikedby().remove(like.getId_lkusr());
                Log.e("----------->", recipe.getLikedby().size() + "");
                Log.e("----------->", like.getId_lkusr() + "");
                db.getReference().child("recipes").child(recipe.getId()).child("likedby").child(like.getId_lkusr()).removeValue();
                db.getReference().child("usuarios").child(auth.getCurrentUser().getUid()).child("liked").child(like.getId()).removeValue();
                like(false);
            } else {
                DatabaseReference reference =db.getReference().child("usuarios").child(auth.getCurrentUser().getUid()).child("liked").push();
                like.setId_var(recipe.getId());
                like.setId(reference.getKey());
                reference.setValue(like);
                reference = db.getReference().child("recipes").child(recipe.getId()).child("likedby").push();
                like.setId_var(auth.getCurrentUser().getUid());
                like.setId_lkusr(reference.getKey());
                reference.setValue(like);
                recipe.getLikedby().put(like.getId_lkusr(), like);
                like(true);
            }
        });

    }

    public void like(boolean liked){
        if (!liked){
            like.setImageResource(R.drawable.icon_fav_empty);
        }
        else {
            like.setImageResource(R.drawable.icon_fav_filled);
        }
    }

    public void obtenerIngredientes(){
        DatabaseReference ingredients_ref = db.getReference().child("ingredients");
        ArrayList<Ingredient> recIngredients = new ArrayList<>();
        ingredients_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> ds = dataSnapshot.getChildren();
                String ings = "";
                for (DataSnapshot data : ds){
                    Ingredient ing = data.getValue(Ingredient.class);
                    if (recipe.getIngredient().containsValue(ing.getId())){
                        String str = ing.getName();
                        String a = str.substring(0,1);
                        String b = str.substring(1);
                        a = a.toUpperCase();
                        ings += "-" + a+b + " " + ing.getCost() + "\n\n";
                        recIngredients.add(ing);
                    }
                }
                ingredients.setText(ings);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void ponerInfo(){

        nombre.setText(recipe.getName());
        String step = "";
        ArrayList<String> ss = recipe.getSteps();
        for (String  s: ss) {
            step += s + "\n\n";
        }
        steps.setText(step);
        obtenerIngredientes();
        like(recipe.getLikedby().containsValue(new Like(auth.getCurrentUser().getUid())));

        StorageReference ref = storage.getReference().child("recipes").child(recipe.getId());
        Log.e("***************", ref.getPath());
        try {
            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).into(foto);
                }
            });
        } catch (Exception e) {

        }


    }


    public void buscarInfo(String recId){

        DatabaseReference recipes_ref = db.getReference().child("recipes");
        recipes_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> ds = dataSnapshot.getChildren();
                for (DataSnapshot data : ds){
                    Recipe rec = data.getValue(Recipe.class);
                    if (rec.getId().equals(recId)){
                        recipe = rec;
                        break;
                    }
                }
                ponerInfo();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
