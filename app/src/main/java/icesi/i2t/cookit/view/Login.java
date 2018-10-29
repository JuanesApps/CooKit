package icesi.i2t.cookit.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import icesi.i2t.cookit.R;

public class Login extends AppCompatActivity {

    private EditText et_username;
    private EditText et_password;
    private Button btn_login;
    private Button btn_sign_in;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        et_username = findViewById(R.id.et_usr);
        et_password = findViewById(R.id.et_pass);
        btn_login = findViewById(R.id.btn_login);
        btn_sign_in = findViewById(R.id.btn_sign_in);

        btn_login.setOnClickListener(e -> {
            doLogin(et_username.getText().toString(), et_password.getText().toString());
        });

        btn_sign_in.setOnClickListener(e -> {
            Intent intent = new Intent(getApplicationContext(), SignUp.class);
            startActivity(intent);
        });
    }

    private void doLogin(String correo, String contraseña) {

        auth.signInWithEmailAndPassword(correo, contraseña).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
//                    Intent i = new Intent(getApplicationContext(), Main.class);
//                    startActivity(i);
//                    finish();
                    Toast.makeText(Login.this, "Login correcto", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Login.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

}
