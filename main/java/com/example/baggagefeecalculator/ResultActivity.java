package com.example.baggagefeecalculator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView tvResultWeight =
                findViewById(R.id.tvResultWeight);

        TextView tvResultCharge =
                findViewById(R.id.tvResultCharge);

        TextView tvResultTotal =
                findViewById(R.id.tvResultTotal);

        Button btnBack =
                findViewById(R.id.btnBack);

        // Receive values from MainActivity
        double excessWeight =
                getIntent().getDoubleExtra("excessWeight", 0.0);

        double charge =
                getIntent().getDoubleExtra("charge", 0.0);

        double total =
                getIntent().getDoubleExtra("total", 0.0);

        // Display the results
        tvResultWeight.setText(
                String.format(Locale.US, "%.2f kg", excessWeight));

        tvResultCharge.setText(
                String.format(Locale.US, "RM %.2f", charge));

        tvResultTotal.setText(
                String.format(Locale.US, "RM %.2f", total));

        // Return to calculator
        btnBack.setOnClickListener(v -> finish());
    }
}