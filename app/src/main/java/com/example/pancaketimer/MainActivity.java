package com.example.pancaketimer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = findViewById(R.id.button_Hello);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final TextView textView_Hello = findViewById(R.id.textView_Hello);
                final EditText editText_Name = findViewById(R.id.editText_Name);

                textView_Hello.setText("Hello " + editText_Name.getText() + "!");
            }
        });
    }
}
