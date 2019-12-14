package com.yogiyulianto.pam.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yogiyulianto.pam.firebase.adapter.ChatAdater;
import com.yogiyulianto.pam.firebase.models.Chat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnSend;
    private EditText etMessage;
    private RecyclerView rvChat;
    private ChatAdater chatAdater;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference chatReference = database.getReference().child("chat");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSend = findViewById(R.id.btnSend);
        etMessage = findViewById(R.id.etMessage);
        rvChat = findViewById(R.id.rvChat);
        chatAdater = new ChatAdater(this);
        rvChat.setAdapter(chatAdater);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvChat.setLayoutManager(layoutManager);

        getChatData();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // grab text dari editText
                String message = etMessage.getText().toString();
                // buat objek chat
                Chat chat = new Chat("yogi", message);
                // kirim chat ke database
                chatReference.push().setValue(chat);
                // kosongkan inputan
                etMessage.getText().clear();
            }
        });

    }

    private void getChatData() {
        chatReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Chat> tmpChat = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    Chat chat = data.getValue(Chat.class);
                    tmpChat.add(chat);
                }

                chatAdater.setData(tmpChat);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
