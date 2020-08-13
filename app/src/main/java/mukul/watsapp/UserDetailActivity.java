package mukul.watsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class UserDetailActivity extends AppCompatActivity {
    EditText Ename;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth mAuth;
    ImageView image;
    DatabaseReference databaseReference;
    Button btnSave;
    String userid, username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        Ename = findViewById(R.id.eentername);
        btnSave = findViewById(R.id.btnsave);
        mAuth = FirebaseAuth.getInstance();
        image=findViewById(R.id.uploadimage);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Ename == null) {
                    Ename.setError("Please Enter Name");
                    Ename.requestFocus();
                    return;
                }
                if (Ename!=null)
                {
                    username = Ename.getText().toString();
                }

                firebaseUser = mAuth.getCurrentUser();
//                userid = firebaseUser.getUid();
                databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
                HashMap<String, String> hashMap = new HashMap<>();
//                hashMap.put("userid", userid);
                hashMap.put("username", username);
                hashMap.put("imageurl", "default");
                databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(UserDetailActivity.this, MainPageActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(UserDetailActivity.this, "You Can't register ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }
}