package icesi.i2t.cookit.lists;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import icesi.i2t.cookit.R;
import icesi.i2t.cookit.model.Like;
import icesi.i2t.cookit.model.Order;
import icesi.i2t.cookit.model.Recipe;

public class ViewHolderOrders extends RecyclerView.ViewHolder {

    private TextView item_recipe;
    private TextView item_estado;
    private LinearLayout item;
    private FirebaseAuth auth;
    private FirebaseDatabase db;
    private Order order;

    public ViewHolderOrders(@NonNull View itemView) {
        super(itemView);

        item_recipe = itemView.findViewById(R.id.txt_receta);
        item_estado = itemView.findViewById(R.id.txt_estado);
        item = itemView.findViewById(R.id.id_item);
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();

    }

    public TextView getItem_recipe() {
        return item_recipe;
    }

    public void setItem_recipe(TextView item_recipe) {
        this.item_recipe = item_recipe;
    }

    public TextView getItem_estado() {
        return item_estado;
    }

    public void setItem_estado(TextView item_estado) {
        this.item_estado = item_estado;
    }

    public LinearLayout getItem() {
        return item;
    }

    public void setItem(LinearLayout item) {
        this.item = item;
    }

    public FirebaseAuth getAuth() {
        return auth;
    }

    public void setAuth(FirebaseAuth auth) {
        this.auth = auth;
    }

    public FirebaseDatabase getDb() {
        return db;
    }

    public void setDb(FirebaseDatabase db) {
        this.db = db;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}

