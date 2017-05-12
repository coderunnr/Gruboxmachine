package com.android.grubox.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.grubox.R;
import com.android.grubox.adapter.ProductMainPageAdapter;
import com.android.grubox.adapter.ToggleButtonGroupTableLayout;
import com.android.grubox.databaseutils.VendingDatabase;
import com.android.grubox.fragments.CartFragment;

import java.sql.SQLException;

import static java.security.AccessController.getContext;

public class LoyaltyandPayments extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loyaltyand_payments);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        if (findViewById(R.id.cartfragment_top) != null) {

            if (savedInstanceState != null) {
                return;
            }
            Fragment fragment;
            fragment = new CartFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.cartfragment_top, fragment).commit();
        }

        final RadioButton Paytm = (RadioButton) findViewById(R.id.Paytm);
        final RadioButton Cash = (RadioButton) findViewById(R.id.Cash);
        final RadioButton CreditCard = (RadioButton) findViewById(R.id.CreditCard);
        RadioButton selectedMode;
        next = (Button) findViewById(R.id.nextButton);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Paytm.isChecked()) {
//                    selectedMode = Paytm.getText().toString();
                    Intent intent=new Intent(LoyaltyandPayments.this, PayWithPaytm.class);
                    intent.putExtra("money",gettotal());
                    startActivity(intent);
                } else if (Cash.isChecked()) {
//                    selectedMode= Cash.getText().toString();
                    Intent intent=new Intent(LoyaltyandPayments.this, CashCommunicate.class);
                    intent.putExtra("amount",gettotal());
                    startActivity(intent);

                } else if (CreditCard.isChecked()) {
//                    selectedMode = CreditCard.getText().toString();
//                    Intent intent=new Intent(getContext(), InitiateCreditCardPayment.class);
//                    intent.putExtra("amount",total);
//                    startActivity(intent);

                }
                else {
                    Toast.makeText(getApplicationContext(), "Please select atleast one of the Payment Options!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private int gettotal(){
        int total=0;
        VendingDatabase vendingDatabase=new VendingDatabase(this);
        try {
            vendingDatabase.open();
            total=vendingDatabase.getTotalSum();
            vendingDatabase.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

}
