package com.mirea.kt.ribo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    private String login;
    private String password;
    private String group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button enterBtn = findViewById(R.id.enterBtn);
        EditText etLogin = findViewById(R.id.enter_login);
        EditText etPassword = findViewById(R.id.enter_pass);
        enterBtn.setOnClickListener(v -> {
            login = etLogin.getText().toString();
            password = etPassword.getText().toString();
            if (!login.isEmpty() && !password.isEmpty()) {
                Log.i("my_task", login);
                Log.i("my_task", password);
                group = "RIBO-04-22";
                HashMap<String, String> map = new HashMap<>();
                HTTPRunnable httpRunnable = new HTTPRunnable("https://android-for-students.ru/coursework/login.php", map);
                Thread th = new Thread(httpRunnable);
                map.put("lgn", login);
                map.put("pwd", password);
                map.put("g", group);
                th.start();
                try {
                    th.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    try {
                        JSONObject jsonObject = new JSONObject(httpRunnable.getResponseBody());
                        int result = jsonObject.getInt("result_code");
                        if (result == 1) {
                            Intent intent = new Intent(this, LobbyAct.class);
                            startActivity(intent);
                            Log.i(TAG, "");
                        } else if (result == -1) {
                            Toast.makeText(getApplicationContext(), "Некор даныне", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException | NullPointerException exception) {
                        Toast.makeText(getApplicationContext(), "Сервер не отвечает", Toast.LENGTH_LONG).show();
                    }
                }
            } else {
                Toast.makeText(getApplicationContext(), "Поля пустые", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, LobbyAct.class);
                startActivity(intent);
                Log.i(TAG, "thapusk activity");
            }
        });
    }
}