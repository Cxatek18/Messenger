package com.team.messenger;

import androidx.annotation.NonNull;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private EditText editTextForgotPasswordEmail;
    private Button buttonResetPassword;
    private ForgotPasswordViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        initViews();
        viewModel = new ViewModelProvider(this).get(ForgotPasswordViewModel.class);
        observeViewModel();
        buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailUser = editTextForgotPasswordEmail.getText().toString().trim();
                if(emailUser.isEmpty()){
                    Toast.makeText(
                            ForgotPassword.this,
                            "Поле email должно быть заполнено",
                            Toast.LENGTH_SHORT
                    ).show();
                }else {
                    viewModel.resetPassword(emailUser);
                }
            }
        });
    }

    private void observeViewModel(){
        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                if(errorMessage != null){
                    Toast.makeText(
                            ForgotPassword.this,
                            errorMessage,
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });
        viewModel.isSuccess().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean success) {
                if(success){
                    Toast.makeText(
                            ForgotPassword.this,
                            R.string.reset_link_sent,
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });
    }

    private void initViews(){
        editTextForgotPasswordEmail = findViewById(R.id.editTextForgotPasswordEmail);
        buttonResetPassword = findViewById(R.id.buttonResetPassword);
    }

    public static Intent newIntent(Context context){
        return new Intent(context, ForgotPassword.class);
    }
}