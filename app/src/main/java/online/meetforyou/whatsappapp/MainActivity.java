package online.meetforyou.whatsappapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import online.meetforyou.whatsappapp.Adapters.FragmentAdapter;
import online.meetforyou.whatsappapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
ActivityMainBinding binding;
FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();


        binding.viewpager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        binding.tablayout.setupWithViewPager(binding.viewpager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.settings:
                Intent i = new Intent(MainActivity.this , SettingsActivity.class);
                startActivity(i);
                finish();
                break;
            case R.id.logout:
            mAuth.signOut();
                Intent intent = new Intent(MainActivity.this , signinActivity.class);
                startActivity(intent);
                finish();
                break;


        }
        return true;
    }
}