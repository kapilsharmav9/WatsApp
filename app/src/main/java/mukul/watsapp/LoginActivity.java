package mukul.watsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import Model.UserObject;

public class LoginActivity extends AppCompatActivity {
    EditText enumber, ecode;
    Button btnVerify;
    Spinner spiner;
    String verificationid;
    String phoneNumber, EnterNumber, otp;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        initview();
        database = FirebaseFirestore.getInstance();
//        userLogin();
        spiner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, mukul.atsapp.CountryData.countryNames));
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //progressBar.setVisibility(View.VISIBLE);
                String code = mukul.atsapp.CountryData.countryAreaCodes[spiner.getSelectedItemPosition()];
                EnterNumber = enumber.getText().toString().trim();
                if (EnterNumber.isEmpty() || EnterNumber.length() < 10) {
                    enumber.setError("Valid number is required");
                    enumber.requestFocus();
                    return;
                }

                phoneNumber = "+" + code + EnterNumber;
                sendVerificationCode(phoneNumber);



            }
        });


    }
//
//    private void initview() {
//        enumber = findViewById(R.id.editnumber);
//        ecode = findViewById(R.id.editcode);
//        btnVerify = findViewById(R.id.btnverify);
//        spiner = findViewById(R.id.spiner);
//        mAuth = FirebaseAuth.getInstance();
//        progressBar = findViewById(R.id.progressbar);
//    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationid, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            UserObject ub = new UserObject();
                            Map<String, Object> user = new HashMap<>();
                            user.put("name", ub.getName());
                            user.put("number", ub.getNumber());

//                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            database.collection("contacts").document().set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    Toast.makeText(LoginActivity.this, "not inserted", Toast.LENGTH_LONG).show();
                                }
                            });
                        } else {
                            Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                });
    }

    private void sendVerificationCode(String number) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationid = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                progressBar.setVisibility(View.VISIBLE);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

        }
    };

    @Override
    protected void onStart() {
        super.onStart();

//        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
//            Intent intent = new Intent(this, UserDetailActivity.class);
//         intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//        }
    }

//    private void userLogin() {
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//
//            if (user != null) {
//                Intent intent = new Intent(getApplicationContext(), UserDetailActivity.class);
//                startActivity(intent);
//
//
//            }
//
//
//
//    }
}
