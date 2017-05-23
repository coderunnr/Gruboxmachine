package com.android.grubox.fragments;

import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.grubox.R;
import com.android.grubox.activity.ProductListing;
import com.android.grubox.adapter.ProductMainPageAdapter;
import com.android.grubox.adapter.ProductShowAllAdapter;
import com.android.grubox.databaseutils.VendingDatabase;
import com.android.grubox.models.ProductResponse;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by root on 7/3/17.
 */
public class ShowAllFragment extends Fragment implements GestureOverlayView.OnGesturePerformedListener {

    private RecyclerView mRecyclerView1;
    private RecyclerView.Adapter mAdapter1;
//    private RecyclerView mRecyclerView2;
//    private RecyclerView.Adapter mAdapter2;
//    private RecyclerView mRecyclerView3;
//    private RecyclerView.Adapter mAdapter3;
//    private RecyclerView mRecyclerView4;
//    private RecyclerView.Adapter mAdapter4;
//    private RecyclerView mRecyclerView5;
//    private RecyclerView.Adapter mAdapter5;
//    private RecyclerView mRecyclerView6;
//    private RecyclerView.Adapter mAdapter6;
    private RecyclerView.LayoutManager mLayoutManager1;
//    private RecyclerView.LayoutManager mLayoutManager2;
//    private RecyclerView.LayoutManager mLayoutManager3;
//    private RecyclerView.LayoutManager mLayoutManager4;
//    private RecyclerView.LayoutManager mLayoutManager5;
//    private RecyclerView.LayoutManager mLayoutManager6;
    private GestureLibrary mLibrary;

    List<ProductResponse> row1,row2,row3,row4,row5,row6;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_show_all, container, false);
        List<ProductResponse> productModels = new ArrayList<>();

            VendingDatabase vendingDatabase = new VendingDatabase(getContext());
            try {
                vendingDatabase.open();
                productModels = vendingDatabase.getProductsList();
                vendingDatabase.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }


            //For unique mapping
        Set<ProductResponse> uniqueProductResponse = new HashSet<ProductResponse>(productModels);
        List<ProductResponse> uniqueProductResponseList = new ArrayList<>();
        uniqueProductResponseList.addAll(uniqueProductResponse);

//        row1=new ArrayList<>();
//        row2=new ArrayList<>();
//        row3=new ArrayList<>();
//        row4=new ArrayList<>();
//        row5=new ArrayList<>();
//        row6=new ArrayList<>();

//        for(int i=0;i<productModels.size();i++)
//        {
//            switch (productModels.get(i).getRow())
//            {
//                case 1:
//                    row1.add(productModels.get(i));
//                    break;
//
//                case 2:
//                    row2.add(productModels.get(i));
//                    break;
//
//                case 3:
//                    row3.add(productModels.get(i));
//                    break;
//
//                case 4:
//                    row4.add(productModels.get(i));
//                    break;
//
//                case 5:
//                    row5.add(productModels.get(i));
//                    break;
//
//                case 6:
//                    row6.add(productModels.get(i));
//                    break;
//
//            }
//        }



        mRecyclerView1 = (RecyclerView) v.findViewById(R.id.row1);
//        mRecyclerView2 = (RecyclerView) v.findViewById(R.id.row2);
//        mRecyclerView3 = (RecyclerView) v.findViewById(R.id.row3);
//        mRecyclerView4 = (RecyclerView) v.findViewById(R.id.row4);
//        mRecyclerView5 = (RecyclerView) v.findViewById(R.id.row5);
//        mRecyclerView6 = (RecyclerView) v.findViewById(R.id.row6);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView1.setHasFixedSize(true);
//        mRecyclerView2.setHasFixedSize(true);
//        mRecyclerView3.setHasFixedSize(true);
//        mRecyclerView4.setHasFixedSize(true);
//        mRecyclerView5.setHasFixedSize(true);
//        mRecyclerView6.setHasFixedSize(true);

        // use a linear layout manager
//        mLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
//        mLayoutManager2= new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        mLayoutManager1 = new GridLayoutManager(getContext(), 2);
//        mLayoutManager2= new GridLayoutManager(getContext(), 2);
//
//        mLayoutManager3 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
//        mLayoutManager4 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
//        mLayoutManager5 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
//        mLayoutManager6 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView1.setLayoutManager(mLayoutManager1);
//        mRecyclerView2.setLayoutManager(mLayoutManager2);
//        mRecyclerView3.setLayoutManager(mLayoutManager3);
//        mRecyclerView4.setLayoutManager(mLayoutManager4);
//        mRecyclerView5.setLayoutManager(mLayoutManager5);
//        mRecyclerView6.setLayoutManager(mLayoutManager6);

        // specify an adapter (see also next example)
        mAdapter1 = new ProductShowAllAdapter((ProductListing) getActivity(),uniqueProductResponseList,getContext());
//        mAdapter2 = new ProductShowAllAdapter((ProductListing) getActivity(),row2,getContext());
//        mAdapter3 = new ProductShowAllAdapter((ProductListing) getActivity(),row3,getContext());
//        mAdapter4 = new ProductShowAllAdapter((ProductListing) getActivity(),row4,getContext());
//        mAdapter5 = new ProductShowAllAdapter((ProductListing) getActivity(),row5,getContext());
//        mAdapter6 = new ProductShowAllAdapter((ProductListing) getActivity(),row6,getContext());
        mRecyclerView1.setAdapter(mAdapter1);
        //mRecyclerView1.setScrollbarFadingEnabled(false);
//        mRecyclerView2.setAdapter(mAdapter2);
//        mRecyclerView3.setAdapter(mAdapter3);
//        mRecyclerView4.setAdapter(mAdapter4);
//        mRecyclerView5.setAdapter(mAdapter5);
//        mRecyclerView6.setAdapter(mAdapter6);

//        mLibrary = GestureLibraries.fromRawResource(getActivity(), R.raw.gestures);
//        if (!mLibrary.load()) {
//            getActivity().finish();
//        }

//        GestureOverlayView gestureOverlayView=(GestureOverlayView)v.findViewById(R.id.gesture_recongizer);
//        gestureOverlayView.addOnGesturePerformedListener(this);



        return v;


    }

    @Override
    public void onGesturePerformed(GestureOverlayView gestureOverlayView, Gesture gesture) {
        ArrayList<Prediction> predictions = mLibrary.recognize(gesture);
        String data="";
        for (int i = 0; i < predictions.size(); i++) {
            data=data+ "=="+predictions.get(i).name;

        }

        // We want at least one prediction
        if (predictions.size() > 0) {
            Prediction prediction = predictions.get(0);
            // We want at least some confidence in the result
            if (prediction.score > 1.0) {
                // Show the spell
                Toast.makeText(getContext(),data+ "  "+ prediction.name, Toast.LENGTH_SHORT).show();
            }
        }
    }
}

