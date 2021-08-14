package online.meetforyou.whatsappapp.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import online.meetforyou.whatsappapp.Models.MessageModel;
import online.meetforyou.whatsappapp.R;

public class ChatAdapter extends  RecyclerView.Adapter {
   ArrayList<MessageModel> messageModels;
   Context context ;
   String recId;
   int SENDER_VIEW_TYPE = 1;
   int RECIEVER_VIEW_TYPE = 2;

    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context) {
        this.messageModels = messageModels;
        this.context = context;
    }

    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context, String recId) {
        this.messageModels = messageModels;
        this.context = context;
        this.recId = recId;
    }

    @NonNull

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        if(viewType == SENDER_VIEW_TYPE){
            View view = LayoutInflater.from(context).inflate(R.layout.sample_sender, parent,false);
            return new SenderviewHolder(view);
        }
        else{
            View view = LayoutInflater.from(context).inflate(R.layout.samle_reciever, parent,false);
            return new ReciverviewHolder(view);

        }
    }


    @Override
    public int getItemViewType(int position) {
        if(messageModels.get(position).getuId().equals((FirebaseAuth.getInstance().getUid()))){


            return  SENDER_VIEW_TYPE;
        }
        else{
            return  RECIEVER_VIEW_TYPE;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull  RecyclerView.ViewHolder holder, int position) {
     MessageModel messageModel = messageModels.get(position);

     holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
         @Override
         public boolean onLongClick(View v) {
             new AlertDialog.Builder(context)
                     .setTitle("Delete")
                     .setMessage("Are you sure you want to delete this message")
                     .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialog, int which) {
                             FirebaseDatabase database = FirebaseDatabase.getInstance();
                             String senderRoom = FirebaseAuth.getInstance().getUid() + recId;
                             database.getReference().child("chats").child(senderRoom)
                                     .child(messageModel.getMessageId())
                                     .setValue(null);
                         }
                     }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {
                 dialog.dismiss();
                 }
             }).show();


             return false;
         }
     });


     if(holder.getClass() == SenderviewHolder.class){
         ((SenderviewHolder)holder).senderMsg.setText(messageModel.getMessage());

     }
     else {
         ((ReciverviewHolder)holder).recevingMsg.setText(messageModel.getMessage());

     }

    }

    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    public class  ReciverviewHolder extends RecyclerView.ViewHolder{
       TextView recevingMsg , recieverTime;
        public ReciverviewHolder(@NonNull View itemView) {
            super(itemView);
            recevingMsg = itemView.findViewById(R.id.reciverText);
            recieverTime = itemView.findViewById(R.id.reciverTime);
        }
    }



    public class  SenderviewHolder extends RecyclerView.ViewHolder{
        TextView senderMsg , senderTime;
        public SenderviewHolder(@NonNull  View itemView) {
            super(itemView);
            senderMsg = itemView.findViewById(R.id.senderText);
            senderTime = itemView.findViewById(R.id.senderTime);
        }
    }
}
