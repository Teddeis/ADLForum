package com.example.adlforum;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adlforum.ui.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.adlforum.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    ImageView imageView;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

    }

    public void displayUserEmail() {
        textView = findViewById(R.id.user_email);

        // Simulating user email fetch (replace with actual logic as needed)
        User user = new User();

        if (user.getEmail() != null) {
            textView.setText(user.getEmail());
        } else {
            textView.setText("No email available");
        }
    }

    public void displayUserName() {
        textView = findViewById(R.id.username);

        // Simulating user email fetch (replace with actual logic as needed)
        User user = new User();

        if (user.getEmail() != null) {
            textView.setText(user.getUsername());
        } else {
            textView.setText("No email available");
        }
    }

    public static class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
        private ImageView imageView;

        public LoadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String imageUrl = urls[0];
            try {
                // Установка соединения и загрузка изображения
                java.net.URL url = new java.net.URL(imageUrl);
                return BitmapFactory.decodeStream(url.openStream());
            } catch (Exception e) {
                e.printStackTrace();
                return null; // Вернуть null, если произошла ошибка
            }
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                // Установить изображение в ImageView
                imageView.setImageBitmap(result);
            } else {
                // Установить изображение-заглушку при ошибке
                imageView.setImageResource(R.drawable.baseline_account_box_24);
            }
        }
    }
}
