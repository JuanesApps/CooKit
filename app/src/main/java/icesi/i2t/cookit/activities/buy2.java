package icesi.i2t.cookit.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import icesi.i2t.cookit.R;
import icesi.i2t.cookit.model.Order;

public class buy2  extends AppCompatActivity {

    public static final String[] PAYMENT = {"CASH"};

    private Spinner spinnerIngs;
    private Spinner spinnerPayment;
    private TextView subtotal;
    private EditText addres;
    private TextView total;
    private ImageButton btn_maps;
    private int totalCost;
    private String ings;
    private String recipeId;
    private String recipeName;
    private Button btn_buy;
    private FirebaseDatabase db;
    private FirebaseAuth auth;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy2);


        recipeId = getIntent().getExtras().getString("recipeId");
        recipeName = getIntent().getExtras().getString("recipeName");
        ings = getIntent().getExtras().getString("ings");
        totalCost = getIntent().getExtras().getInt("cost");
        context = this;
        spinnerIngs = findViewById(R.id.spiner);
        spinnerPayment = findViewById(R.id.spiner_pay);
        subtotal = findViewById(R.id.subtotal);
        addres = findViewById(R.id.address);
        total = findViewById(R.id.total_cost);
        btn_maps = findViewById(R.id.ibtn_maps);
        btn_buy = findViewById(R.id.buy);

        db = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        String[] arr = ings.split("\n");
        String subtotal = arr[arr.length-1].split("Total cost: ")[1];
        ings = "";
        ArrayList<String> ingreds = new ArrayList<>();
        for (int i = 0; i < arr.length-2; i++) {
            String str = arr[i];
            if (str!=null || !str.equals("")){
                ingreds.add(str);
            }
        }
//        for (String str: arr) {
//            if (str!=null || !str.equals("")){
//                ingreds.add(str);
//            }
//        }
        spinnerIngs.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ingreds));
        spinnerPayment.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, PAYMENT));

        this.subtotal.setText(subtotal);
        Log.e("iiiiiiiiiiiiii", totalCost + "");
        total.setText(((totalCost+2)*1000) + "");
        btn_maps.setOnClickListener(e -> {

        });

        btn_buy.setOnClickListener(e -> {

            if (addres.getText()==null || addres.getText().equals("")){

                Toast.makeText(buy2.this, "Debe añadir una dirección", Toast.LENGTH_SHORT).show();
                return;

            }

            DatabaseReference reference = db.getReference().child("orders").push();
            Order orders = new Order();
            orders.setId(reference.getKey());
            orders.setIdUsuario(auth.getCurrentUser().getUid());
            orders.setIdReceta(recipeId);
            orders.setDireccion(addres.getText().toString());
            orders.setEstado(Order.PENDIENTE);
            orders.setCost(((totalCost+2)*1000));
            orders.setNombreReceta(recipeName);
            reference.setValue(orders);

            db.getReference().child("usuarios").child(auth.getCurrentUser().getUid()).child("orders").child(orders.getId()).setValue(orders.getId());

            Toast.makeText(buy2.this, "Orden creada", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);

        });

    }

    public void addOrder(){

    }

}
