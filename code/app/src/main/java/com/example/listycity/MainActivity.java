package com.example.listycity;

import static com.google.android.material.internal.ViewUtils.hideKeyboard;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    // Declaring the variables
    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;

    Button add_city, delete_city, confirm;
    EditText city_input;

    int selectedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        setTitle(R.string.app_name);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        cityList = findViewById(R.id.city_list);
        String []cities = {"Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin", "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi"};
        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        //Buttons
        add_city = findViewById(R.id.add_city);
        delete_city = findViewById(R.id.delete_city);
        confirm = findViewById(R.id.confirm);
        city_input = findViewById(R.id.city_input);

        //hidden
        city_input.setVisibility(View.GONE);
        confirm.setVisibility(View.GONE);

        //delete is not working until something is selected
        delete_city.setEnabled(false);
        //select item for deletion
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position;
            delete_city.setEnabled(true);
        });

        //show input
        add_city.setOnClickListener(v -> {
            city_input.setVisibility(View.VISIBLE);
            confirm.setVisibility(View.VISIBLE);
            city_input.requestFocus();
        });
        //ADD city
        confirm.setOnClickListener(v -> {
            String cityName = city_input.getText().toString();
            if (!cityName.isEmpty()) {
                dataList.add(cityName);
                cityAdapter.notifyDataSetChanged();//refresh the listview
            }
            city_input.setText("");
            city_input.setVisibility(View.GONE);
            confirm.setVisibility(View.GONE);
        });

        //DELETE city
        delete_city.setOnClickListener(v -> {
            if (selectedPosition != -1) {
                dataList.remove(selectedPosition);
                cityAdapter.notifyDataSetChanged();
                selectedPosition = -1;
                delete_city.setEnabled(false);
            }
        });
    }
}