package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import Model.Chat;
import Model.User;
import mukul.watsapp.R;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.Holder> {
    public static final int Msg_Type_left = 0;
    public static final int Msg_Type_Right = 1;
    private Context context;
    private ArrayList<Chat> list;
    String imageUrl;
    FirebaseUser firebaseUser;

    public MessageAdapter(Context context, ArrayList<Chat> list, String imageUrl) {
        this.context = context;
        this.list = list;
        this.imageUrl = imageUrl;
    }

    @NonNull
    @Override
    public MessageAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == Msg_Type_left) {
            View v = LayoutInflater.from(context).inflate(R.layout.chat_item_left, parent, false);
            return new Holder(v);
        } else {
            View v = LayoutInflater.from(context).inflate(R.layout.chat_item_right, parent, false);
            return new Holder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.Holder holder, int position) {
        Chat chat = list.get(position);
        holder.show_msg.setText(chat.getMessage());
        if(imageUrl.equals("default"))
        {
            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        }else
        {
            Glide.with(context).load(imageUrl).into(holder.profile_image);
        }
        if (position==list.size()-1)
        {
if (chat.isIsseen())
{
    holder.txt_seen.setText("seen");
}else
    {
        holder.txt_seen.setText("Delivered ");
    }

        }else
            holder.txt_seen.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        public TextView show_msg;
        public ImageView profile_image;
        private  TextView txt_seen;

        public Holder(@NonNull View itemView) {
            super(itemView);
            show_msg = itemView.findViewById(R.id.show_message);
            profile_image = itemView.findViewById(R.id.cprofile);
            txt_seen=itemView.findViewById(R.id.textseen);
        }
    }


    @Override
    public int getItemViewType(int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (list.get(position).getSender().equals(firebaseUser.getUid())) {
            return Msg_Type_Right;
        } else {
            return Msg_Type_left;
        }
    }

}
