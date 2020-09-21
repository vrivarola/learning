package com.example.seccion_01;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class SecondActivity extends AppCompatActivity {
    private TextView text;
    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
         text = (TextView) findViewById(R.id.textViewMain);

         //tomar datos del intent
        Bundle bundle = getIntent().getExtras();
        if(bundle != null && bundle.getString("greeter") != null){
            String greeter = bundle.getString("greeter");
            Toast.makeText(SecondActivity.this, greeter, Toast.LENGTH_LONG).show();
            text.setText(greeter);
        } else {
            text.setText("Nothing todo here");
            Toast.makeText(SecondActivity.this, "it Empty", Toast.LENGTH_LONG).show();
        }

    }
}
