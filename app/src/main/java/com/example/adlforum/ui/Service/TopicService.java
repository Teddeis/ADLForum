package com.example.adlforum.ui.Service;

import static com.example.adlforum.ui.SupaBase.SupabaseConfig.SUPABASE_KEY;
import static com.example.adlforum.ui.SupaBase.SupabaseConfig.SUPABASE_URL;

import androidx.annotation.NonNull;

import com.example.adlforum.ui.SupaBase.HttpHelper;
import com.example.adlforum.ui.model.Topic;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class TopicService {

    public static void getTopics(TopicCallback callback) {
        String url = SUPABASE_URL + "/rest/v1/topics?select=id,title,content&status=eq.true";

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + SUPABASE_KEY)
                .addHeader("apikey", SUPABASE_KEY)
                .addHeader("Content-Type", "application/json")
                .build();

        HttpHelper.client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callback.onFailure("Ошибка сети: " + e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    try {
                        // Преобразование JSON в список объектов Topic
                        Type listType = new TypeToken<List<Topic>>() {}.getType();
                        List<Topic> topics = new Gson().fromJson(responseData, listType);

                        callback.onSuccess(topics);
                    } catch (Exception e) {
                        callback.onFailure("Ошибка обработки данных: " + e.getMessage());
                    }
                } else {
                    callback.onFailure("Ошибка загрузки данных: Код " + response.code());
                }
            }
        });
    }

    // Интерфейс обратного вызова
    public interface TopicCallback {
        void onSuccess(List<Topic> topics);
        void onFailure(String message);
    }
}
