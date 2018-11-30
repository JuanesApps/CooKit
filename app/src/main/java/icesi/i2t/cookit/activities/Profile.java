package icesi.i2t.cookit.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;

import icesi.i2t.cookit.R;

public class Profile extends AppCompatActivity {

    private Toolbar toolbar;
    private CircularImageView image;
    private TextView name;
    private TextView email;
    private Button but_edit;
    private RecyclerView list_recs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toolbar = (Toolbar) findViewById(R.id.toolbar_profile);
        image = findViewById(R.id.img_user);
        name = findViewById(R.id.txt_show_name);
        email = findViewById(R.id.txt_show_correo);
        but_edit = findViewById(R.id.btn_edit);
        list_recs = findViewById(R.id.list_myrecipies);


    }

}
