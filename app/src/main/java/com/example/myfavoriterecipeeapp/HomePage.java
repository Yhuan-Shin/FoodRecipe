package com.example.myfavoriterecipeeapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.view.View.OnClickListener;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.database.sqlite.SQLiteDatabase;

public class HomePage extends AppCompatActivity implements OnClickListener {
    EditText etName, etPTime, etCTime, etServ, etIngred;
    Button btnSubmit, btnList;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        etName = findViewById(R.id.etRecipeName);
        etPTime = findViewById(R.id.etTime);
        etCTime = findViewById(R.id.etCookingTime);
        etServ = findViewById(R.id.etServings);
        etIngred = findViewById(R.id.etIngredients);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnList = findViewById(R.id.btnList);

        db = openOrCreateDatabase("StudentDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS recipe(food_name VARCHAR, ptime VARCHAR, ctime VARCHAR, serve VARCHAR, ingredients VARCHAR);");

    }

    public void clearText(){
        etName.setText("");
        etPTime.setText("");
        etCTime.setText("");
        etServ.setText("");
        etIngred.setText("");

    }
    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    @Override
    public void onClick(View view) {
        if(view == btnList){
            Intent i = new Intent(HomePage.this, ShowRecipee.class);
            startActivity(i);
        }
        if(view == btnSubmit){
            db.execSQL("INSERT INTO recipe VALUES('"+etName.getText()+"','"+etPTime.getText()+"','"+etCTime.getText()+"','"+
                    etServ.getText()+ "','"+etIngred.getText()+"');");
            showMessage("Success", "Record added.");
            clearText();
        }
    }
}