package online.meetforyou.whatsappapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import online.meetforyou.whatsappapp.Models.Users;
import online.meetforyou.whatsappapp.databinding.ActivitySignupBinding;

public class signupActivity extends AppCompatActivity {
ActivitySignupBinding binding;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;
    GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(signupActivity.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("we're creating your account");

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        binding.googlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
        binding.facebookbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(signupActivity.this, "Coming Soon!", Toast.LENGTH_SHORT).show();
            }
        });

        binding.phonebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(signupActivity.this, "Coming Soon!", Toast.LENGTH_SHORT).show();
            }
        });

        if(mAuth.getCurrentUser()!=null)
        {
            Intent intent = new Intent(signupActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        binding.signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.etusername.getText().toString().isEmpty()){
                    binding.etusername.setError("Please Enter your username");
                    return;
                }
                if(binding.etemail.getText().toString().isEmpty()){
                    binding.etemail.setError("Please Enter your Email");
                    return;
                }
                if(binding.etpassword.getText().toString().isEmpty()){
                    binding.etpassword.setError("Please set your Password");
                    return;
                }
                progressDialog.show();
            mAuth.createUserWithEmailAndPassword(binding.etemail.getText().toString() , binding.etpassword.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull  Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                        Users user = new Users(binding.etusername.getText().toString(), binding.etemail.getText().toString(),binding.etpassword.getText().toString());
                        String id = task.getResult().getUser().getUid();
                        database.getReference().child("Users").child(id).setValue(user);
                        Toast.makeText(signupActivity.this, "User Created Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(signupActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                        
                    }
                    else {
                        Toast.makeText(signupActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });


            }
        });
        binding.signintext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(signupActivity.this, signinActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    int RC_SIGN_IN = 66;
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("TAG", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("TAG", "Google sign in failed", e);
            }
        }
    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Users users = new Users();
                            users.setUserid(user.getUid());
                            users.setUsername(user.getDisplayName());
                            users.setProfilepic(user.getPhotoUrl().toString());
                            database.getReference().child("Users").child(user.getUid()).setValue(users);
                            Intent intent = new Intent(signupActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(signupActivity.this, "sign in with google", Toast.LENGTH_SHORT).show();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            // updateUI(null);
                        }
                    }
                });
    }
}