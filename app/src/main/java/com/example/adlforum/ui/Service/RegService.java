package com.example.adlforum.ui.Service;

import static com.example.adlforum.ui.SupaBase.SupabaseConfig.SUPABASE_KEY;
import static com.example.adlforum.ui.SupaBase.SupabaseConfig.SUPABASE_URL;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.adlforum.ui.SupaBase.HttpHelper;
import com.example.adlforum.ui.model.User;
import com.google.gson.Gson;

import okhttp3.*;

import java.io.IOException;

public class RegService {

    public static void register(Context context, String username, String email, String password, RegisterCallback callback) {
        if (context == null) {
            callback.onFailure("Ошибка: контекст null");
            return;
        }

        String url = SUPABASE_URL + "/rest/v1/" + "users";

        // Создание объекта нового пользователя
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPasswordHash(password);

        Gson gson = new Gson();
        String json = gson.toJson(newUser);

        // Формирование запроса
        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
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
                if (response.body() == null) {
                    callback.onFailure("Ошибка: тело ответа от сервера пустое");
                    return;
                }

                String responseData = response.body().string();
                if (response.isSuccessful()) {
                    try {
                        // Преобразование JSON
                        User createdUser = gson.fromJson(responseData, User.class);
                        if (createdUser == null) {
                            callback.onFailure("Ошибка: невозможно преобразовать данные пользователя из JSON: " + responseData);
                            return;
                        }

                        // Сохранение всех данных пользователя
                        SharedPreferences prefs = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("email", email); // Сохраняем почту
                        editor.putString("username", createdUser.getUsername()); // Сохраняем username
                        editor.putString("id", String.valueOf(createdUser.getId())); // Сохраняем ID пользователя
                        editor.apply();

                        callback.onSuccess(createdUser);
                    } catch (Exception e) {
                        callback.onFailure("Ошибка обработки данных: " + e.getMessage() + ", JSON: " + responseData);
                    }
            } else if (response.code() == 409) { // Обработка конфликта
                    callback.onFailure("Пользователь с таким email или username уже существует.");
                } else {
                    callback.onFailure("Не удалось зарегистрироваться. Код ошибки: " + response.code());
                }
            }
        });
    }


    // Интерфейс обратного вызова для регистрации
    public interface RegisterCallback {
        void onSuccess(User user);
        void onFailure(String message);
    }
}
