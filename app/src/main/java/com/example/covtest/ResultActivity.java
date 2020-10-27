package com.example.covtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class ResultActivity extends AppCompatActivity {
    TextView resutText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resutText = (TextView) findViewById(R.id.textView4);
        String result = getIntent().getStringExtra("result");
        String message = "Hello hero;\n" +
                "The probability that you are infected by this virus is "+result+" unless the errors in the calculate.\n" +
                "Thank you.\n" +
                "safe your life and stay home";

        resutText.setText(message);
    }
}