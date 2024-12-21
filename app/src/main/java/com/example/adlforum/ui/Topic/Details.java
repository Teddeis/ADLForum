package com.example.adlforum.ui.Topic;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adlforum.MainActivity;
import com.example.adlforum.R;
import com.example.adlforum.ui.CommentAdapter;
import com.example.adlforum.ui.Service.CommentService;
import com.example.adlforum.ui.model.Comment;

import java.util.ArrayList;
import java.util.List;

public class Details extends AppCompatActivity {

    private Button button2;
    private RecyclerView recyclerViewComments;
    private String topicId;

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
        topicId = getIntent().getStringExtra("id");
        String title = getIntent().getStringExtra("topic_title");
        String description = getIntent().getStringExtra("topic_description");

        // Установка данных в TextView
        TextView sss = findViewById(R.id.topic_ds_s);
        TextView textViewTitle = findViewById(R.id.topic_title_details);
        TextView textViewDescription = findViewById(R.id.topic_content_details);

        sss.setText(topicId);
        textViewTitle.setText(title);
        textViewDescription.setText(description);

        // Инициализация RecyclerView
        recyclerViewComments = findViewById(R.id.topic_commentlist_details);

        // Установка LayoutManager для RecyclerView
        recyclerViewComments.setLayoutManager(new LinearLayoutManager(this));

        // Загрузка комментариев
        loadComments();

        // Обработчик кнопки возврата
        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(v -> {
            Intent intent = new Intent(Details.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void loadComments() {
        try {
            CommentService.getComment(new CommentService.CommentCallback() {
                @Override
                public void onSuccess(List<Comment> comments) {
                    if (comments == null) {
                        runOnUiThread(() -> Toast.makeText(Details.this, "Нет данных", Toast.LENGTH_SHORT).show());
                        return;
                    }

                    List<Comment> filteredComments = new ArrayList<>();
                    for (Comment comment : comments) {
                        if (topicId != null && String.valueOf(comment.getIdTopics()).equals(topicId)) {
                            filteredComments.add(comment);
                        }
                    }

                    runOnUiThread(() -> {
                        if (!filteredComments.isEmpty()) {
                            // Создание и установка адаптера для RecyclerView
                            CommentAdapter adapter = new CommentAdapter(Details.this, filteredComments);
                            recyclerViewComments.setAdapter(adapter);
                        } else {
                            Toast.makeText(Details.this, "Комментарии отсутствуют.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onFailure(String message) {
                    runOnUiThread(() -> Toast.makeText(Details.this, "Ошибка загрузки комментариев: " + message, Toast.LENGTH_SHORT).show());
                }
            });
        } catch (Exception e) {
            runOnUiThread(() -> Toast.makeText(Details.this, "Произошла ошибка: " + e.getMessage(), Toast.LENGTH_LONG).show());
        }
    }
}
