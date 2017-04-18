package com.android.grubox.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.grubox.R;
import com.android.grubox.activity.ConsoleActivity;
import com.android.grubox.activity.GrucardActivity;
import com.android.grubox.activity.PayWithCash;
import com.android.grubox.activity.PayWithPaytm;
import com.android.grubox.activity.ProductListing;
import com.android.grubox.activity.TestMachineMain;
import com.android.grubox.adapter.CartAdapter;
import com.android.grubox.databaseutils.VendingDatabase;
import com.android.grubox.models.ProductResponse;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 24/1/17.
 */
public class CartFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    int total=0;
    TextView total_cart;
    List<ProductResponse> productModels;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_cart, container, false);

        productModels=new ArrayList<>();

        mRecyclerView = (RecyclerView) v.findViewById(R.id.cart_list);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        total_cart=(TextView)v.findViewById(R.id.total_amount_cart);
        // specify an adapter (see also next example)
        refreshCart();
       // mAdapter = new CartAdapter((ProductListing)getActivity(),productModels,getContext());
       // mRecyclerView.setAdapter(mAdapter);
        v.findViewById(R.id.cart_pay_with_cash).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getActivity().getSharedPreferences("grubox_port", getActivity().MODE_PRIVATE);
                sp.edit().putInt("called",0).apply();
                Intent intent=new Intent(getContext(), PayWithCash.class);
                startActivity(intent);
//                Intent intent=new Intent(getContext(), TestMachineMain.class);
//                startActivity(intent);

            }
        });
        v.findViewById(R.id.cart_pay_with_paytm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                SharedPreferences sp = getActivity().getSharedPreferences("grubox_port", getActivity().MODE_PRIVATE);
//                sp.edit().putInt("called",0).apply();
//                Intent intent=new Intent(getContext(), PayWithCash.class);
//                startActivity(intent);
                Intent intent=new Intent(getContext(), PayWithPaytm.class);
                intent.putExtra("money",total);
                startActivity(intent);

            }
        });

        v.findViewById(R.id.cart_pay_with_grucard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getActivity().getSharedPreferences("grubox_port", getActivity().MODE_PRIVATE);
                sp.edit().putInt("called",1).apply();
                Intent intent=new Intent(getContext(), GrucardActivity.class);
                startActivity(intent);


            }
        });
//        Intent intent=new Intent(getContext(), PayWithPaytm.class);
//        startActivity(intent);
        return v;


    }



    public void refreshCart() {
        VendingDatabase vendingDatabase=new VendingDatabase(getActivity());
        try {
            vendingDatabase.open();
            productModels=vendingDatabase.getCartData();
            vendingDatabase.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mAdapter = new CartAdapter((ProductListing)getActivity(),productModels,getContext(),this);
        mRecyclerView.setAdapter(mAdapter);
        updateTotal();
    }

    public void updateTotal()
    {
        VendingDatabase vendingDatabase=new VendingDatabase(getContext());
        try {
            vendingDatabase.open();
            total=vendingDatabase.getTotalSum();
            vendingDatabase.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        total_cart.setText("Total: "+getString(R.string.Rs)+total);

    }
}