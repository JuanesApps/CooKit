package icesi.i2t.cookit.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import icesi.i2t.cookit.R;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread thread = new Thread(){
            @Override
            public void run() {
                Log.e("ahksajk", "Holiholiholi");
                super.run();
                try {
                    sleep(2000);
                    GoToLogin();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();
    }

    public void GoToLogin(){
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
    }

}
