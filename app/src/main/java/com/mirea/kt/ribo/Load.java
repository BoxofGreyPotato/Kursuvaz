/*package com.example.kirsuva;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class Load implements Runnable {
    private String src_name;
    private Drawable drawable;
    public Load(String src_name) {
        this.src_name = src_name;
    }
    public Drawable result(){
        return this.drawable;
    }
    @Override
    public void run() {
        try {
            InputStream is = (InputStream) new URL("https://news.store.rambler.ru/img/" + src_name).getContent();
            drawable = Drawable.createFromStream(is, src_name);
        } catch (Exception e) {
            System.out.println("Exc=" + e);
        }
    }
}*/