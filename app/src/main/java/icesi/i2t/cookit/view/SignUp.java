package icesi.i2t.cookit.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import icesi.i2t.cookit.R;
import icesi.i2t.cookit.model.Usuario;

public class SignUp extends AppCompatActivity {

    private EditText et_name;
    private EditText et_last_name;
    private EditText et_email;
    private EditText et_password;
    private EditText et_cpassword;
    private Button btn_next;

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
        btn_next = findViewById(R.id.btn_next);

        btn_next.setOnClickListener(e -> {

            Usuario usr = new Usuario(et_name.getText().toString(), et_last_name.getText().toString(), et_email.getText().toString(), "");
            registerUser(usr);

        });



    }

    private void registerUser(Usuario usr) {

        if(et_password.getText().toString().length()<=5){
            Toast.makeText(this,"La contraseña tiene que tener por lo menos 6 caracteres",Toast.LENGTH_SHORT).show();
            return;
        }

        if(!et_cpassword.getText().toString().equals(et_password.getText().toString())){
            Toast.makeText(this,"Debe confirmar la contraseña",Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(usr.getEmail(), et_password.getText().toString()).
                addOnCompleteListener(this
                        , new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(SignUp.this,"Registro existoso",Toast.LENGTH_SHORT).show();
                                    usr.setUser_id(auth.getCurrentUser().getUid());
                                    DatabaseReference reference = db.getReference().child("usuarios").child(usr.getUser_id());
                                    Log.e("aaaaaa", reference.toString());
                                    reference.setValue(usr);

                                    //AQUI ME VOY PARA LA OTRA ACTIVIDAD: PERFIL
                                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(i);
                                    finish();

                                }
                            }
                        });

    }
}
