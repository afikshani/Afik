package com.example.afikshani.gameofthrones;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class LannisterFamily extends AppCompatActivity {

    private ArrayList<FamilyMember> lannisterFamilyTree;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lannister_family);

        //Creating the recycle viewer with linear layout manager
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.lannister_RV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Creating the family tree of the Lannister
        lannisterFamilyTree = new ArrayList<FamilyMember>();

        //Adding all the family members
        lannisterFamilyTree.add(new FamilyMember("Cersei", ContextCompat.getDrawable(this, R.drawable.cersei_lannister)));
        lannisterFamilyTree.add(new FamilyMember("Jaime", ContextCompat.getDrawable(this, R.drawable.jaime_lannister)));
        lannisterFamilyTree.add(new FamilyMember("Joffrey", ContextCompat.getDrawable(this, R.drawable.joffrey_lannister)));
        lannisterFamilyTree.add(new FamilyMember("Kevan", ContextCompat.getDrawable(this, R.drawable.kevan_lannister)));
        lannisterFamilyTree.add(new FamilyMember("Lancel", ContextCompat.getDrawable(this, R.drawable.lancel_lannister)));
        lannisterFamilyTree.add(new FamilyMember("Myrcella", ContextCompat.getDrawable(this, R.drawable.myrcella_lannister)));
        lannisterFamilyTree.add(new FamilyMember("Tommen", ContextCompat.getDrawable(this, R.drawable.tommen_lannister)));
        lannisterFamilyTree.add(new FamilyMember("Tyrion", ContextCompat.getDrawable(this, R.drawable.tyrion_lannister)));
        lannisterFamilyTree.add(new FamilyMember("Tywin", ContextCompat.getDrawable(this, R.drawable.tywin_lannister)));

        //Setting up adapter for the recyclerview
        ListAdapter listAdapter = new ListAdapter(lannisterFamilyTree);
        recyclerView.setAdapter(listAdapter);

    }


}
