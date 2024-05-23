package com.example.chat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UserAdapterMSG extends RecyclerView.Adapter<UserAdapterMSG.MyViewHolder>{
    private Context context;
    private List<ContactModel> userModelList;

    public UserAdapterMSG(Context context) {
        this.context = context;
        userModelList = new ArrayList<>();
    }
    public void add(ContactModel userModel){
        userModelList.add(userModel);
        notifyDataSetChanged();
    }
    public void clear(){
        userModelList.clear();
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row_msg,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
ContactModel userModel= userModelList.get(position);
holder.name.setText(userModel.name);
holder.email.setText(userModel.phone_no);
holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, ChatActivityMSG.class);
        intent.putExtra("id",userModel.phone_no);
        context.startActivity(intent);
    }
});
    }

    @Override
    public int getItemCount() {
        return userModelList.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView name,email;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.txtName);
            email=itemView.findViewById(R.id.txtEmail);
        }
    }
}
