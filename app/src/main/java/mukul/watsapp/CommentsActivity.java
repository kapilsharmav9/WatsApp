package mukul.watsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.HashSet;

import Model.User;

public class CommentsActivity extends AppCompatActivity {
EditText addcomment;
TextView tpost;
ImageView image_profile;
String postid;
String publisher;
FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        Toolbar toolbar=findViewById(R.id.toolbarc);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Comments");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        addcomment=findViewById(R.id.edit_comment);
        image_profile=findViewById(R.id.profile_image);
        tpost=findViewById(R.id.txt_post);
        Intent intent=getIntent();
       postid=intent.getStringExtra("postid");
       publisher=intent.getStringExtra("publisher");
       tpost.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(addcomment.getText().toString().equals(""))
               {
                   Toast.makeText(CommentsActivity.this,"You Can't Send Empty Comment",Toast.LENGTH_SHORT).show();
               }else
                   {
                       addComment();
                   }
           }
       });
       getImage();
    }
    private  void addComment()
    {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("comments").child(postid);
        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("comment",addcomment.getText().toString());
        hashMap.put("publisher",firebaseUser.getUid());
        databaseReference.push().setValue(hashMap);
        addcomment.setText("");
    }
    private  void getImage()
    {
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("users").child(postid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user=dataSnapshot.getValue(User.class);
                Glide.with(CommentsActivity.this).load(user.getImageUrl()).into(image_profile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}