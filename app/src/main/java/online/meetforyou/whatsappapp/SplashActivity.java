package online.meetforyou.whatsappapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.FirebaseDatabase;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        Thread thread = new Thread(){
            public void run() {
                try {
                    sleep(4000);

                } catch (Exception e) {
                    e.printStackTrace();

                } finally {
                    Intent intent = new Intent(SplashActivity.this, signinActivity.class);
                    startActivity(intent);
                    finish();

                }

            }
        };thread.start();
    }
}