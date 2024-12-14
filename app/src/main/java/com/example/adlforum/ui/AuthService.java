package com.example.adlforum.ui;

import androidx.annotation.NonNull;

import com.example.adlforum.ui.model.User;
import com.google.gson.Gson;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import java.io.IOException;

public class AuthService {
    public static void login(String email, String password, LoginCallback callback) {
        // Подготовка JSON-запроса
        String json = new Gson().toJson(new LoginRequest(email, password));

        String url = SupabaseConfig.SUPABASE_URL + "/rest/v1/" + SupabaseConfig.USERS_TABLE;

        // Отправка POST-запроса
        HttpHelper.post(url, json, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callback.onFailure("Ошибка сети: " + e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    User user = new Gson().fromJson(responseData, User.class);
                    if (user.is_active) {
                        callback.onSuccess(user);
                    } else {
                        callback.onFailure("Пользователь не активен");
                    }
                } else {
                    callback.onFailure("Неверный email или пароль");
                }
            }
        });
    }

    // Класс для подготовки данных запроса
    static class LoginRequest {
        String email;
        String password;

        public LoginRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }

    // Интерфейс обратного вызова
    public interface LoginCallback {
        void onSuccess(User user);
        void onFailure(String message);
    }
}
