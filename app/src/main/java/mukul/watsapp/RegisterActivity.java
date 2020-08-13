package mukul.watsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import Model.User;

public class RegisterActivity extends AppCompatActivity {
    EditText Efullname, Eemail, Epassword;
    Button btnregister;
    TextView textlogin;
    FirebaseAuth mauth;
    DatabaseReference reference;
    String UserName, Email, Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Eemail = findViewById(R.id.editemail);
        Efullname = findViewById(R.id.editfullname);
        Epassword = findViewById(R.id.editpassword);
        btnregister = findViewById(R.id.btnregister);
        mauth = FirebaseAuth.getInstance();
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserName = Efullname.getText().toString();
                Email = Eemail.getText().toString();
                Password = Epassword.getText().toString();
                if (UserName.isEmpty() | Email.isEmpty() | Password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please fill All Fields", Toast.LENGTH_SHORT).show();
                } else if (Password.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "Password must be Alteast 6 character", Toast.LENGTH_SHORT).show();
                } else {
                    register(UserName, Email, Password);
                }
            }
        });

    }

    private void register(final String username, String email, String password) {

        mauth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser = mauth.getCurrentUser();
                    assert firebaseUser != null;
                    String id = firebaseUser.getUid();
                    reference = FirebaseDatabase.getInstance().getReference().child("users").child(id);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("id", id);
                    hashMap.put("username", username);
                    hashMap.put("imageUrl", "default");
                    hashMap.put("status", "offline");
                    hashMap.put("search", username.toLowerCase());
//                   

                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                Intent intent = new Intent(RegisterActivity.this, MainPageActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(RegisterActivity.this, "you can't Register with this Email", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
            }
        });


    }
}