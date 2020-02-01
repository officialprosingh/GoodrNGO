package online.goodr.ngo.food;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText ngo_email , ngo_password;
    private Button ngo_login , ngo_sign;
    private ImageButton arrow_login_ngo;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mAuth = FirebaseAuth.getInstance();
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }


        ngo_sign = (Button) findViewById(R.id.sign_ngo);
        ngo_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
                myWebLink.setData(Uri.parse("https://docs.google.com/forms/d/e/1FAIpQLSc6CQRKic1XLKuIog5IV2remEXK0NiwY9qz4BiPhpcVGVhq4g/viewform?usp=sf_link"));
                startActivity(myWebLink);
            }
        });

        ngo_email =  (EditText) findViewById(R.id.ngo_email);
        ngo_password = (EditText) findViewById(R.id.ngo_password);

        arrow_login_ngo = (ImageButton) findViewById(R.id.login_ngo_arrow);
        arrow_login_ngo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });

        ngo_login = (Button) findViewById(R.id.ngo_login);
        ngo_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });


    }


    private void Login(){
        String email = ngo_email.getText().toString().trim();
        String password = ngo_password.getText().toString().trim();

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            ngo_email.setError("Please Enter a Valid Email");
            ngo_email.requestFocus();
            return;
        }

        if (email.isEmpty()){
            ngo_email.setError("Email is Required");
            ngo_email.requestFocus();
            return;
        }
        if (password.isEmpty()){
            ngo_password.setError("Password is Required");
            ngo_password.requestFocus();
            return;
        }

        if (password.length()<6){
            ngo_password.setError("Maximum Length of Password Should be 6");
            ngo_password.requestFocus();
            return;
        }


        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else {

                    Toast.makeText(LoginActivity.this, "Authentication failed. Check Your Email or Password",
                            Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
