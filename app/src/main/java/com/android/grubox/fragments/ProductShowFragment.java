package com.android.grubox.fragments;

import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.grubox.R;
import com.android.grubox.activity.ProductListing;
import com.android.grubox.adapter.ProductMainPageAdapter;
import com.android.grubox.databaseutils.VendingDatabase;
import com.android.grubox.models.ProductResponse;
import com.android.grubox.models.TagModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by root on 21/1/17.
 */
public class ProductShowFragment extends Fragment implements GestureOverlayView.OnGesturePerformedListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private GestureLibrary mLibrary;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_product_show, container, false);
        List<ProductResponse> productModels = new ArrayList<>();
        int cat_id;
        if (getArguments().containsKey("selection")) {
            cat_id = getArguments().getInt("selection");



        VendingDatabase vendingDatabase = new VendingDatabase(getContext());
        try {
            vendingDatabase.open();
            productModels = vendingDatabase.getProductsList();
            vendingDatabase.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        else
        {
            TagModel tagModel=(TagModel) getArguments().getSerializable("tag");

            if(tagModel.getType()==1) {
                VendingDatabase vendingDatabase = new VendingDatabase(getContext());
                try {
                    vendingDatabase.open();
                    productModels = vendingDatabase.getTagBrand(tagModel.getName());
                    vendingDatabase.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else if(tagModel.getType()==2)
            {
                VendingDatabase vendingDatabase=new VendingDatabase(getContext());

                try {
                    vendingDatabase.open();
                    productModels=vendingDatabase.getTagFlavour(tagModel.getName());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                VendingDatabase vendingDatabase = new VendingDatabase(getContext());
                try {
                    vendingDatabase.open();
                    productModels = vendingDatabase.getTagPrice(tagModel.getLowrate(),tagModel.getHighrate());
                    vendingDatabase.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        mRecyclerView = (RecyclerView) v.findViewById(R.id.product_list);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new GridLayoutManager(getContext(),2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new ProductMainPageAdapter((ProductListing) getActivity(),productModels,getContext());
        mRecyclerView.setAdapter(mAdapter);

        mLibrary = GestureLibraries.fromRawResource(getActivity(), R.raw.gestures);
        if (!mLibrary.load()) {
            getActivity().finish();
        }

        GestureOverlayView gestureOverlayView=(GestureOverlayView)v.findViewById(R.id.gesture_recongizer);
        gestureOverlayView.addOnGesturePerformedListener(this);



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
//                if(prediction.name.contentEquals("s")||prediction.name.contentEquals("S"))
//                    ((ProductListing)getActivity()).finish();ssssssss
            }
        }
    }
}
