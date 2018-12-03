package icesi.i2t.cookit.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import icesi.i2t.cookit.activities.MainActivity;

public class DataBase {

    private static FirebaseAuth auth;
    private static FirebaseDatabase db;
    private static DataBase dataBase;
    private modelLogic logic;
    public boolean termino;

    public DataBase(){
        db = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    public DataBase(modelLogic logic){
        db = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        this.logic = logic;
    }

    public static DataBase getInstance(){
        if (dataBase == null){
            dataBase = new DataBase();
        }
        return dataBase;
    }



    public HashMap<String, User> getUserList(){
        termino = false;
        DatabaseReference usuarios_ref = db.getReference().child("usuarios");
        HashMap<String, User> usuarios = new HashMap<>();
        usuarios_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> ds = dataSnapshot.getChildren();
                Log.e("----------->", "Usuarios");
                for (DataSnapshot data : ds){
                    User usr = data.getValue(User.class);
                    usuarios.put(usr.getUser_id(), usr);
                    Log.e("----------->", usr.getUser_id());
                }
                if (logic!=null){
                    logic.refreshUsuarios(usuarios);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return usuarios;
    }


    public HashMap<String, Recipe> getRecipeList(){
        DatabaseReference recipies_ref = db.getReference().child("recipes");
        HashMap<String, Recipe> recipies = new HashMap<>();

        recipies_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> ds = dataSnapshot.getChildren();
                for (DataSnapshot data : ds){
                    Recipe rec = data.getValue(Recipe.class);
                    recipies.put(rec.getId(), rec);
                }
                Log.e("----------->", "Fin recetas " + recipies.size());
                logic.refreshRecetas(recipies);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return recipies;
    }

    public HashMap<String, Ingredient> getIngedientsList(){
        DatabaseReference ingredients_ref = db.getReference().child("ingredients");
        HashMap<String, Ingredient> ingredients = new HashMap<>();

        ingredients_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> ds = dataSnapshot.getChildren();
                for (DataSnapshot data : ds){
                    Ingredient rec = data.getValue(Ingredient.class);
//                    Log.e("----------->", rec.getName());
                    ingredients.put(rec.getId(), rec);
                }
                logic.refreshIngredientes(ingredients);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return ingredients;
    }

    public HashMap<String, Ingredient> getIngedientsListByName(){
        DatabaseReference ingredients_ref = db.getReference().child("ingredients");
        HashMap<String, Ingredient> ingredients = new HashMap<>();

        ingredients_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> ds = dataSnapshot.getChildren();
                for (DataSnapshot data : ds){
                    Ingredient rec = data.getValue(Ingredient.class);
//                    Log.e("----------->", rec.getName());
                    ingredients.put(rec.getName(), rec);
                }
                logic.refreshIngredientesName(ingredients);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return ingredients;
    }

    public HashMap<String, Category> getCategoriesList(){
        DatabaseReference category_ref = db.getReference().child("categories");
        HashMap<String, Category> ingredients = new HashMap<>();

        category_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> ds = dataSnapshot.getChildren();
                for (DataSnapshot data : ds){
                    Category rec = data.getValue(Category.class);
                    ingredients.put(rec.getId(), rec);
                }
                logic.refreshCategories(ingredients);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return ingredients;
    }

    public HashMap<String, Category> getCategoriesListName(){
        DatabaseReference category_ref = db.getReference().child("categories");
        HashMap<String, Category> ingredients = new HashMap<>();

        category_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> ds = dataSnapshot.getChildren();
                for (DataSnapshot data : ds){
                    Category rec = data.getValue(Category.class);
                    ingredients.put(rec.getName(), rec);
                }
                logic.refreshCategoriesNames(ingredients);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return ingredients;
    }

}
