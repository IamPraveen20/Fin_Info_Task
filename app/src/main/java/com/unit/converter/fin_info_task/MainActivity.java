package com.unit.converter.fin_info_task;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText edt_email, edt_mobile_number;
    Button insert_btn, save_btn;
    RecyclerView recyclerView;
    DataAdapter dataAdapter;
    ArrayList<Item_model> item_List;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        insert_btn = findViewById(R.id.insert_btn);
        save_btn = findViewById(R.id.save_btn);
        edt_email = findViewById(R.id.edt_Email);
        edt_mobile_number = findViewById(R.id.edt_mobile);
        recyclerView = findViewById(R.id.recycle);

        loadData();
        save_btn.setVisibility(View.GONE);

        recyclerView.setHasFixedSize(true);
        dataAdapter = new DataAdapter(item_List);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(dataAdapter);


        insert_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_email.length() > 5 && edt_mobile_number.length() > 3) {
                    insertData(edt_email.getText().toString(), edt_mobile_number.getText().toString());
                    insert_btn.setVisibility(View.GONE);
                    save_btn.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "Data Inserted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Please fill the Fields", Toast.LENGTH_SHORT).show();
                }

            }
        });


        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                save_btn.setVisibility(View.GONE);
                insert_btn.setVisibility(View.VISIBLE);
                edt_email.setText("");
                edt_mobile_number.setText("");
                Toast.makeText(getApplicationContext(), "Data Saved", Toast.LENGTH_SHORT).show();
            }
        });
    }

          // To Load the Data

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared_preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("data_list", null);
        Type type = new TypeToken<ArrayList<Item_model>>() {
        }.getType();
        item_List = gson.fromJson(json, type);
        if (item_List == null) {
            item_List = new ArrayList<>();
        }
    }

      // Inserting the data to Adapter
    private void insertData(String email, String mobile) {
        if (item_List.contains(email) || item_List.contains(mobile)) {
            Toast.makeText(getApplicationContext(), "Already exits", Toast.LENGTH_SHORT).show();
        } else {
            item_List.add(new Item_model(email, mobile));
            dataAdapter.notifyItemInserted(item_List.size());
        }

    }

     // Saving the data to shared preferences
    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared_preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(item_List);
        editor.putString("data_list", json);
        editor.apply();
    }

}