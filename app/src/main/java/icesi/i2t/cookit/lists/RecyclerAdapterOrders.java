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
import icesi.i2t.cookit.model.Order;
import icesi.i2t.cookit.model.Recipe;

public class RecyclerAdapterOrders extends RecyclerView.Adapter<ViewHolderOrders> {

    private LayoutInflater inflater;
    private ArrayList<Order> orders;
    private FirebaseAuth auth;
    private FirebaseDatabase db;
    private Context context;
    private FirebaseStorage storage;
    String[] a;


    public RecyclerAdapterOrders(Context context, ArrayList<Order> orders) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.orders = orders;
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        a = new String[100];
        for (int i = 0; i < 100; i++) {
            a[i] = "a";
        }
    }

    @NonNull
    @Override
    public ViewHolderOrders onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.order_item, viewGroup, false);
        ViewHolderOrders holder = new ViewHolderOrders(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderOrders viewHolder, int i) {
        Order current = orders.get(i);
        String c = a[i];


        viewHolder.getItem_recipe().setText(current.getNombreReceta());
        viewHolder.getItem_estado().setText(current.getEstado());

        viewHolder.getItem().setOnClickListener(action -> {
//            Intent intent = new Intent(context, RecipeName.class);
//            intent.putExtra("recipeId", current.getId());
//            context.startActivity(intent);
        });

    }


    @Override
    public int getItemCount() {
        return orders.size();
    }
}
