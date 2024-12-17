package com.example.adlforum.ui.Topic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.adlforum.MainActivity;
import com.example.adlforum.R;
import com.example.adlforum.ui.Account.Login;
import com.example.adlforum.ui.Account.Registration;

public class Details extends AppCompatActivity {

    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Получение данных из Intent
        String title = getIntent().getStringExtra("topic_title");
        String description = getIntent().getStringExtra("topic_description");

        // Установка данных в TextView
        TextView textViewTitle = findViewById(R.id.topic_title_details);
        TextView textViewDescription = findViewById(R.id.topic_content_details);

        textViewTitle.setText(title);
        textViewDescription.setText(description);


        button2 = findViewById(R.id.button2);


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Details.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}