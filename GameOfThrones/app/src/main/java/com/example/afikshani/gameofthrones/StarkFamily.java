package com.example.afikshani.gameofthrones;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class StarkFamily extends AppCompatActivity {

    private ArrayList<FamilyMember> starkFamilyTree;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stark_family);

        //Creating the recycle viewer with linear layout manager
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.stark_RV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Creating the family tree of the Lannister
        starkFamilyTree = new ArrayList<FamilyMember>();

        //Adding all the family members
        starkFamilyTree.add(new FamilyMember("Arya", ContextCompat.getDrawable(this, R.drawable.arya_stark)));
        starkFamilyTree.add(new FamilyMember("Robb", ContextCompat.getDrawable(this, R.drawable.robb_stark)));
        starkFamilyTree.add(new FamilyMember("Bran", ContextCompat.getDrawable(this, R.drawable.bran_stark)));
        starkFamilyTree.add(new FamilyMember("Catelyn", ContextCompat.getDrawable(this, R.drawable.catelyn_stark)));
        starkFamilyTree.add(new FamilyMember("Jon Snow", ContextCompat.getDrawable(this, R.drawable.jon_snow)));
        starkFamilyTree.add(new FamilyMember("Ned", ContextCompat.getDrawable(this, R.drawable.ned_stark)));
        starkFamilyTree.add(new FamilyMember("Rickon", ContextCompat.getDrawable(this, R.drawable.rickon_stark)));
        starkFamilyTree.add(new FamilyMember("Benjen", ContextCompat.getDrawable(this, R.drawable.benjen_stark)));
        starkFamilyTree.add(new FamilyMember("Sansa", ContextCompat.getDrawable(this, R.drawable.sansa_stark)));

        //Setting up adapter for the recyclerview
        ListAdapter listAdapter = new ListAdapter(starkFamilyTree);
        recyclerView.setAdapter(listAdapter);
    }
}
