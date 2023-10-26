package com.team.messenger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextRegisterEmail;
    private EditText editTextRegisterPassword;
    private EditText editTextRegisterName;
    private EditText editTextRegisterLastName;
    private EditText editTextRegisterOld;
    private Button buttonSignUp;
    private RegistrationViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();
        viewModel = new ViewModelProvider(this).get(RegistrationViewModel.class);
        observeViewModel();

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = getTrimnedValue(editTextRegisterEmail);
                String password = getTrimnedValue(editTextRegisterPassword);
                String name = getTrimnedValue(editTextRegisterName);
                String lastName = getTrimnedValue(editTextRegisterLastName);
                int age = Integer.parseInt(getTrimnedValue(editTextRegisterOld));
                viewModel.signUp(email, password, name, lastName, age);
            }
        });
    }

    private void observeViewModel(){
        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                if(errorMessage != null){
                    Toast.makeText(
                            RegisterActivity.this,
                            errorMessage,
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });

        viewModel.getUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if(firebaseUser != null){
                    Intent intent = UserActivity.newIntent(RegisterActivity.this, firebaseUser.getUid());
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private String getTrimnedValue(EditText text){
        return text.getText().toString().trim();
    }

    private void initViews(){
        editTextRegisterEmail = findViewById(R.id.editTextRegisterEmail);
        editTextRegisterPassword = findViewById(R.id.editTextRegisterPassword);
        editTextRegisterName = findViewById(R.id.editTextRegisterName);
        editTextRegisterLastName = findViewById(R.id.editTextRegisterLastName);
        editTextRegisterOld = findViewById(R.id.editTextRegisterOld);
        buttonSignUp = findViewById(R.id.buttonSignUp);
    }

    public static Intent newIntent(Context context){
        return new Intent(context, RegisterActivity.class);
    }
}