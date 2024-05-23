package com.example.chat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder>{
    private Context context;
    private List<MessageModel> messageModelList;

    public MessageAdapter(Context context) {
        this.context = context;
        messageModelList = new ArrayList<>();
    }
    public void add(MessageModel messageModel){
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
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_row,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


MessageModel messageModel= messageModelList.get(position);
holder.msg.setText(messageModel.getMessage());
holder.time.setText(messageModel.getTime());
if(messageModel.getSenderId().equals(FirebaseAuth.getInstance().getUid())){
   // holder.main.setBackgroundColor(context.getResources().getColor(R.color.send));
    holder.main.setBackground(context.getResources().getDrawable(R.drawable.my_message));
    holder.msg.setTextColor(context.getResources().getColor(R.color.white));
    holder.M_main.setGravity(Gravity.RIGHT);
}else{
    //holder.main.setBackgroundColor(context.getResources().getColor(R.color.received));
    holder.main.setBackground(context.getResources().getDrawable(R.drawable.their_message));
    holder.msg.setTextColor(context.getResources().getColor(R.color.white));
    holder.M_main.setGravity(Gravity.LEFT);
}

holder.M_main.setOnLongClickListener(new View.OnLongClickListener() {
    @Override
    public boolean onLongClick(View v) {
        AlertDialog.Builder builder =new AlertDialog.Builder(context)
                .setTitle("Delete Message")
                .setMessage("Delete message for everyone")
                .setIcon(R.drawable.baseline_delete_24)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseReference delData1= FirebaseDatabase.getInstance().getReference().child("chats").child(messageModel.getSenderId()).child(messageModel.getReceiverId()).child(messageModel.getMsgId());
                        DatabaseReference delData2= FirebaseDatabase.getInstance().getReference().child("chats").child(messageModel.getReceiverId()).child(messageModel.getSenderId()).child(messageModel.getMsgId());
                    delData1.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                        }
                    });
                        delData2.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }
                        });
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
        return true;
    }
});



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
