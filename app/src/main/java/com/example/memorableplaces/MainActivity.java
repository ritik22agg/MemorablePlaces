package com.example.memorableplaces;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static ArrayList<String> list = new ArrayList<>();
    static ArrayList<LatLng> location = new ArrayList<>();
    static ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.list);

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.memorableplaces", Context.MODE_PRIVATE);
        ArrayList<String> lattitudese = new ArrayList<>();
        ArrayList<String> longitudes = new ArrayList<>();

        list.clear();
        lattitudese.clear();
        longitudes.clear();
        location.clear();

        try {
            list = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("places",ObjectSerializer.serialize(new ArrayList<String>())));
            lattitudese = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("lattitudes",ObjectSerializer.serialize(new ArrayList<String>())));
            longitudes = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("longitudes",ObjectSerializer.serialize(new ArrayList<String>())));



        } catch (IOException e) {
            e.printStackTrace();
        }

        if(list.size() > 0 && lattitudese.size() > 0 && longitudes.size() > 0 ){
            if(list.size() == lattitudese.size() && lattitudese.size() == longitudes.size()){
                for(int i = 0;i < lattitudese.size();i++){
                    LatLng newer = new LatLng(Double.parseDouble(lattitudese.get(i)), Double.parseDouble(longitudes.get(i)));
                    location.add(newer);
                }
            }
        }
        else {
            list.add("Add a  new Places....");
            location.add(new LatLng(0, 0));
        }

        arrayAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, list);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
    }
}
