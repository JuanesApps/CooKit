package icesi.i2t.cookit.lists;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import icesi.i2t.cookit.R;

public class ViewHolderCategory extends RecyclerView.ViewHolder {

    private ImageView image_item;
    private TextView item_name;
    private LinearLayout item;
    private Boolean liked = false;

    public ViewHolderCategory(@NonNull View itemView) {
        super(itemView);

        image_item = itemView.findViewById(R.id.img_cat);
        item_name = itemView.findViewById(R.id.name_cat);
        item = itemView.findViewById(R.id.item_cat);
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
}
