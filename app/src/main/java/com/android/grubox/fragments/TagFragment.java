package com.android.grubox.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.grubox.R;
import com.android.grubox.activity.ProductListing;
import com.android.grubox.adapter.ProductMainPageAdapter;
import com.android.grubox.adapter.TagAdapter;
import com.android.grubox.models.TagModel;

import java.util.ArrayList;
import java.util.List;


public class TagFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    List<TagModel> tagModels;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_tag, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.tag_recycleview);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
//        mLayoutManager = new GridLayoutManager(getContext(),5);
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        tagModels=new ArrayList<>();

        setUpTags();
        // specify an adapter (see also next example)
        mAdapter = new TagAdapter((ProductListing) getActivity(),tagModels,this);
        mRecyclerView.setAdapter(mAdapter);

        return v;
    }

    private void setUpTags() {
        TagModel tagModel=new TagModel();
        tagModel.setType(1);
        tagModel.setName("lays");
        tagModels.add(tagModel);
        TagModel tagModel6=new TagModel();
        tagModel6.setType(1);
        tagModel6.setName("pepsi");
        tagModels.add(tagModel6);
        TagModel tagModel7=new TagModel();
        tagModel7.setType(1);
        tagModel7.setName("maggi");
        tagModels.add(tagModel7);
        TagModel tagModel8=new TagModel();
        tagModel8.setType(1);
        tagModel8.setName("nestle");
        tagModels.add(tagModel8);
        TagModel tagModel9=new TagModel();
        tagModel9.setType(1);
        tagModel9.setName("britannia");
        tagModels.add(tagModel9);
        TagModel tagModel1=new TagModel();
        tagModel1.setName("10-20");
        tagModel1.setLowrate(10);
        tagModel1.setHighrate(20);
        tagModel1.setType(0);
        tagModels.add(tagModel1);
        TagModel tagModel2=new TagModel();
        tagModel2.setName("21-40");
        tagModel2.setLowrate(21);
        tagModel2.setHighrate(40);
        tagModel2.setType(0);
        tagModels.add(tagModel2);
        TagModel tagModel3=new TagModel();
        tagModel3.setName("41-60");
        tagModel3.setLowrate(41);
        tagModel3.setHighrate(60);
        tagModel3.setType(0);
        tagModels.add(tagModel3);
        TagModel tagModel4=new TagModel();
        tagModel4.setName("61-100");
        tagModel4.setLowrate(61);
        tagModel4.setHighrate(100);
        tagModel4.setType(0);
        tagModels.add(tagModel4);
        TagModel tagModel5=new TagModel();
        tagModel5.setName("100+");
        tagModel5.setLowrate(100);
        tagModel5.setHighrate(2000);
        tagModel5.setType(0);
        tagModels.add(tagModel5);

        TagModel tagModel10=new TagModel();
        tagModel10.setType(2);
        tagModel10.setName("orange");
        tagModels.add(tagModel10);
        TagModel tagModel11=new TagModel();
        tagModel11.setType(2);
        tagModel11.setName("mango");
        tagModels.add(tagModel11);
        TagModel tagModel12=new TagModel();
        tagModel12.setType(2);
        tagModel12.setName("masala");
        tagModels.add(tagModel12);
        TagModel tagModel13=new TagModel();
        tagModel13.setType(2);
        tagModel13.setName("garlic");
        tagModels.add(tagModel13);
        TagModel tagModel14=new TagModel();
        tagModel14.setType(3);
        tagModel14.setName("all");
        tagModels.add(tagModel14);
    }

}
