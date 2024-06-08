package com.example.myfavoriterecipeeapp;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import android.database.sqlite.SQLiteDatabase;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ShowRecipee extends AppCompatActivity {
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_recipee);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ConstraintLayout constraintLayout = new ConstraintLayout(this);
        constraintLayout.setLayoutParams(new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
        ));

        ScrollView scrollView = new ScrollView(this);
        scrollView.setLayoutParams(new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
        ));

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(16, 16, 16, 16);

        scrollView.addView(linearLayout);

        db = openOrCreateDatabase("StudentDB", Context.MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery("SELECT * FROM recipe", null);

        // Iterate through the results and create a CardView for each recipe
        while (cursor.moveToNext()) {
            String foodName = cursor.getString(0);
            String ptime = cursor.getString(1);
            String ctime = cursor.getString(2);
            String serve = cursor.getString(3);
            String ingredients = cursor.getString(4);

            CardView cardView = new CardView(this);
            LinearLayout.LayoutParams cardLayoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            cardLayoutParams.setMargins(0, 0, 0, 16);
            cardView.setLayoutParams(cardLayoutParams);
            cardView.setRadius(8);

            // Create inner ConstraintLayout
            ConstraintLayout cardInnerLayout = new ConstraintLayout(this);
            cardInnerLayout.setLayoutParams(new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
            ));
            cardInnerLayout.setPadding(4, 4, 4, 4);

            // Create ImageView
            ImageView imageView = new ImageView(this);
            imageView.setId(View.generateViewId());
            imageView.setLayoutParams(new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    200
            ));
            imageView.setImageResource(R.drawable.food_icon); // Use your drawable here

            // Create Title TextView
            TextView titleTextView = new TextView(this);
            titleTextView.setId(View.generateViewId());
            titleTextView.setLayoutParams(new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
            ));
            titleTextView.setTextAppearance(androidx.appcompat.R.style.TextAppearance_AppCompat_Large);
            titleTextView.setAllCaps(true);
            titleTextView.setText(foodName);

            // Create Subhead TextView
            TextView subheadTextView = new TextView(this);
            subheadTextView.setId(View.generateViewId());
            subheadTextView.setLayoutParams(new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
            ));
                subheadTextView.setText("Preparation time: " + ptime +"\n"+"Cooking time: " + ctime + "\n"+"Servings: " + serve);

            // Create Body TextView
            TextView bodyTextView = new TextView(this);
            bodyTextView.setId(View.generateViewId());
            bodyTextView.setLayoutParams(new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
            ));
            bodyTextView.setText("Ingredients: " + ingredients);

            // Add views to the inner ConstraintLayout
            cardInnerLayout.addView(imageView);
            cardInnerLayout.addView(titleTextView);
            cardInnerLayout.addView(subheadTextView);
            cardInnerLayout.addView(bodyTextView);

            // Apply constraints
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(cardInnerLayout);

            constraintSet.connect(imageView.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
            constraintSet.connect(imageView.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
            constraintSet.connect(imageView.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);

            constraintSet.connect(titleTextView.getId(), ConstraintSet.TOP, imageView.getId(), ConstraintSet.BOTTOM, 4);
            constraintSet.connect(titleTextView.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
            constraintSet.connect(titleTextView.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);

            constraintSet.connect(subheadTextView.getId(), ConstraintSet.TOP, titleTextView.getId(), ConstraintSet.BOTTOM, 4);
            constraintSet.connect(subheadTextView.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
            constraintSet.connect(subheadTextView.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);

            constraintSet.connect(bodyTextView.getId(), ConstraintSet.TOP, subheadTextView.getId(), ConstraintSet.BOTTOM, 4);
            constraintSet.connect(bodyTextView.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.END);
            constraintSet.applyTo(cardInnerLayout);

            // Add inner layout to CardView
            cardView.addView(cardInnerLayout);

            // Add CardView to LinearLayout
            linearLayout.addView(cardView);
        }
        cursor.close();

        // Add ScrollView to the ConstraintLayout
        constraintLayout.addView(scrollView);

        // Set ConstraintLayout as the content view
        setContentView(constraintLayout);
    }
}