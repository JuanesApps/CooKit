package icesi.i2t.cookit.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import icesi.i2t.cookit.R;
import icesi.i2t.cookit.model.Ingredient;
import icesi.i2t.cookit.model.Recipe;

public class buy1 extends AppCompatActivity {

    private Recipe recipe;
    private TextView recipe_name;
    private TextView list_ings;
    private Button but_continuar;
    private FirebaseDatabase db;
    private int tCost;
    private String ings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy1);

        recipe_name = findViewById(R.id.name_recipe);
        list_ings = findViewById(R.id.list_ings);
        but_continuar = findViewById(R.id.but_continue);

        db = FirebaseDatabase.getInstance();

        String recipeId = getIntent().getExtras().getString("recipeId");

        buscarInfo(recipeId);

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

    public void ponerInfo(){
        recipe_name.setText(recipe.getName());
        obtenerIngredientes();
        but_continuar.setOnClickListener(e ->{
            Intent intent = new Intent(getApplicationContext(), buy2.class);
            intent.putExtra("recipeId", recipe.getId());
            intent.putExtra("recipeName", recipe.getName());
            intent.putExtra("ings", ings);
            intent.putExtra("cost", tCost);
            startActivity(intent);
        });
    }

    public void obtenerIngredientes(){
        DatabaseReference ingredients_ref = db.getReference().child("ingredients");
        ArrayList<Ingredient> recIngredients = new ArrayList<>();
        ingredients_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> ds = dataSnapshot.getChildren();
                ings = "";
                tCost = 0;
                for (DataSnapshot data : ds){
                    Ingredient ing = data.getValue(Ingredient.class);

                    if (recipe.getIngredient().containsValue(ing.getId())){
                        String str = ing.getName();
                        String a = str.substring(0,1);
                        String b = str.substring(1);
                        a = a.toUpperCase();
                        ings += "-" + a+b + " " + ing.getCost() + "\n\n";
                        char[] c = ing.getCost().substring(1).toCharArray();
                        String cost = "";
                        for (int i = 0; i < c.length; i++) {
                            if (c[i]=='.'){
                                break;
                            }
                            cost =+ c[i]+"";
                        }
                        tCost += Integer.parseInt(cost);
                        recIngredients.add(ing);
                    }
                }
                ings += "Total cost: $" + tCost + ".000";
                list_ings.setText(ings);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
