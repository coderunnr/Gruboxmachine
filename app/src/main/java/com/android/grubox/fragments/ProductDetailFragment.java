package com.android.grubox.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.android.grubox.R;
import com.android.grubox.activity.ProductListing;
import com.android.grubox.databaseutils.VendingDatabase;
import com.android.grubox.models.ProductResponse;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.sql.SQLException;

/**
 * Created by root on 21/1/17.
 */
public class ProductDetailFragment extends Fragment implements View.OnClickListener {

    ImageView image;
    TextView name,price,details,quantity;
    ProductResponse productModel;
    VendingDatabase vendingDatabase;
    VideoView videoView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_product_details, container, false);
        productModel=(ProductResponse) getArguments().get("product");
        v.findViewById(R.id.cancel).setOnClickListener(this);
        v.findViewById(R.id.add_to_cart).setOnClickListener(this);
        v.findViewById(R.id.plus_quantity_cart).setOnClickListener(this);
        v.findViewById(R.id.minus_quantity_cart).setOnClickListener(this);
        image=(ImageView) v.findViewById(R.id.product_image);
        name=(TextView) v.findViewById(R.id.product_name);
        price=(TextView) v.findViewById(R.id.product_price);
        quantity=(TextView) v.findViewById(R.id.tv_total_quantity);
        details=(TextView) v.findViewById(R.id.product_details);
        videoView=(VideoView)v.findViewById(R.id.videoview_productdetails);

        videoView.setVideoURI(Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.workout));


        image.setImageResource(productModel.getImage());
        name.setText(productModel.getB_name()+" "+productModel.getF_name());
        price.setText(productModel.getMrp()+"");
        videoView.start();

        YoYo.with(Techniques.BounceIn)
                .duration(700)
                .playOn(image);
        return v;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.add_to_cart:
                ((ProductListing)getActivity()).addtoCart(productModel);
                break;
            case R.id.cancel:
            getActivity().getSupportFragmentManager().popBackStackImmediate();
                break;
            case R.id.plus_quantity_cart:
                vendingDatabase=new VendingDatabase(getContext());
                try {
                    vendingDatabase.open();
                    vendingDatabase.updateEntryQuantity(productModel.getRow_id(),productModel.getQuantity_cart()+1);
                    vendingDatabase.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                productModel.setQuantity_cart(productModel.getQuantity_cart()+1);
                quantity.setText(productModel.getQuantity_cart()+"");

                break;
            case R.id.minus_quantity_cart:
                if(productModel.getQuantity_cart()>1) {
                    vendingDatabase = new VendingDatabase(getContext());
                    try {
                        vendingDatabase.open();
                        vendingDatabase.updateEntryQuantity(productModel.getRow_id(), productModel.getQuantity_cart() - 1);
                        vendingDatabase.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    productModel.setQuantity_cart(productModel.getQuantity_cart() - 1);
                    quantity.setText(productModel.getQuantity_cart() + "");
                }
                break;
        }
    }
}
