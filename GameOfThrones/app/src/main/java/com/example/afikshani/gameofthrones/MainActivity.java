package com.example.afikshani.gameofthrones;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button lannisterButton = (Button) findViewById(R.id.lannister_btn);
        Button starkButton = (Button) findViewById(R.id.stark_btn);

        lannisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lannisterIntent = new Intent(v.getContext(), LannisterFamily.class);
                startActivity(lannisterIntent);
            }
        });

        starkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent starkIntent = new Intent(v.getContext(), StarkFamily.class);
                startActivity(starkIntent);
            }
        });

    }


}
