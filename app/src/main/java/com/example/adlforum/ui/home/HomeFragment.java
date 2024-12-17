package com.example.adlforum.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.adlforum.R;
import com.example.adlforum.databinding.FragmentHomeBinding;
import com.example.adlforum.ui.Service.TopicService;
import com.example.adlforum.ui.TopicAdapter;
import com.example.adlforum.ui.Topic.Details;
import com.example.adlforum.ui.model.Topic;

import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ListView listViewTopics;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Использование ViewBinding
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Инициализация ListView
        listViewTopics = root.findViewById(R.id.listViewTopics);

        // Загрузка тем
        loadTopics();

        return root;
    }

    private void loadTopics() {
        TopicService.getTopics(new TopicService.TopicCallback() {
            @Override
            public void onSuccess(List<Topic> topics) {
                getActivity().runOnUiThread(() -> {
                    TopicAdapter adapter = new TopicAdapter(getContext(), topics);
                    listViewTopics.setAdapter(adapter);

                    // Обработка кликов на элементы списка
                    listViewTopics.setOnItemClickListener((parent, view, position, id) -> {
                        // Получение выбранной темы
                        Topic selectedTopic = topics.get(position);

                        // Переход на DetailsActivity
                        Intent intent = new Intent(getActivity(), Details.class);
                        intent.putExtra("topic_title", selectedTopic.getTitle());
                        intent.putExtra("topic_description", selectedTopic.getContent());
                        startActivity(intent);
                    });
                });
            }

            @Override
            public void onFailure(String message) {
                getActivity().runOnUiThread(() -> {
                    Toast.makeText(getContext(), "Ошибка: " + message, Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
