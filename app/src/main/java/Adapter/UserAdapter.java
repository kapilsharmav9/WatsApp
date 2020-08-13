package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Model.Chat;
import Model.User;

import de.hdodenhof.circleimageview.CircleImageView;
import mukul.watsapp.MessageActivity;
import mukul.watsapp.R;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.Holder> {
   ArrayList<User> userlist;
    Context context;
    private boolean isChat;
    String thelastMessage;


    public UserAdapter( Context context,ArrayList<User> userlist,boolean isChat) {
        this.context = context;
        this.userlist = userlist;
        this.isChat=isChat;
    }

    @NonNull
    @Override
    public UserAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.user, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.Holder holder, int position) {
        final User user = userlist.get(position);
holder.username.setText(user.getUsername());
if(user.getImageUrl().equals("default"))
{
    holder.profile_image.setImageResource(R.mipmap.ic_launcher);
}else
    {
        Glide.with(context).load(user.getImageUrl()).into(holder.profile_image);

    }
if (isChat)
{

    lastMessage(user.getId(),holder.last_msg);
}else{
    holder.last_msg.setVisibility(View.GONE);
}
if (isChat)
{
    if (user.getStatus().equals("online"))
    {
        holder.statusOn.setVisibility(View.VISIBLE);
        holder.statusOf.setVisibility(View.GONE);
    }
    else
    {
        holder.statusOn.setVisibility(View.GONE);
        holder.statusOf.setVisibility(View.VISIBLE);
    }
}else
{
    holder.statusOn.setVisibility(View.GONE);
    holder.statusOf.setVisibility(View.GONE);
}

        holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(context, MessageActivity.class);
        intent.putExtra("userid",user.getId());
        context.startActivity(intent);
    }
});
    }
    @Override
    public int getItemCount() {
        return userlist.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView profile_image;
        private TextView username;
        private ImageView statusOn,statusOf;
        TextView last_msg;

        public Holder(@NonNull View itemView) {
            super(itemView);
            profile_image = itemView.findViewById(R.id.Uprofile_image);
            username = itemView.findViewById(R.id.UUserName);
            statusOf=itemView.findViewById(R.id.statusoff);
            statusOn=itemView.findViewById(R.id.statuson);
            last_msg=itemView.findViewById(R.id.lastmsg);
        }
    }
    private  void lastMessage(final  String userid, final TextView last_msg)
    {
        thelastMessage="default";
        final FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("chats");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chat chat = snapshot.getValue(Chat.class);
                    if (chat.getReceiver().equals(firebaseUser.getUid()) && chat.getSender().equals(userid)
                            || chat.getReceiver().equals(userid) && chat.getSender().equals(firebaseUser.getUid())) {
                        thelastMessage=chat.getMessage();
                    }
                }
                switch (thelastMessage)
                {
                    case "default":
                        last_msg.setText("No Message");
                        break;
                    default:
                        last_msg.setText(thelastMessage);
                        break;

                }
                thelastMessage="default";
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
