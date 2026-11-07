package com.example.baggagefeecalculator;

import android.text.Editable;
import android.text.TextWatcher;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etWeight;
    private EditText etRate;
    private RadioGroup rgTicketClass;
    private View btnCalculate;
    private View btnAbout;
    private View btnShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight = findViewById(R.id.etWeight);
        etRate = findViewById(R.id.etRate);
        rgTicketClass = findViewById(R.id.rgTicketClass);
        // Clear weight error when user starts typing
        etWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etWeight.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

// Clear rate error when user starts typing
        etRate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etRate.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        btnCalculate = findViewById(R.id.btnCalculate);
        btnAbout = findViewById(R.id.btnAbout);
        btnShare = findViewById(R.id.btnShare);

        // Calculate baggage fee
        btnCalculate.setOnClickListener(v -> calculateFee());

        // Open About page
        btnAbout.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    AboutActivity.class
            );
            startActivity(intent);
        });

        // Share app
        btnShare.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");

            shareIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    "Check out Baggage Fee Calculator! " +
                            "Estimate your excess baggage fees easily."
            );

            startActivity(
                    Intent.createChooser(shareIntent, "Share App")
            );
        });

    }

    private void calculateFee() {
        String weightInput = etWeight.getText().toString().trim();
        String rateInput = etRate.getText().toString().trim();

        // Check empty inputs
        boolean hasEmptyInput = false;

        if (weightInput.isEmpty()) {
            etWeight.setError("Please enter baggage weight.");
            hasEmptyInput = true;
        }

        if (rateInput.isEmpty()) {
            etRate.setError("Please enter rate per kg.");
            hasEmptyInput = true;
        }

        if (hasEmptyInput) {
            Toast.makeText(this,
                    "Please enter baggage weight and rate.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        double weight;
        double rate;

        // Check valid weight
        try {
            weight = Double.parseDouble(weightInput);
        } catch (NumberFormatException e) {
            etWeight.setError("Please enter a valid number.");
            Toast.makeText(this,
                    "Please enter valid numbers.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // Check valid rate
        try {
            rate = Double.parseDouble(rateInput);
        } catch (NumberFormatException e) {
            etRate.setError("Please enter a valid number.");
            Toast.makeText(this,
                    "Please enter valid numbers.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // Check negative values
        if (weight < 0) {
            etWeight.setError("Weight cannot be negative.");
            Toast.makeText(this,
                    "Values cannot be negative.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (rate < 0) {
            etRate.setError("Rate cannot be negative.");
            Toast.makeText(this,
                    "Values cannot be negative.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // Check ticket class
        int selectedClass = rgTicketClass.getCheckedRadioButtonId();
        double allowance;

        if (selectedClass == R.id.rbEconomy) {
            allowance = 20;
        } else if (selectedClass == R.id.rbBusiness) {
            allowance = 40;
        } else {
            Toast.makeText(this,
                    "Please select a ticket class.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // Calculate baggage fee
        double excessWeight = Math.max(0, weight - allowance);
        double charge = excessWeight * rate;
        double tax = charge * 0.08;
        double total = charge + tax;

        Intent intent = new Intent(
                MainActivity.this,
                ResultActivity.class
        );

        intent.putExtra("excessWeight", excessWeight);
        intent.putExtra("charge", charge);
        intent.putExtra("total", total);

        startActivity(intent);
    }
}
