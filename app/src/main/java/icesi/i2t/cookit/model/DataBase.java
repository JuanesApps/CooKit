package icesi.i2t.cookit.model;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class DataBase {

    private static FirebaseAuth auth;
    private static FirebaseDatabase db;
    private static DataBase dataBase;
//    private static

    public DataBase(){
        db = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    public static DataBase getInstance(){
        if (dataBase == null){
            dataBase = new DataBase();
        }
        return dataBase;
    }

    public HashMap<String, User> getUserList(){
        DatabaseReference usuarios_ref = db.getReference().child("usuarios");
        HashMap<String, User> usuarios = new HashMap<>();
        usuarios_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> ds = dataSnapshot.getChildren();
                for (DataSnapshot data : ds){
                    User usr = data.getValue(User.class);
                    usuarios.put(usr.getUser_id(), usr);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return usuarios;
    }

    public HashMap<String, Recipe> getRecipeList(){
        DatabaseReference recipies_ref = db.getReference().child("recipies");
        HashMap<String, Recipe> recipies = new HashMap<>();

        recipies_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> ds = dataSnapshot.getChildren();
                for (DataSnapshot data : ds){
                    Recipe rec = data.getValue(Recipe.class);
                    recipies.put(rec.getId(), rec);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return recipies;
    }

    public HashMap<String, Ingredient> getIngedientsList(){
        //TODO
        return null;
    }

    public HashMap<String, Category> getCategoriesList(){
        //TODO
        return null;
    }

}
