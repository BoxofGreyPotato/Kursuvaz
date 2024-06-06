package com.mirea.kt.ribo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;

public class InfoActivity extends AppCompatActivity {

    private String shareMessage = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {

            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        String pubDate = getIntent().getStringExtra("pubDate");
        String desc = getIntent().getStringExtra("desc");
        String link = getIntent().getStringExtra("link");
        pubDate = pubDate.split("2")[0];
        shareMessage += pubDate + "\n";
        shareMessage += desc + "\n";
        shareMessage += link;

        TextView pubDateTv = findViewById(R.id.pubDate);
        TextView linkTv = findViewById(R.id.link);
        TextView decsTv = findViewById(R.id.desc);
        pubDateTv.setText(pubDate);
        linkTv.setText(link);
        decsTv.setText(desc);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else if (item.getItemId() == R.id.share) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");

            String body = "Поделиться списком продуктов";

            intent.putExtra(Intent.EXTRA_TEXT, body);
            intent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(intent, "Share"));
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}