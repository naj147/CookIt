package com.example.naj_t.cookitv1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.naj_t.cookitv1.Utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by naj_t on 09/05/2018.
 */

public class LoginActivity extends AppCompatActivity {
    private Animation animShow, animHide;
    String TAG="LoginActivity";
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mAuth=FirebaseAuth.getInstance();
        FirebaseUser user =mAuth.getCurrentUser();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if(user==null){
            initAnimation();
            setupViews();
        }
        else {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            finish();
                        }
                    }, 300);
                }
            }, 500);
        }

    }
    void setupViews(){
        LinearLayout signin,signup;
         final TextInputLayout emailInput1,passInput1,emailInput2,passInput2,passConfInput;
        Button signIn1,signUp1,signIn2,signUp2;
        final ViewFlipper simpleViewFlipper;
        final TextInputEditText emailText1,emailText2,passText1,passText2,passConfText;
        signin=findViewById(R.id.layout1);
        signup=findViewById(R.id.layout2);
        signin.setVisibility(View.VISIBLE);
        signup.setVisibility(View.INVISIBLE);
        signIn1=signin.findViewById(R.id.signIn1);
        signUp1=signin.findViewById(R.id.signup1);
        simpleViewFlipper = findViewById(R.id.simpleViewFlipper);
        simpleViewFlipper.setInAnimation(animShow);
        simpleViewFlipper.setOutAnimation(animHide);
        signUp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleViewFlipper.showNext();
            }
        });
        signIn2=signup.findViewById(R.id.signin2);
        signUp2=signup.findViewById(R.id.signup2);
        signIn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleViewFlipper.showNext();
            }
        });
        emailInput1 = signin.findViewById(R.id.emailInput);
        passInput1 = signin.findViewById(R.id.passInput);
        emailInput2 = signup.findViewById(R.id.emailInput);
        passInput2 = signup.findViewById(R.id.passInput);
        passConfInput = signup.findViewById(R.id.passConfInput);
        emailText1=signin.findViewById(R.id.email);
        emailText2=signup.findViewById(R.id.email);
        passText1=signin.findViewById(R.id.pass);
        passText2=signup.findViewById(R.id.pass);
        passConfText=signup.findViewById(R.id.passConf);
        signIn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login (emailText1,passText1,emailInput1,passInput1);
            }
        });
        signUp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register(emailText2,passText2,passConfText,emailInput2,passInput2,passConfInput);
            }
        });
    }
    void login(TextInputEditText emailText,TextInputEditText passText,TextInputLayout emailInput, TextInputLayout passInput){

        String email= emailText.getText().toString();
        String pass= passText.getText().toString();
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailInput.setError("Enter a valid email");
            return;
        }else{
            emailInput.setError(null);
        }
        if(!Utils.isPassword(pass)){
            passInput.setError("Invalid password: password must have 8 characters,1 uppercase, 1 lowercase and a digit");
            return;
        }else {
            passInput.setError(null);
        }
        signin(email,pass);
    }
    void register(TextInputEditText emailText,TextInputEditText passText,TextInputEditText passConfText,TextInputLayout emailInput, TextInputLayout passInput,TextInputLayout passConfInput){

        String email= emailText.getText().toString();
        String pass= passText.getText().toString();
        String passConf= passConfText.getText().toString();
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailInput.setError("Enter a valid email");
            return;
        }else{
            emailInput.setError(null);
        }
        if(!Utils.isPassword(pass)){
            passInput.setError("Invalid password: password must have 8 characters,1 uppercase, 1 lowercase and a digit");
            return;
        }else {
            passInput.setError(null);
        }
        if(!Utils.isPassword(passConf)){
            passConfInput.setError("Invalid password: password must have 8 characters,1 uppercase, 1 lowercase and a digit");
            return;
        }else {
            if(!Utils.isPasswordConf(pass,passConf)){
                passConfInput.setError("The password and confirmation password don't match!");
                return;
            }
            passConfInput.setError(null);
        }
        signup(email,pass);
    }

    private void initAnimation()
    {
        animShow = AnimationUtils.loadAnimation( this, R.anim.view_show);
        animHide = AnimationUtils.loadAnimation( this, R.anim.view_hide);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    protected void updateUI(FirebaseUser user){
        if(user!=null){
            Toast.makeText(LoginActivity.this, "Authentication succeeded, email :"+user.getEmail()+ " \nid: "+user.getUid(),
                    Toast.LENGTH_LONG).show();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }

    }
    protected  void signup(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });

    }
    protected  void signin(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }


                    }
                });

    }
    @Override
    protected void onStart() {
        super.onStart();
    }
}
