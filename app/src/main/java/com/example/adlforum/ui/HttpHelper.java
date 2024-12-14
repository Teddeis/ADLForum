package com.example.adlforum.ui;
import okhttp3.*;

public class HttpHelper {
    static final OkHttpClient client = new OkHttpClient();

    public static void post(String url, String json, Callback callback) {
        RequestBody body = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + SupabaseConfig.SUPABASE_KEY)
                .addHeader("Content-Type", "application/json")
                .build();
        client.newCall(request).enqueue(callback);
    }
}
