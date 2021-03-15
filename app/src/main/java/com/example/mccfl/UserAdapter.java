package com.example.mccfl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {
    private OnItemClickListener listener;


    public UserAdapter(ArrayList<User> data, OnItemClickListener listener) {
        this.uData = data;
        this.listener = listener;

    }




    Context context;
    FirebaseFirestore fb = FirebaseFirestore.getInstance();
    ArrayList<User> uData;

    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View itemIn = LayoutInflater.from(parent.getContext()).inflate(R.layout.product, parent, false);
        return new MyViewHolder(itemIn);
    }

    @Override
    public void onBindViewHolder( MyViewHolder holder, int position) {
        holder.uName.setText(uData.get(position).getUserName());
        holder.uMobile.setText(uData.get(position).getMobileNumber());
        holder.uAddress.setText(uData.get(position).getAddress());
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onclick(v, position, "Delete");

            }
        });
    }

    @Override
    public int getItemCount() {
        return uData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView uName;
        public TextView uMobile;
        public TextView uAddress;
        public Button btnDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            uName = (TextView) itemView.findViewById(R.id.username);
            uMobile = itemView.findViewById(R.id.userNumber);
            uAddress = itemView.findViewById(R.id.userAddress);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
    interface OnItemClickListener {
        void onclick(View v, int pos, String tag);
    }
}
