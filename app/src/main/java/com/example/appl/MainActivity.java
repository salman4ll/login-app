package com.example.appl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText getUsername = (EditText) findViewById(R.id.username);
        EditText getPassword = (EditText) findViewById(R.id.password);

        String username = String.valueOf(getUsername.getText().toString());
        String password = String.valueOf(getPassword.getText().toString());

        Button btnLogin = (Button) findViewById(R.id.login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username != "admin" && password != "password"){
                    startActivity(new Intent(MainActivity.this, MainActivity3.class));
                }
                startActivity(new Intent(MainActivity.this, MainActivity2.class));
            }
        });
    }
}