package com.example.baggagefeecalculator;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ImageView btnBackAbout = findViewById(R.id.btnBackAbout);

        btnBackAbout.setOnClickListener(v -> {
            finish();
        });

        Button btnWebsite = findViewById(R.id.btnWebsite);

        btnWebsite.setOnClickListener(v -> {
            Intent intent = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.uitm.edu.my/")
            );

            startActivity(intent);
        });
    }
}
