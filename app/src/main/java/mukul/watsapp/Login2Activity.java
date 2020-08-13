package mukul.watsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
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

public class Login2Activity extends AppCompatActivity {
    EditText editemail, editPassword;
    Button btnlogin;
    FirebaseAuth mAuth;
    TextView textreg, texforgetpaasword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        editemail = findViewById(R.id.editemailL);
        texforgetpaasword = findViewById(R.id.textforget);
        editPassword = findViewById(R.id.editpasswordL);
        textreg = findViewById(R.id.textregister);
        btnlogin = findViewById(R.id.btnlogin);
        mAuth = FirebaseAuth.getInstance();
        loginuser();
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = editemail.getText().toString();
                String Password = editPassword.getText().toString();
                if (Email.isEmpty() | Password.isEmpty()) {
                    editemail.setError("Enter Email");
                    editemail.requestFocus();
                    editPassword.setError("Enter Password");
                    editPassword.requestFocus();
                    return;
                } else
                    mAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(Login2Activity.this, MainPageActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(Login2Activity.this, "Authentiction faild", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            }
        });
        textreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login2Activity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        texforgetpaasword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login2Activity.this,ResetPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    public void loginuser() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            Intent intent = new Intent(Login2Activity.this, MainPageActivity.class);
            startActivity(intent);

        }

    }
}