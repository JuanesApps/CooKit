package icesi.i2t.cookit.lists;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.CompletionInfo;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import icesi.i2t.cookit.R;

public class AddListAdapter extends BaseAdapter {

    ArrayList<String> list;
    Context context;
    private String type;

    public AddListAdapter(Context context){
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            convertView = inflater.inflate(R.layout.add_list_item,null);
        }

        TextView editTextInfo = convertView.findViewById(R.id.et_item);
        Button delete_btn = convertView.findViewById(R.id.but_delete);
        editTextInfo.setText((position+1)+") " + list.get(position));
        delete_btn.setOnClickListener(e -> {
            list.remove(position);
            notifyDataSetChanged();
        });

        return convertView;
    }

    public void clear() {
        list.clear();
    }


    public void addItem(String p) {
        list.add(p);
    }

    public void agregarElemento(String c) {
        list.add(c);
        notifyDataSetChanged();
    }

    public ArrayList<String> getAll(){
        return list;
    }

}
