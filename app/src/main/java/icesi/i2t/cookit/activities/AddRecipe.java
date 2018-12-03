package icesi.i2t.cookit.activities;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

import icesi.i2t.cookit.R;
import icesi.i2t.cookit.lists.AddListAdapter;
import icesi.i2t.cookit.model.Category;
import icesi.i2t.cookit.model.Ingredient;
import icesi.i2t.cookit.model.UtilDomi;
import icesi.i2t.cookit.model.modelLogic;

public class AddRecipe extends AppCompatActivity {

    public final static int REQUEST_GALLERY = 0;
    private TextView nombre;
    private ListView listIngs;
    private ListView listSteps;
    private ListView listCats;
    private Button butAddIngs;
    private Button butAddSteps;
    private Button butAddCats;
    private Button butAddPhoto;
    private Button butSubmit;
    private EditText et_step;
    private AutoCompleteTextView et_ing;
    private AutoCompleteTextView et_cat;
    private ImageView foto;
    private AddListAdapter adapterIngredients;
    private AddListAdapter adapterCategories;
    private AddListAdapter adapterSteps;
    private FirebaseDatabase db;
    private FirebaseAuth auth;
    private modelLogic logic;
    private ArrayList<String> ingredients;
    private ArrayList<String> categories;
    private String imagePath;
    private FirebaseStorage storage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}
                ,11);

        nombre = findViewById(R.id.txt_nom);
        listIngs = findViewById(R.id.lista_ing);
        listSteps = findViewById(R.id.lista_pasos);
        listCats = findViewById(R.id.lista_cat);
        butAddIngs = findViewById(R.id.agregarIngrediente);
        butAddCats = findViewById(R.id.agregarCategoria);
        butAddSteps = findViewById(R.id.agregarPaso);
        butAddPhoto = findViewById(R.id.btn_agregarfoto);
        butSubmit = findViewById(R.id.btn_submit);
        et_step = findViewById(R.id.et_agregarPaso);
        et_ing = findViewById(R.id.et_agregaring);
        et_cat = findViewById(R.id.et_agregarcat);
        foto = findViewById(R.id.img_foto);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        logic = new modelLogic();

        adapterIngredients = new AddListAdapter(this);
        adapterCategories = new AddListAdapter(this);
        adapterSteps = new AddListAdapter(this);
        listIngs.setAdapter(adapterIngredients);
        listCats.setAdapter(adapterCategories);
        listSteps.setAdapter(adapterSteps);

        cargarComponentes();

    }

    public void cargarComponentes(){
        DatabaseReference recipes_ref = db.getReference().child("ingredients");
        HashMap<String, Ingredient> ingkey = new HashMap<>();
        HashMap<String, Ingredient> ingNam = new HashMap<>();
        ingredients = new ArrayList<>();
        recipes_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> ds = dataSnapshot.getChildren();
                for (DataSnapshot data : ds){
                    Ingredient rec = data.getValue(Ingredient.class);
//                    Log.e("----------->", rec.getName());
                    ingkey.put(rec.getId(), rec);
                    ingNam.put(rec.getName(), rec);
                    ingredients.add(rec.getName());
                }
                logic.refreshIngredientes(ingkey);
                logic.refreshIngredientesName(ingNam);
                cargarCategorias();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void cargarCategorias(){
        DatabaseReference recipes_ref = db.getReference().child("categories");
        HashMap<String, Category> catkey = new HashMap<>();
        HashMap<String, Category> catNam = new HashMap<>();
        categories = new ArrayList<>();
        recipes_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> ds = dataSnapshot.getChildren();
                for (DataSnapshot data : ds){
                    Category cat = data.getValue(Category.class);
//                    Log.e("----------->", rec.getName());
                    catkey.put(cat.getId(), cat);
                    catNam.put(cat.getName(), cat);
                    categories.add(cat.getName());
                }
                logic.refreshCategories(catkey);
                logic.refreshCategoriesNames(catNam);
                finishSetup();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void finishSetup(){

        ArrayAdapter<String> ad1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ingredients);
        ArrayAdapter<String> ad2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, categories);
        et_ing.setAdapter(ad1);
        et_cat.setAdapter(ad2);

        butAddIngs.setOnClickListener(e -> {
            adapterIngredients.agregarElemento(et_ing.getText().toString());
            et_ing.setText("");
        });
        butAddCats.setOnClickListener(e -> {
            adapterCategories.agregarElemento(et_cat.getText().toString());
            et_cat.setText("");
        });
        butAddSteps.setOnClickListener(e -> {
            adapterSteps.agregarElemento(et_step.getText().toString());
            et_step.setText("");
        });
        butAddPhoto.setOnClickListener(e ->{
            Intent i = new Intent();
            i.setType("image/*");
            //i.setType("video/*");
            //i.setType("*/*");
            i.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(i,REQUEST_GALLERY);
        });
        butSubmit.setOnClickListener(e ->{
            if (nombre == null || nombre.equals("")){
                Toast.makeText(AddRecipe.this, "Debe añadirle nombre a la receta", Toast.LENGTH_SHORT).show();
                return;
            }
            if (adapterIngredients.getAll().size()==0){
                Toast.makeText(AddRecipe.this, "Debe añadir los ingredientes", Toast.LENGTH_SHORT).show();
                return;
            }
            if (adapterSteps.getAll().size()==0){
                Toast.makeText(AddRecipe.this, "Debe añadir los pasos", Toast.LENGTH_SHORT).show();
                return;
            }
            if (adapterIngredients.getAll().size()==0){
                Toast.makeText(AddRecipe.this, "Debe añadir los ingredientes", Toast.LENGTH_SHORT).show();
                return;
            }
            if (imagePath == null){
                Toast.makeText(AddRecipe.this, "Debe añadir una imagen", Toast.LENGTH_SHORT).show();
                return;
            }
            ArrayList<String> steps = adapterSteps.getAll();
            for (int i = 0; i < steps.size(); i++) {
                steps.set(i, (i+1)+") " + steps.get(i));
            }
            String recId = logic.addRecipie(steps, adapterIngredients.getAll(), adapterCategories.getAll(), nombre.getText().toString());
            if(imagePath != null){
                try {
                    StorageReference ref = storage.getReference().child("recipes").child(recId);
                    FileInputStream file = new FileInputStream(new File(imagePath));
                    //Sube la foto
                    ref.putStream(file);
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_GALLERY && resultCode == RESULT_OK){
            //ACCIONES QUE HAREMOS EN 15 DIAS
            imagePath = UtilDomi.getPath(AddRecipe.this,data.getData());
            Bitmap m = BitmapFactory.decodeFile(imagePath);
            foto.setImageBitmap(m);
        }
    }

}
