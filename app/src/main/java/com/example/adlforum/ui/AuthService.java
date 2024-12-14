package com.example.adlforum.ui;

import static com.example.adlforum.ui.SupabaseConfig.SUPABASE_KEY;
import static com.example.adlforum.ui.SupabaseConfig.USERS_TABLE;
import static com.example.adlforum.ui.SupabaseConfig.SUPABASE_URL;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.adlforum.ui.model.User;
import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;

public class AuthService {

    // Добавьте параметр context в метод login
    public static void login(Context context, String email, String password, LoginCallback callback) {
        String url = SUPABASE_URL + "/rest/v1/" + USERS_TABLE + "?email=eq." + email;

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
                                // Сохранение почты
                                SharedPreferences prefs = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString("email", email); // Сохраняем почту
                                editor.apply();

                                callback.onSuccess(user);
                            } else {
                                callback.onFailure("Пользователь не активен");
                            }
                        } else {
                            callback.onFailure("Пользователь не найден");
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
