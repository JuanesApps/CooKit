package icesi.i2t.cookit.lists;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import icesi.i2t.cookit.R;
import icesi.i2t.cookit.model.DataBase;
import icesi.i2t.cookit.model.Like;
import icesi.i2t.cookit.model.Recipe;
import icesi.i2t.cookit.model.modelLogic;

public class MyViewHolder extends RecyclerView.ViewHolder {

    private ImageView image_item;
    private TextView item_name;
    private LinearLayout item;
    private ImageView img_fav;
    private FirebaseAuth auth;
    private FirebaseDatabase db;
    private Recipe recipe;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        image_item = itemView.findViewById(R.id.item_image);
        item_name = itemView.findViewById(R.id.item_name);
        item = itemView.findViewById(R.id.item);
        img_fav = itemView.findViewById(R.id.img_fav);
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();

        img_fav.setOnClickListener(e -> {
            Like like = new Like();
            like.setId_var(auth.getCurrentUser().getUid());
            boolean liked = !recipe.getLikedby().containsValue(like);
            Log.e("----------->", recipe.getLikedby().containsValue(like) + "");
            if (liked){
                DatabaseReference reference =db.getReference().child("usuarios").child(auth.getCurrentUser().getUid()).child("liked").push();
                like.setId_var(recipe.getId());
                like.setId(reference.getKey());
                reference.setValue(like);
                reference = db.getReference().child("recipes").child(recipe.getId()).child("likedby").push();
                like.setId_var(auth.getCurrentUser().getUid());
                like.setId_lkusr(reference.getKey());
                reference.setValue(like);

            } else {
                for (Like l: recipe.getLikedby().values()) {
                    if (l.equals(like)){
                        like = l;
                        break;
                    }
                }
                Log.e("----------->", like.getId_lkusr() + "");
                db.getReference().child("recipes").child(recipe.getId()).child("likedby").child(like.getId_lkusr()).removeValue();
                db.getReference().child("usuarios").child(auth.getCurrentUser().getUid()).child("liked").child(like.getId()).removeValue();
            }
            like(liked);
//            like(logic.doLike(recipe.getId()));
        });

    }

    public void like(boolean liked){
        if (!liked){
            img_fav.setImageResource(R.drawable.icon_fav_empty);
            liked = true;
        }
        else {
            img_fav.setImageResource(R.drawable.icon_fav_filled);
            liked = false;
        }
    }

    public ImageView getImage_item() {
        return image_item;
    }

    public void setImage_item(ImageView image_item) {
        this.image_item = image_item;
    }

    public TextView getItem_name() {
        return item_name;
    }

    public void setItem_name(TextView item_name) {
        this.item_name = item_name;
    }

    public LinearLayout getItem() { return item; }

    public void setItem(LinearLayout item) { this.item = item; }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
