package icesi.i2t.cookit.activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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

public class vista_orden extends AppCompatActivity {

    private RecyclerView orderList;
    private FirebaseDatabase db;
    private FirebaseAuth auth;
    private ArrayList<Order> orders;
    private String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        orderList = findViewById(R.id.list_orders);
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();

        userType = getIntent().getExtras().getString("userType");

        listaOrdenes();

    }

    private void listaOrdenes(){

        db.getReference().child("orders").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> ds = dataSnapshot.getChildren();
                String ings = "";
                orders = new ArrayList<>();
                for (DataSnapshot data : ds){
                    Order ord = data.getValue(Order.class);
                    if (!userType.equals("delivery")){
                        if (ord.getIdUsuario().equals(auth.getCurrentUser().getUid())){
                            orders.add(ord);
                        }
                    } else {
                        orders.add(ord);
                    }
                }
                RecyclerAdapterOrders adapter = new RecyclerAdapterOrders(getApplicationContext(), orders, userType);
                orderList.setHasFixedSize(true);
                orderList.setAdapter(adapter);
                orderList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
