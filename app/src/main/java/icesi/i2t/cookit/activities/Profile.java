package icesi.i2t.cookit.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.HashMap;

import icesi.i2t.cookit.R;
import icesi.i2t.cookit.lists.RecyclerAdapterFeed;
import icesi.i2t.cookit.model.Recipe;
import icesi.i2t.cookit.model.User;

public class Profile extends AppCompatActivity {

    private Toolbar toolbar;
    private CircularImageView image;
    private TextView name;
    private TextView email;
    private Button but_edit;
    private RecyclerView list_recs;
    private FirebaseAuth auth;
    private FirebaseDatabase db;
    private User user;
    private ArrayList<Recipe> recipes;
    private RecyclerAdapterFeed adapterFeed;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toolbar = (Toolbar) findViewById(R.id.toolbar_profile);
        image = findViewById(R.id.img_user);
        name = findViewById(R.id.txt_show_name);
        email = findViewById(R.id.txt_show_correo);
        but_edit = findViewById(R.id.btn_edit);
        list_recs = findViewById(R.id.list_myrecipies);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();

        context = getApplicationContext();

        cargarInfo();

    }

    private void cargarInfo(){
        DatabaseReference usuarios_ref = db.getReference().child("usuarios");
        usuarios_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> ds = dataSnapshot.getChildren();
                Log.e("----------->", "Usuarios");
                for (DataSnapshot data : ds){
                    User usr = data.getValue(User.class);
                    if (usr.getUser_id().equals(auth.getCurrentUser().getUid())){
                        user = usr;
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
        String n = user.getName() + " " + user.getLast_name();
        name.setText(formatNaoe(n));
        email.setText(user.getEmail());
        HashMap<String, String> map = user.getCreated();
        DatabaseReference recipes_ref = db.getReference().child("recipes");
        recipes = new ArrayList<>();
        recipes_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> ds = dataSnapshot.getChildren();
                for (DataSnapshot data : ds){
                    Recipe rec = data.getValue(Recipe.class);
                    if (map.containsValue(rec.getId())){
                        recipes.add(rec);
                    }
                }

                adapterFeed = new RecyclerAdapterFeed(context, recipes);
                list_recs.setHasFixedSize(true);
                list_recs.setAdapter(adapterFeed);
                list_recs.setLayoutManager(new LinearLayoutManager(context));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public String formatNaoe(String name){
        String[] arr = name.split(" ");
        int n = arr.length/2;
        if (n == 0){
            return name;
        } else {
            String na = "";
            String nb = "";
            for (int i = 0; i < n; i++) {
                na += arr[i] + " ";
            }
            na = na.trim()+"\n";
            for (int i = n; i < arr.length; i++) {
                nb += arr[i] + " ";
            }
            nb = nb.trim();
            return na + nb;
        }
    }

}
