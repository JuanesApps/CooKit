package icesi.i2t.cookit.model;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import icesi.i2t.cookit.R;

public class MyViewHolder extends RecyclerView.ViewHolder {

    private ImageView image_item;
    private TextView item_name;
    private TextView item_description;
    private LinearLayout item;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        image_item = itemView.findViewById(R.id.item_image);
        item_name = itemView.findViewById(R.id.item_name);
        item_description = itemView.findViewById(R.id.item_description);
        item = itemView.findViewById(R.id.item);

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

    public TextView getItem_description() {
        return item_description;
    }

    public void setItem_description(TextView item_description) {
        this.item_description = item_description;
    }

    public LinearLayout getItem() { return item; }

    public void setItem(LinearLayout item) { this.item = item; }
}
