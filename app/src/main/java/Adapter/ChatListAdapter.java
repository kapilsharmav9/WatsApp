package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import Model.Chat;
import mukul.watsapp.R;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.Holder> {
    ArrayList<Chat> chatList;
    Context context;

    public ChatListAdapter(ArrayList<Chat> chatList, Context context) {
        this.chatList = chatList;
        this.context = context;
    }

    @NonNull
    @Override
    public ChatListAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.chat, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListAdapter.Holder holder, int position) {
        Chat chatobject= chatList.get(position);
        holder.title.setText(chatobject.getMessage());
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView title;

        public Holder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.txttitle);
        }
    }
}
