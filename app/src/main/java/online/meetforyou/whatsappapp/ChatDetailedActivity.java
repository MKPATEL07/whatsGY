package online.meetforyou.whatsappapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

import online.meetforyou.whatsappapp.Adapters.ChatAdapter;
import online.meetforyou.whatsappapp.Models.MessageModel;
import online.meetforyou.whatsappapp.databinding.ActivityChatDetailedBinding;

public class ChatDetailedActivity extends AppCompatActivity {
   ActivityChatDetailedBinding binding;
   FirebaseDatabase database;
   FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatDetailedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

         getSupportActionBar().hide();

         database = FirebaseDatabase.getInstance();
         auth = FirebaseAuth.getInstance();


          final  String senderId = auth.getUid();
         String recieveId = getIntent().getStringExtra("userId");
         String  userName = getIntent().getStringExtra("userName");
         String  profilePic = getIntent().getStringExtra("profilePic");

         binding.userName.setText(userName);
        Picasso.get().load(profilePic).placeholder(R.drawable.ic_user).into(binding.profileImage);


        binding.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatDetailedActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        final ArrayList<MessageModel> messageModels = new ArrayList<>();


        final ChatAdapter chatAdapter = new ChatAdapter(messageModels , this , recieveId);
        binding.chatrecyclearview.setAdapter(chatAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.chatrecyclearview.setLayoutManager(layoutManager);


        final String senderRoom = senderId + recieveId;
        final  String receiverRoom = recieveId + senderId;

        database.getReference().child("chats")
                .child(senderRoom)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull  DataSnapshot snapshot) {
                     messageModels.clear();
                        for(DataSnapshot snapshot1 : snapshot.getChildren())
                        {
                            MessageModel model = snapshot1.getValue(MessageModel.class);
                            model.setMessageId(snapshot1.getKey());

                            messageModels.add(model);
                        }
                        chatAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull  DatabaseError error) {

                    }
                });



        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.etMessage.getText().toString().isEmpty()){
                    binding.etMessage.setError("Your message is empty!");
                    return;
                }
             String message =  binding.etMessage.getText().toString();
             final  MessageModel model = new MessageModel(senderId , message);
             model.setTimestamp(new Date().getTime());
             binding.etMessage.setText("");

             database.getReference().child("chats")
                     .child(senderRoom)
                     .push()
                     .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                 @Override
                 public void onSuccess(Void aVoid) {
                    database.getReference().child("chats")
                            .child(receiverRoom)
                            .push()
                            .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    });
                 }
             });
            }
        });
    }
}