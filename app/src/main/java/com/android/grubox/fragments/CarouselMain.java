package com.android.grubox.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.grubox.R;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

/**
 * Created by root on 21/1/17.
 */
public class CarouselMain extends Fragment {

    CarouselView carouselView;
    int[] ads=new int[]{R.drawable.pandaforcarousel, R.drawable.pandaforcarousel1};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_carousel_main, container, false);

        carouselView = (CarouselView) v.findViewById(R.id.carouselView_main);
        carouselView.setPageCount(ads.length);

        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(ads[position]);
            }
        });

        return v;
    }
}
