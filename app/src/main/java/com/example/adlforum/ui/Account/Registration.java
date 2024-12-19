package com.example.adlforum.ui.Account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.adlforum.MainActivity;
import com.example.adlforum.R;
import com.example.adlforum.ui.Service.RegService;
import com.example.adlforum.ui.model.User;

public class Registration extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword, editTextUsername;
    private Button buttonRegister, buttonBackToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registation);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextEmail = findViewById(R.id.editTextEmailAddress);
        editTextPassword = findViewById(R.id.editTextTextPassword);
        editTextUsername = findViewById(R.id.editTextUsername);

        buttonRegister = findViewById(R.id.button3);
        buttonBackToLogin = findViewById(R.id.button2);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        buttonBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registration.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void register() {
        if (editTextUsername == null || editTextEmail == null || editTextPassword == null) {
            Toast.makeText(this, "Ошибка инициализации полей ввода", Toast.LENGTH_SHORT).show();
            return;
        }

        String username = editTextUsername.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        RegService.register(this, username, email, password, new RegService.RegisterCallback() {
            @Override
            public void onSuccess(User user) {
                if (user == null) {
                    runOnUiThread(() -> Toast.makeText(Registration.this, "Ошибка при регистрации", Toast.LENGTH_SHORT).show());
                    return;
                }
                else
                runOnUiThread(() -> {
                    Toast.makeText(Registration.this, "Регистрация успешна, " + user.getEmail(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Registration.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                });
            }

            @Override
            public void onFailure(String message) {
                runOnUiThread(() -> Toast.makeText(Registration.this, message, Toast.LENGTH_SHORT).show());
            }
        });
    }
}
