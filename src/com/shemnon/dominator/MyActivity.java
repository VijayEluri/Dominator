package com.shemnon.dominator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MyActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button animalButton = (Button) findViewById(R.string.button_goto_animals);
        animalButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent editAnimals = new Intent(MyActivity.this, AnimalElements.class);
                startActivity(editAnimals);
            }
        });
    }
}
