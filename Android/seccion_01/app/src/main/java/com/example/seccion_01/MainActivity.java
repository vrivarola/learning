package com.example.seccion_01;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private Button btn;
    private final String Gretter = "Hello from the other side";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Acceder al segundo Activity y mandarle un String
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);//intent explicito
                intent.putExtra("greeter", Gretter);// se le pasa una clave:valor
                startActivity(intent);
            }
        });

    }
}
