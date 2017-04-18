package com.android.grubox.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.grubox.R;
import com.android.grubox.databaseutils.Utility;
import com.android.grubox.databaseutils.VendingDatabase;
import com.android.grubox.models.ProductResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PayWithCash extends AppCompatActivity {

    List<ProductResponse> productResponses;
    ProductResponse productResponse;
    int position;
    int REQUEST_CODE = 100;
    int RESULT_CODE = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_with_cash);

        productResponses = new ArrayList<>();
        try {
            VendingDatabase vendingDatabase = new VendingDatabase(PayWithCash.this);
            vendingDatabase.open();
            productResponses = vendingDatabase.getCartData();
            //    Log.v("REsponse",productResponse.getColumn()+"-"+productResponse.getRow());
            //      byte[] mbuffer= Utility.getBytesOfProduct(vendingDatabase.getRowandCol(productResponse.getId()));
            vendingDatabase.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        makelistacctoquantity();

        callVending();

    }

    private void makelistacctoquantity() {
        List<ProductResponse> productResponseList = new ArrayList<>();

        for (int i = 0; i < productResponses.size(); i++) {
            int quantity = productResponses.get(i).getQuantity_cart();
            for (int j = 0; j < quantity; j++) {
                productResponseList.add(productResponses.get(i));
            }
        }
        productResponses = productResponseList;
    }

    private void callVending() {
        if (position < productResponses.size()) {


            if (position > 0) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(PayWithCash.this, ConsoleActivity.class);
                        intent.putExtra("response", productResponses.get(position));
                        startActivityForResult(intent, REQUEST_CODE);
                        ++position;
                    }
                }, 4500);
            } else {
                Intent intent = new Intent(PayWithCash.this, ConsoleActivity.class);
                intent.putExtra("response", productResponses.get(position));
                startActivityForResult(intent, REQUEST_CODE);
                ++position;
            }

        } else {
            Toast.makeText(PayWithCash.this, "Done", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 99) {
            //  REQUEST_CODE=100;
            callVending();
        } else if (resultCode == 80) {
            //current  position fail
//            REQUEST_CODE=97;
            --position;
            callVending();
        }
    }
}
