package com.android.grubox.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.android.grubox.R;

import java.math.BigInteger;

public class CashCommunicate extends AppCompatActivity {

    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_communicate);

       // openCash();

        editText=(EditText)findViewById(R.id.edit_cashcommunicate);

//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                checkCash();
//            }
//        }, 3000);

        findViewById(R.id.check_cash).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s=editText.getText().toString();
                byte[] b = new BigInteger(s,16).toByteArray();

              //  System.out.print(String.format("%x", Byte.toUnsignedInt(b[1])));
                Intent intent=new Intent(CashCommunicate.this, GrucardActivity.class);
//        intent.putExtra("byte",new byte[]{0x01,0x01,0x02,0x03});
//        intent.putExtra("byte",new byte[]{0x01,0x09,0x03,0x01,0x01,0x00,0x00,0x01,0x27,0x0f,0x00,0x22});
                intent.putExtra("byte",b);
                startActivity(intent);
            }
        });
    }

    private void checkCash() {
        SharedPreferences sp = getSharedPreferences("grubox_port", MODE_PRIVATE);
        sp.edit().putInt("called",0).apply();
        Intent intent=new Intent(CashCommunicate.this, GrucardActivity.class);
//        intent.putExtra("byte",new byte[]{0x01,0x01,0x02,0x03});
//        intent.putExtra("byte",new byte[]{0x01,0x09,0x03,0x01,0x01,0x00,0x00,0x01,0x27,0x0f,0x00,0x22});
        intent.putExtra("byte",new byte[]{0x01,0x02,0x0c,0x00,0x0e});
        startActivity(intent);
    }

    private void openCash() {
        SharedPreferences sp = getSharedPreferences("grubox_port", MODE_PRIVATE);
        sp.edit().putInt("called",0).apply();
        Intent intent=new Intent(CashCommunicate.this, GrucardActivity.class);
//        intent.putExtra("byte",new byte[]{0x01,0x02,0x07,0x01,0x04});
        intent.putExtra("byte",new byte[]{0x01,0x02,0x1a,0x01,0x19});
        startActivity(intent);

    }
}
