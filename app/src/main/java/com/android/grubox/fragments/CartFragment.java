package com.android.grubox.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.grubox.R;
import com.android.grubox.activity.CashCommunicate;
import com.android.grubox.activity.ConsoleActivity;
import com.android.grubox.activity.GrucardActivity;
import com.android.grubox.activity.LoyaltyandPayments;
import com.android.grubox.activity.PayWithCash;
import com.android.grubox.activity.PayWithPaytm;
import com.android.grubox.activity.ProductListing;
import com.android.grubox.activity.TestMachineMain;
import com.android.grubox.adapter.CartAdapter;
import com.android.grubox.adapter.StaticCartAdapter;
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
    public RecyclerView.Adapter mAdapter;
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
        if(getActivity() instanceof LoyaltyandPayments) {
            total_cart = (TextView) getActivity().findViewById(R.id.staticcart_amount);
        }
        // specify an adapter (see also next example)

        //testing starts here
        //Setting the adapter in OnCreate() once.
        //Making a new adapter
        //Adapter is in the fragment named "CartFrag"
        if(getActivity() instanceof ProductListing) {
            CartFragment t= (CartFragment)getFragmentManager().findFragmentByTag("CartFrag");
            this.mAdapter = new CartAdapter((ProductListing) getActivity(), productModels, getContext(), t);
            //mAdapter.notifyDataSetChanged();
        }
        else if(getActivity() instanceof LoyaltyandPayments){
            CartFragment t= (CartFragment)getFragmentManager().findFragmentByTag("CartFrag");
            this.mAdapter = new StaticCartAdapter((LoyaltyandPayments) getActivity(), productModels, getContext(), t);
            //mAdapter.notifyDataSetChanged();
        }
        mRecyclerView.setAdapter(mAdapter);

        //No need to call refresh cart as there is nothing to refresh
        //First call to refresh will be called when cart is updated for the first time
        //refreshCart();

        //Testing ends here
        return v;
    }


    public void refreshCart() {
        //Clearing the last adapter
        if(mAdapter!=null) {
            productModels.clear();
            mAdapter.notifyDataSetChanged();
        }

        //Fetching data from the database
        //Maybe the fragment hasn't been added yet
        VendingDatabase vendingDatabase=new VendingDatabase(getActivity().getApplicationContext());
        try {
            vendingDatabase.open();
            productModels=vendingDatabase.getCartData();
            vendingDatabase.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();

        //Updating the total amount to be paid
        updateTotal();
//        ((ProductListing) getActivity()).updateTotal();
    }

    public void add_item(ProductResponse productModel){

        productModels.add(productModel);
        mAdapter.notifyDataSetChanged();
        updateTotal();

    }

    public void delete_item(ProductResponse productModel){
        productModels.remove(productModel);
        mAdapter.notifyDataSetChanged();
        updateTotal();
    }

    public void clear_cart(){
        productModels.clear();
        mAdapter.notifyDataSetChanged();
        updateTotal();
    }

    public void updateTotal()
    {

        if(getActivity() instanceof LoyaltyandPayments) {
            VendingDatabase vendingDatabase=new VendingDatabase(getContext());
            try {
                vendingDatabase.open();
                total=vendingDatabase.getTotalSum();
                vendingDatabase.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            total_cart.setText("Total: " + getString(R.string.Rs) + total);
        }
        else {
            ((ProductListing) getActivity()).updateTotal();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Log.v("Debug", "Attached");
    }
}