package icesi.i2t.cookit.activities;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import icesi.i2t.cookit.R;
import icesi.i2t.cookit.lists.RecyclerAdapterOrders;
import icesi.i2t.cookit.model.Ingredient;
import icesi.i2t.cookit.model.Order;

public class MyOrder extends AppCompatActivity {

    private TextView nombre;
    private TextView ingredientes;
    private TextView direccion;
    private TextView estado;
    private Button boton;
    private ProgressBar progressBar;
    private FirebaseDatabase db;
    private FirebaseAuth auth;
    private Order order;
    private String orderId;
    private String ingres;
    private String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_orden);

        nombre = findViewById(R.id.nombre);
        ingredientes = findViewById(R.id.ingredientes);
        direccion = findViewById(R.id.direccion);
        boton = findViewById(R.id.id_btns);
        progressBar = findViewById(R.id.progress_orden);
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        estado = findViewById(R.id.estado);

        orderId = getIntent().getExtras().getString("orderId");
        userType = getIntent().getExtras().getString("userType");

        getInfo();

    }

    public void getInfo() {

        db.getReference().child("orders").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> ds = dataSnapshot.getChildren();
                for (DataSnapshot data : ds) {
                    Order ord = data.getValue(Order.class);
                    if (ord.getId().equals(orderId)) {
                        order = ord;
                        break;
                    }
                }
                getIngredients();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void getIngredients() {

        db.getReference().child("ingredients").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> ds = dataSnapshot.getChildren();
                ingres = "";
                int tCost = 0;
                for (DataSnapshot data : ds) {
                    Ingredient ing = data.getValue(Ingredient.class);
                    if (ing.getRecipes().containsValue(order.getIdReceta())) {
                        String str = ing.getName();
                        String a = str.substring(0, 1);
                        String b = str.substring(1);
                        a = a.toUpperCase();
                        ingres += "-" + a + b + " " + ing.getCost() + "\n\n";
                        char[] c = ing.getCost().substring(1).toCharArray();
                        String cost = "";
                        for (int i = 0; i < c.length; i++) {
                            if (c[i] == '.') {
                                break;
                            }
                            cost = +c[i] + "";
                        }
                        tCost += Integer.parseInt(cost);
                    }
                }
                ingres += "Total cost: $" + tCost + ".000";
                setComponents();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void setComponents() {

        nombre.setText(order.getNombreReceta());
        ingredientes.setText("Ingredientes:\n\n" + ingres);
        direccion.setText("Direccion: " + order.getDireccion());
        estado.setText(order.getEstado());
        progressBar();
        if (userType.equals("delivery")){
            buton();
            boton.setOnClickListener(e -> {
                if ((order.getIdCopmrador()==null||order.getIdCopmrador().equals(auth.getCurrentUser().getUid()))) {

                    String nuevoEstado = "";
                    switch (order.getEstado()) {
                        case Order.PENDIENTE:
                            nuevoEstado = Order.ACEPTADA;
                            db.getReference().child("orders").child(orderId).child("idCopmrador").setValue(auth.getCurrentUser().getUid());
                            break;
                        case Order.ACEPTADA:
                            nuevoEstado = Order.COMPRADO;
                            break;
                        case Order.COMPRADO:
                            nuevoEstado = Order.LLEGO;
                            break;
                        case Order.LLEGO:
                            nuevoEstado = Order.ENTREGADO;
                            break;
                        case Order.ENTREGADO:
                            nuevoEstado = Order.ENTREGADO;
                            break;
                    }
                    db.getReference().child("orders").child(orderId).child("estado").setValue(nuevoEstado);
                    order.setEstado(nuevoEstado);
                    buton();
                    progressBar();
                } else {
                    Toast.makeText(MyOrder.this,"No tienes permiso para atender esta orden", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            boton.setVisibility(View.GONE);
        }

    }

    public void buton() {

        switch (order.getEstado()) {
            case Order.PENDIENTE:
                boton.setText("Aceptar");
                estado.setText(order.getEstado());
                break;
            case Order.ACEPTADA:
                boton.setText("Comprar");
                estado.setText(order.getEstado());
                break;
            case Order.COMPRADO:
                boton.setText("Llego al destino");
                estado.setText(order.getEstado());
                break;
            case Order.LLEGO:
                boton.setText("Entregado");
                estado.setText(order.getEstado());
                break;
            case Order.ENTREGADO:
                boton.setText("Entregado");
                estado.setText(order.getEstado());
                boton.setEnabled(false);
                break;
        }

    }

    public void progressBar() {

        switch (order.getEstado()) {
            case Order.PENDIENTE:
                progressBar.setProgress(0);
                estado.setBackgroundColor(Color.RED);
                break;
            case Order.ACEPTADA:
                progressBar.setProgress(25);
                estado.setBackgroundColor(Color.YELLOW);
                break;
            case Order.COMPRADO:
                progressBar.setProgress(50);
                estado.setBackgroundColor(Color.YELLOW);
                break;
            case Order.LLEGO:
                progressBar.setProgress(75);
                estado.setBackgroundColor(Color.GREEN);
                break;
            case Order.ENTREGADO:
                progressBar.setProgress(100);
                estado.setBackgroundColor(Color.GREEN);
                break;
        }
    }

}
