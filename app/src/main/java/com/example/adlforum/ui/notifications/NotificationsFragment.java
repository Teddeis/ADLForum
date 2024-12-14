package com.example.adlforum.ui.notifications;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.adlforum.databinding.FragmentNotificationsBinding;
import com.example.adlforum.ui.Login;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Создаем объект ViewModel для фрагмента
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        // Инфлятируем разметку и получаем ссылку на корневое View
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Извлекаем почту из SharedPreferences
        SharedPreferences prefs = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String email = prefs.getString("email", "Неизвестный пользователь");

        // Находим TextView и устанавливаем в него почту
        TextView emailTextView = binding.textNotifications;
        emailTextView.setText(email);

        // Находим кнопку выхода и добавляем обработчик
        Button logoutButton = binding.logoutButton;
        logoutButton.setOnClickListener(v -> logout());

        return root;
    }

    // Метод для выхода из аккаунта
    private void logout() {
        // Удаляем сохраненную почту из SharedPreferences
        SharedPreferences prefs = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("email"); // Удаляем сохраненную почту
        editor.apply();

        // Переход на экран логина
        Intent intent = new Intent(requireContext(), Login.class);
        startActivity(intent);

        // Завершаем текущую активность (опционально)
        getActivity().finish();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
