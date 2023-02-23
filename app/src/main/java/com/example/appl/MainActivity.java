package com.example.appl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.bson.Document;
import org.bson.types.ObjectId;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;

public class MainActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_main);
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
//                        Toast.makeText(MainActivity.this, "Gagal lagi", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else {
//            Toast.makeText(this, "user is not null", Toast.LENGTH_SHORT).show();
            initializeMongoDB();
        }

        EditText getUsername = (EditText) findViewById(R.id.username);
        EditText getPassword = (EditText) findViewById(R.id.password);

        Button btnLogin = (Button) findViewById(R.id.login);
        Button btnRegist = (Button) findViewById(R.id.register);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = getUsername.getText().toString().trim();
                String password = getPassword.getText().toString().trim();

                if (user == null)
                    user = app.currentUser();

                Document query = new Document("username",username);
                mongoCollection.findOne(query).getAsync(task ->{
                    if (task.isSuccess()){
                        Document hasil = task.get();
                        if (password.equals(hasil.getString("password"))){
                            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                            startActivity(intent);
                        }else{
                            Intent intent = new Intent(MainActivity.this, MainActivity3.class);
                            startActivity(intent);
                        }

//                        Toast.makeText(MainActivity.this, hasil.getString("password"), Toast.LENGTH_SHORT).show();
                    }else{
                        Intent intent = new Intent(MainActivity.this, MainActivity3.class);
                        startActivity(intent);
                    }
                });



//                if (username.equals("admin") && password.equals("pw")){
//                    Intent intent = new Intent(MainActivity.this, MainActivity2.class);
//                    startActivity(intent);
//                }else{
//                    Intent intent = new Intent(MainActivity.this, MainActivity3.class);
//                    startActivity(intent);
//                }
            }
        });
        btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity4.class);
                startActivity(intent);
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
