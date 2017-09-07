package com.example.stiangrim.hangman;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Category extends AppCompatActivity {

    ListView categoryListView;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        categoryListView = (ListView) findViewById(R.id.category_list);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getCategories());
        categoryListView.setAdapter(adapter);


        categoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Category.this, Game.class);
                intent.putExtra("category", String.valueOf(parent.getItemAtPosition(position)));
                startActivity(intent);
            }
        });
    }

    private String[] getCategories() {
        String[] rawArray = getResources().getStringArray(R.array.category);
        ArrayList<String> categories = new ArrayList<>();

        for (String item : rawArray) {
            if (item.contains("--CATEGORY--")) {
                categories.add(item.substring(12, item.length()));
            }
        }

        return categories.toArray(new String[0]);
    }
}
