package com.android.grubox.activity;

import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.grubox.R;
import com.android.grubox.databaseutils.VendingDatabase;
import com.android.grubox.fragments.CarouselMain;
import com.android.grubox.fragments.CartFragment;
import com.android.grubox.fragments.ProductDetailFragment;
import com.android.grubox.fragments.ProductShowFragment;
import com.android.grubox.fragments.ShowAllFragment;
import com.android.grubox.fragments.TagFragment;
import com.android.grubox.models.ProductResponse;
import com.android.grubox.models.TagModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductListing extends AppCompatActivity implements View.OnClickListener {

    CartFragment cartFragment;
    MediaPlayer mp;
    boolean emptyCart=true;
    PowerManager powerManager;
    PowerManager.WakeLock wakeLock;
    View mDecorView;
//    private GestureLibrary mLibrary;

    @Override
    protected void onResume() {
        super.onResume();
        if(mp!=null)
        {
            mp = MediaPlayer.create(ProductListing.this, R.raw.queenbreakfree);
          //  mp.start();
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_product_listing);
        mDecorView=getWindow().getDecorView();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        hideSystemUI();
//        powerManager = (PowerManager) getSystemService(POWER_SERVICE);
//       wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
//                "MyWakelockTag");
//        wakeLock.acquire();

        setUpViews();
        if (findViewById(R.id.fragment_container_upper) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            ShowAllFragment showAllFragment=new ShowAllFragment();
            Bundle bundle=new Bundle();
            bundle.putInt("selection",0);
            showAllFragment.setArguments(bundle);
            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments


            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container_upper, showAllFragment).commit();
        }
        if (findViewById(R.id.fragment_container_top) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            TagFragment tagFragment=new TagFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments


            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container_top, tagFragment).commit();
        }

        if (findViewById(R.id.fragment_container_lower) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            VendingDatabase vendingDatabase=new VendingDatabase(ProductListing.this);
            int size=0;
            try {
                vendingDatabase.open();
               size= vendingDatabase.getCartData().size();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Fragment fragment;
            if(size==0)
             fragment=new CarouselMain();
            else {
                fragment = new CartFragment();
                cartFragment=(CartFragment)fragment;
            }
//            cartFragment=new CartFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments


            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container_lower, fragment).commit();
        }





        mp = MediaPlayer.create(ProductListing.this, R.raw.queenbreakfree);
      //  mp.start();
