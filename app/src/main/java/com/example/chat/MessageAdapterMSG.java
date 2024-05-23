package com.example.chat;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapterMSG extends RecyclerView.Adapter<MessageAdapterMSG.MyViewHolder>{
    private Context context;
    private List<MessageModelMsg> messageModelList;

    public MessageAdapterMSG(Context context) {
        this.context = context;
        messageModelList = new ArrayList<>();
    }
    public void add(MessageModelMsg messageModel){
        messageModelList.add(messageModel);
        notifyDataSetChanged();
    }
    public void clear(){
        messageModelList.clear();
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_row_msg,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


MessageModelMsg messageModel= messageModelList.get(position);
holder.msg.setText(messageModel.getMessage());
holder.time.setText(messageModel.getMsgId());
if(messageModel.getSenderId().equals("me")){
    holder.main.setBackground(context.getResources().getDrawable(R.drawable.my_message));
    holder.msg.setTextColor(context.getResources().getColor(R.color.white));
    holder.M_main.setGravity(Gravity.RIGHT);
}else{
    holder.main.setBackground(context.getResources().getDrawable(R.drawable.their_message));
    holder.msg.setTextColor(context.getResources().getColor(R.color.white));
    holder.M_main.setGravity(Gravity.RIGHT);
}
    }

    @Override
    public int getItemCount() {
        return messageModelList.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView msg,time;
        private LinearLayout M_main;
        private  LinearLayout main;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            msg=itemView.findViewById(R.id.msg);
            time=itemView.findViewById(R.id.txtTime);
            main=itemView.findViewById(R.id.mainMsgLayout);
            M_main=itemView.findViewById(R.id.mainmainMsgLayout);
        }
    }
}
