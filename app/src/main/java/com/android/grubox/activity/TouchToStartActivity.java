package com.android.grubox.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.android.grubox.R;
import com.android.grubox.adapter.TouchToStartGrid;

/**
 * Created by Utkarsh Bindal on 12/05/2017.
 */
public class TouchToStartActivity extends AppCompatActivity {
    Button button_touchToStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_touch_to_start);

        final String[] web = {
                "#masala", "#kurkure", "#CocaCola", "#healthy", "#Snacks", "#Mango", "#Chips", "#Chocolate", "#Fanta"

        };

        GridView first_grid;
        TouchToStartGrid adapter = new TouchToStartGrid(TouchToStartActivity.this, web);
        first_grid=(GridView)findViewById(R.id.grid_for_buttons);
        first_grid.setAdapter(adapter);
        first_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(TouchToStartActivity.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();

            }
        });

        //To start the menu activity
        //This has to be inside an onClickListener
        button_touchToStart = (Button) findViewById(R.id.button_start);

        button_touchToStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TouchToStartActivity.this,ProductListing.class);
                startActivity(intent);
            }
        });

    }
}
