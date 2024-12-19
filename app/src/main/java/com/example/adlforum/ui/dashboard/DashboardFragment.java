package com.example.adlforum.ui.dashboard;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.adlforum.R;
import com.example.adlforum.ui.Service.TopicCreateService;
import com.example.adlforum.ui.Service.TopicService;
import com.example.adlforum.ui.model.Topic;

public class DashboardFragment extends Fragment {

    private EditText editTextTitle, editTextContent;
    private Button buttonCreateTopic;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        // Инициализация полей и кнопки
        editTextTitle = root.findViewById(R.id.editTextTitle);
        editTextContent = root.findViewById(R.id.editTextContent);
        buttonCreateTopic = root.findViewById(R.id.button);

        // Установка слушателя для кнопки
        buttonCreateTopic.setOnClickListener(v -> createNewTopic());

        return root;
    }

    private void createNewTopic() {
        String title = editTextTitle.getText().toString().trim();
        String content = editTextContent.getText().toString().trim();

        if (title.isEmpty() || content.isEmpty()) {
            Toast.makeText(getContext(), "Заполните все поля!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Создание нового объекта Topic
        Topic newTopic = new Topic(getContext(),title,content);
        newTopic.setTitle(title);
        newTopic.setContent(content);

        // Вызов метода для создания темы
        TopicCreateService.createTopic(newTopic, new TopicCreateService.CreateTopicCallback() {
            @Override
            public void onSuccess(String message) {
                getActivity().runOnUiThread(() -> {
                    Toast.makeText(getContext(), "Тема успешно отправлена на модерацию!", Toast.LENGTH_SHORT).show();
                    editTextTitle.setText("");
                    editTextContent.setText("");
                });
            }

            @Override
            public void onFailure(String message) {
                getActivity().runOnUiThread(() ->
                        Toast.makeText(getContext(), "Ошибка: " + message, Toast.LENGTH_SHORT).show());
            }
        });
    }
}
