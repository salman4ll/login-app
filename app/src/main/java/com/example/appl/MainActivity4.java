package com.example.appl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.bson.Document;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;


public class MainActivity4 extends AppCompatActivity {
    Realm uiThreadRealm;
    MongoClient mongoClient;
    MongoDatabase mongoDatabase;
    MongoCollection<Document> mongoCollection;
    User user;
    App app;
    String AppId = "application-0-ombad";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        Realm.init(this);
        app = new App(new AppConfiguration.Builder(AppId).build());
        if (app.currentUser() == null) {
//            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            app.loginAsync(Credentials.anonymous(), new App.Callback<User>() {
                @Override
                public void onResult(App.Result<User> result) {
                    if (result.isSuccess()) {
                        Log.v("User", "Sukses Login");
                        initializeMongoDB();
//                        Toast.makeText(getApplicationContext(), "Login Berhasil", Toast.LENGTH_LONG).show();
                    }else {
                        Log.v("User", "Gagal");
//                        Toast.makeText(MainActivity4.this, "Gagal lagi", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else {
//            Toast.makeText(this, "user is not null", Toast.LENGTH_SHORT).show();
            initializeMongoDB();
        }
        Button btnReg = (Button) findViewById(R.id.register1);
        EditText getUsername = (EditText) findViewById(R.id.userregis);
        EditText getPassword = (EditText) findViewById(R.id.passwordregis);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user == null){
                    user = app.currentUser();
                    Document document = new Document().append("username",getUsername.getText().toString()).append("password",getPassword.getText().toString());
                    mongoCollection.insertOne(document).getAsync(result -> {
                        if (result.isSuccess()){
//                            Toast.makeText(MainActivity4.this, "berhasil insert", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity4.this, MainActivity.class);
                            startActivity(intent);
                        }else {
//                            Toast.makeText(MainActivity4.this, "gagal", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


    }
    private void initializeMongoDB() {
        User user = app.currentUser();
        mongoClient = user.getMongoClient("mongodb-atlas");
        mongoDatabase = mongoClient.getDatabase("apk_sederhana");
        mongoCollection = mongoDatabase.getCollection("user");

    }
}