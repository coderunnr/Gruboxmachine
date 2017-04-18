package com.android.grubox.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.android.grubox.R;

public class TestMachineMain extends AppCompatActivity {

    int i=11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_est_machine_main);

        findViewById(R.id.test_machine_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                i=11;
//                for(int d=0;d<5;d++) {
//                    for (i = 10; i < 69; i++) {
//
//
//                        Handler handler = new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                Intent intent = new Intent(TestMachineMain.this, TestMachine.class);
//                                intent.putExtra("pos", i);
//                                startActivity(intent);
//                            }
//                        }, 10000);
//                    }
//                }

                Intent intent = new Intent(TestMachineMain.this, TestMachine.class);
                intent.putExtra("pos", 11);
                startActivity(intent);
            }
        });

    }
}
