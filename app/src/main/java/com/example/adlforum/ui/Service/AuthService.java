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

public class AuthService {

    // Добавьте параметр context в метод login
    public static void login(Context context, String email, String password, LoginCallback callback) {
        // Формируем запрос с фильтрацией по email и паролю
        String url = SUPABASE_URL + "/rest/v1/users?email=eq." + email + "&password_hash=eq." + password;

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
                        User[] users = new Gson().fromJson(responseData, User[].class);
                        if (users.length > 0) {
                            User user = users[0];
                            if (user.is_active) {
                                // Сохранение данных пользователя
                                SharedPreferences prefs = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString("email", email); // Сохраняем почту
                                editor.putString("username", user.getUsername()); // Сохраняем username
                                editor.putString("id", String.valueOf(user.getId())); // Сохраняем ID пользователя
                                editor.putString("avatar", user.getAvatarUrl()); // Сохраняем avatar пользователя
                                editor.putString("status", String.valueOf(user.is_active)); // Сохраняем avatar пользователя

                                editor.apply();

                                callback.onSuccess(user);
                            } else {
                                callback.onFailure("Пользователь не активен");
                            }
                        } else {
                            callback.onFailure("Неверный email или пароль");
                        }
                    } catch (Exception e) {
                        callback.onFailure("Ошибка обработки данных: " + e.getMessage());
                    }
                } else {
                    callback.onFailure("Не удалось выполнить вход. Код ошибки: " + response.code());
                }
            }
        });
    }

    // Интерфейс обратного вызова
    public interface LoginCallback {
        void onSuccess(User user);
        void onFailure(String message);
    }
}