//        mLibrary = GestureLibraries.fromRawResource(this, R.raw.gestures);
//        if (!mLibrary.load()) {
//            finish();
//        }
//
//        GestureOverlayView gestureOverlayView=(GestureOverlayView)findViewById(R.id.gesture_recongizer);
//        gestureOverlayView.addOnGesturePerformedListener(this);

    }


    private void setUpViews() {
//        card_snacks=(CardView)findViewById(R.id.card_snacks);
//        card_snacks.setOnClickListener(this);
//        card_beverages=(CardView)findViewById(R.id.card_beverages);
//        card_beverages.setOnClickListener(this);
//        card_cakes=(CardView)findViewById(R.id.card_cakes);
//        card_cakes.setOnClickListener(this);
//        card_betterforyou=(CardView)findViewById(R.id.card_betterforyou);
//        card_betterforyou.setOnClickListener(this);
//
//        layout_snacks=(LinearLayout)findViewById(R.id.layout_snacks);
//        layout_beverages=(LinearLayout)findViewById(R.id.layout_beverages);
//        layout_cakes=(LinearLayout)findViewById(R.id.layout_cakes);
//        layout_betterforyou=(LinearLayout)findViewById(R.id.layout_betterforyou);
    }




    private void hideSystemUI() {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        mDecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }


    private void showSystemUI() {
        mDecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    public void replace_fragment_upper(ProductResponse productModel)
    {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        ProductDetailFragment productDetailFragment=new ProductDetailFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable("product",productModel);
        productDetailFragment.setArguments(bundle);
        transaction.replace(R.id.fragment_container_upper, productDetailFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void replace_fragment_selection(int i)
    {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        ProductShowFragment productShowFragment=new ProductShowFragment();
        Bundle bundle=new Bundle();

        switch (i)
        {
            case 0:
                bundle.putInt("selection",0);
                break;
            case 1:
                bundle.putInt("selection",1);
                break;
            case 2:
                bundle.putInt("selection",2);
                break;
            case 3:
                bundle.putInt("selection",3);
                break;

        }
        productShowFragment.setArguments(bundle);
        transaction.replace(R.id.fragment_container_upper, productShowFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onClick(View view) {

//        card_snacks.setLayoutParams(new TableLayout.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT, 0, 1f));
//        card_beverages.setLayoutParams(new TableLayout.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT, 0, 1f));
//        card_cakes.setLayoutParams(new TableLayout.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT, 0, 1f));
//        card_betterforyou.setLayoutParams(new TableLayout.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT, 0, 1f));

//        layout_snacks.setBackgroundResource(0);
//        layout_beverages.setBackgroundResource(0);
//        layout_cakes.setBackgroundResource(0);
//        layout_betterforyou.setBackgroundResource(0);
//
//
//        switch (view.getId())
//        {
//             case R.id.card_snacks:
////                 card_snacks.setLayoutParams(new TableLayout.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT, 0, 1.2f));
//                 layout_snacks.setBackgroundResource(R.drawable.border_selection);
//                 replace_fragment_selection(0);
//                break;
//            case R.id.card_beverages:
////                card_beverages.setLayoutParams(new TableLayout.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT, 0, 1.2f));
//                layout_beverages.setBackgroundResource(R.drawable.border_selection);
//                replace_fragment_selection(1);
//                break;
//            case R.id.card_cakes:
////                card_cakes.setLayoutParams(new TableLayout.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT, 0, 1.2f));
//                layout_cakes.setBackgroundResource(R.drawable.border_selection);
//                replace_fragment_selection(2);
//                break;
//            case R.id.card_betterforyou:
////                card_betterforyou.setLayoutParams(new TableLayout.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT, 0, 1.2f));
//                layout_betterforyou.setBackgroundResource(R.drawable.border_selection);
//                replace_fragment_selection(3);
//                break;
//
//
//        }
    }


    public void addtoCart(ProductResponse productModel)
    {
        if(emptyCart)
        {
            showCart();

        }

        VendingDatabase vendingDatabase=new VendingDatabase(ProductListing.this);
        try {
            vendingDatabase.open();
            vendingDatabase.createentryforcart(productModel);
            vendingDatabase.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
if(emptyCart)
{
    emptyCart=false;
}
        else {
    cartFragment.refreshCart();
}
    }


    public void deleteProductFromCart(ProductResponse productModel) {
        VendingDatabase vendingDatabase=new VendingDatabase(ProductListing.this);
        try {
            vendingDatabase.open();
            vendingDatabase.deleteEntryforcart(productModel.getRow_id());
            if(vendingDatabase.getCartData().size()==0)
            {
                showCarousel();
            }
            vendingDatabase.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cartFragment.refreshCart();
    }


    public void replacewithTag(TagModel tagModel) {

        if(tagModel.getType()==3)
        {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            ShowAllFragment showAllFragment=new ShowAllFragment();
            transaction.replace(R.id.fragment_container_upper, showAllFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else {

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            ProductShowFragment productShowFragment = new ProductShowFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("tag", tagModel);
            productShowFragment.setArguments(bundle);
            transaction.replace(R.id.fragment_container_upper, productShowFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
    public void showCarousel()
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        CarouselMain carouselMain=new CarouselMain();
        transaction.replace(R.id.fragment_container_lower, carouselMain);
        transaction.addToBackStack(null);
        transaction.commit();
        emptyCart=true;
    }

    public void showCart()
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        cartFragment=new CartFragment();
        transaction.replace(R.id.fragment_container_lower, cartFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        wakeLock.release();
        mp.release();

    }

//    @Override
//    public void onGesturePerformed(GestureOverlayView gestureOverlayView, Gesture gesture) {
//        ArrayList<Prediction> predictions = mLibrary.recognize(gesture);
//        String data="";
//        for (int i = 0; i < predictions.size(); i++) {
//            data=data+ "=="+predictions.get(i).name;
//
//        }
//
//        // We want at least one prediction
//        if (predictions.size() > 0) {
//            Prediction prediction = predictions.get(0);
//            // We want at least some confidence in the result
//            if (prediction.score > 1.0) {
//                // Show the spell
//                Toast.makeText(this,data+ "  "+ prediction.name, Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
}
