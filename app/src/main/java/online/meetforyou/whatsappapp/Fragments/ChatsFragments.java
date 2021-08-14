package online.meetforyou.whatsappapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import online.meetforyou.whatsappapp.Adapters.UsersAdapter;
import online.meetforyou.whatsappapp.Models.Users;
import online.meetforyou.whatsappapp.databinding.FragmentChatsFragmentsBinding;


public class ChatsFragments extends Fragment {


    public ChatsFragments() {
        // Required empty public constructor
    }


    FragmentChatsFragmentsBinding binding;
    ArrayList<Users> list = new ArrayList<>();
    FirebaseDatabase database;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentChatsFragmentsBinding.inflate(inflater, container, false);

        database = FirebaseDatabase.getInstance();

        UsersAdapter adapter = new UsersAdapter(list , getContext());
        binding.chatrecycler.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.chatrecycler.setLayoutManager(layoutManager);

        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Users users = dataSnapshot.getValue(Users.class);
                    users.setUserid(dataSnapshot.getKey());
                    if (!users.getUserid().equals(FirebaseAuth.getInstance().getUid())) {

                        list.add(users);
                    }

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });




        return binding.getRoot();
    }
}