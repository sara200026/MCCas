package com.example.mccfl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText etUserName;
    EditText etNumber;
    EditText etAddress;

    LinearLayoutManager layoutManager = new LinearLayoutManager(this);

    RecyclerView rec;
    Context ctx;
    ArrayList<User> users;
    UserAdapter adapter;
    private static FirebaseFirestore fb = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etUserName = findViewById(R.id.user_name);
        users = new ArrayList<User>();


        etNumber = findViewById(R.id.user_number);
        etAddress = findViewById(R.id.user_address);
        rec = findViewById(R.id.recV);
        ctx = this;
          }

    public void saveData(View view) {
        String userName = etUserName.getText().toString();
        String number = etNumber.getText().toString();
        String address = etAddress.getText().toString();

        Map<String, Object> users = new HashMap<>();
        users.put("UserName", userName);
        users.put("MobileNumber", number);
        users.put("Address", address);
        fb.collection("Users").add(users)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.e("TAG", "Success");
                        etUserName.setText("");
                        etNumber.setText("");
                        etAddress.setText("");
                        GetUsers();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("TAG", "Failure");
                    }
                });

    }

    public void GetUsers() {

        fb.collection("Users").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if (documentSnapshots.isEmpty()) {
                            Log.d("TAG", "onSuccess: LIST EMPTY");
                            return;
                        } else {
                            for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                                if (documentSnapshot.exists()) {
                                    String id = documentSnapshot.getId();
                                    String username = documentSnapshot.getString("UserName");
                                    String userNumber = documentSnapshot.getString("MobileNumber");
                                    String userAddress = documentSnapshot.getString("Address");


                                    User user = new User(id, username, userAddress, userNumber);
                                    users.add(user);
                                    adapter = new UserAdapter(users, new UserAdapter.OnItemClickListener() {
                                        public void onclick(View v, int pos, String tag) {
                                            deleteitem(pos);
                                        }
                                    });
                                    rec.setLayoutManager(layoutManager);
                                    rec.setHasFixedSize(true);
                                    rec.setAdapter(adapter);

                                    adapter.notifyDataSetChanged();
                                    Log.e("TAG", users.toString());

                                }
                            }
                        }


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Log.e("TAG", " failed ");


            }
        });

    }

    private void deleteitem(int posi) {
        String id = users.get(posi).getId();
            fb.collection("Users").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    users.remove(posi);
                    adapter.notifyDataSetChanged();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure( Exception e) {
                    Log.d("TAG", "Delete failed");
                }
            });


    }
}