package online.meetforyou.whatsappapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

import online.meetforyou.whatsappapp.Adapters.ChatAdapter;
import online.meetforyou.whatsappapp.Models.MessageModel;
import online.meetforyou.whatsappapp.databinding.ActivityGroupChatBinding;

public class GroupChatActivity extends AppCompatActivity {

    ActivityGroupChatBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGroupChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



getSupportActionBar().hide();
        binding.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupChatActivity.this , MainActivity.class);
            startActivity(intent);
            finish();

            }
        });
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final ArrayList<MessageModel> messageModels = new ArrayList<>();

        final String senderId = FirebaseAuth.getInstance().getUid();
        binding.userName.setText("Discussion Group");

        final ChatAdapter adapter = new ChatAdapter(messageModels , this);
        binding.chatrecyclearview.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.chatrecyclearview.setLayoutManager(layoutManager);

       database.getReference().child("Group Chat")
               .addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull  DataSnapshot snapshot) {
                       messageModels.clear();
                       for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                           MessageModel model = dataSnapshot.getValue(MessageModel.class);
                           messageModels.add(model);

                       }
                       adapter.notifyDataSetChanged();
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {

                   }
               });

        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.etMessage.getText().toString().isEmpty()){
                    binding.etMessage.setError("Your message is empty!");
                    return;
                }
                final String message = binding.etMessage.getText().toString();
                final MessageModel model = new MessageModel(senderId, message);
                model.setTimestamp(new Date().getTime());
                binding.etMessage.setText("");

                database.getReference().child("Group Chat")
                .push()
                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                });
            }
        });
    }


}