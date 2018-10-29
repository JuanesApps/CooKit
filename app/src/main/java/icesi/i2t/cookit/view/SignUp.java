package icesi.i2t.cookit.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import icesi.i2t.cookit.R;

public class SignUp extends AppCompatActivity {

    public EditText et_name;
    public EditText et_last_name;
    public EditText et_email;
    public EditText et_password;
    public EditText et_cpassword;

    private FirebaseAuth auth;
    private FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        db = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        et_name = findViewById(R.id.et_name);
        et_last_name = findViewById(R.id.et_last_name);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        et_cpassword = findViewById(R.id.et_cpassword);



    }
}
