package com.android.grubox.activity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.grubox.Parameters;
import com.android.grubox.R;
import com.android.grubox.databaseutils.VendingDatabase;
import com.android.grubox.fragments.CarouselMain;
import com.android.grubox.fragments.CarouselOffers;
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
import java.util.Locale;
import java.util.Scanner;

import static android.R.attr.typeface;

public class ProductListing extends AppCompatActivity implements View.OnClickListener {

    private CartFragment cartFragment;
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


    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            //update Time, News fragment
            //Sync any items necessary, (maybe update inventory, update advetisements, etc)....

            handler.postDelayed(runnable, 10000000);
        }
    };



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

//        AssetManager am = getApplicationContext().getAssets();
//
//        typeface = Typeface.createFromAsset(am,
//                String.format(Locale.US, "fonts/%s", "Quicksand-Regular.otf"));
//
//        setTypeface(typeface);

        final ViewGroup mContainer = (ViewGroup) findViewById(android.R.id.content).getRootView();
        final Typeface mFont = Typeface.createFromAsset(getAssets(), "Quicksand-Regular.otf");
        Parameters.setAppFont(mContainer, mFont);



        //setUpViews();
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
                    .add(R.id.fragment_container_upper, showAllFragment, "showAllFrag")
                    //.add(R.id.fragment_container_upper, showAllFragment)
                    //.add(R.id.fragment_container_upper, showAllFragment)
                    .commit();
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

                //Testing
                //check if table exists
                if(vendingDatabase.isTableExists("carttable")){
                    Log.v("checkdb", "The table exists");
                }
                else{
                    Log.v("checkdb", "The table wasn't created properly");
                }


                //Testing end


               size= vendingDatabase.getCartData().size();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Fragment fragment;
//            if(size==0)
//             fragment=new CarouselMain();
//            else {
            //Instead of this
//            fragment = new CartFragment();
//            cartFragment=(CartFragment)fragment;
//            }
//            cartFragment=new CartFragment();

                this.cartFragment = new CartFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments


            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container_lower, cartFragment, "CartFrag").commit();
        }
        if (findViewById(R.id.fragment_container_left) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
//            VendingDatabase vendingDatabase=new VendingDatabase(ProductListing.this);
//            int size=0;
//            try {
//                vendingDatabase.open();
//                size= vendingDatabase.getCartData().size();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
            Fragment fragment;
//            if(size==0)
            fragment=new CarouselOffers();
//            else {
//                fragment = new CartFragment();
//                cartFragment=(CartFragment)fragment;
//            }
//            cartFragment=new CartFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments


            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container_left, fragment).commit();
        }


        View proceed = findViewById(R.id.proceed);
        proceed.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView cart_amount = (TextView) findViewById(R.id.cart_amount);
                        Scanner in = new Scanner(cart_amount.getText().toString()).useDelimiter("[^0-9]+");
                        int intamount = in.nextInt();

                        if(intamount  >0 ) {
                            Intent toproceed = new Intent(ProductListing.this, LoyaltyandPayments.class);
                            startActivity(toproceed);
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Please add a product to cart before proceeding!",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        View cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            VendingDatabase vendingDatabase=new VendingDatabase(ProductListing.this);
                            vendingDatabase.open();
                            vendingDatabase.DeleteCart();
                            vendingDatabase.close();
                            cartFragment = (CartFragment)getSupportFragmentManager().findFragmentByTag("CartFrag");
                            cartFragment.clear_cart();

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        //finish();

                    }
                }
        );



        mp = MediaPlayer.create(ProductListing.this, R.raw.queenbreakfree);
      //  mp.start();
//        mLibrary = GestureLibraries.fromRawResource(this, R.raw.gestures);
//        if (!mLibrary.load()) {
//            finish();
//        }
//
//        GestureOverlayView gestureOverlayView=(GestureOverlayView)findViewById(R.id.gesture_recongizer);
//        gestureOverlayView.addOnGesturePerformedListener(this);

        handler.postDelayed(runnable, 10000000);

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
        //Executed only the first time to add the fragment
        if(emptyCart)
        {
            //showCart();
            emptyCart=false;

        }

        VendingDatabase vendingDatabase=new VendingDatabase(ProductListing.this);
        try {
            vendingDatabase.open();
            vendingDatabase.createentryforcart(productModel);
            vendingDatabase.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        if(emptyCart){
//            emptyCart=false;
//        }
//        else{
            //cartFragment.refreshCart();
            //cartFragment = (CartFragment)getSupportFragmentManager().findFragmentByTag("CartFrag");
            //cartFragment.mAdapter.notifyDataSetChanged();
            this.cartFragment.add_item(productModel);
//        }
        updateTotal();
    }


    public void deleteProductFromCart(ProductResponse productModel) {
        VendingDatabase vendingDatabase=new VendingDatabase(ProductListing.this);
        cartFragment = (CartFragment)getSupportFragmentManager().findFragmentByTag("CartFrag");
        try {
            vendingDatabase.open();
            vendingDatabase.deleteEntryforcart(productModel.getRow_id());
            //To remove black space
            //cartFragment.refreshCart();

//            if(vendingDatabase.getCartData().size()==0)
//            {
//                showCarousel();
//            }
            vendingDatabase.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cartFragment.delete_item(productModel);

//        CartFragment temp = (CartFragment) getSupportFragmentManager().findFragmentByTag("CartFrag");
//        getSupportFragmentManager().beginTransaction();


        //Refresh the fragment
//        CartFragment frg = null;
//        frg = (CartFragment) getSupportFragmentManager().findFragmentByTag("CartFrag");
//        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.remove(frg);
//        //CartFragment new_frag = new CartFragment();
        //ft.attach(frg);
//        ft.add(R.id.fragment_container_lower, cartFragment, "CartFrag");
//        ft.commit();
        //To add a new fragment
        //.add(R.id.fragment_container_lower, cartFragment, "CartFrag").commit();

        updateTotal();
    }


    public void replacewithTag(TagModel tagModel) {

        if(tagModel.getType()==3)
        {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            //ShowAllFragment showAllFragment=new ShowAllFragment();
            ShowAllFragment showAllFragment = (ShowAllFragment) getSupportFragmentManager().findFragmentByTag("showAllFrag");
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
        cartFragment=(CartFragment) getSupportFragmentManager().findFragmentByTag("CartFrag");

        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container_lower) != null)
            transaction.replace(R.id.fragment_container_lower, cartFragment);
        else
            transaction.add(R.id.fragment_container_lower, cartFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        wakeLock.release();
        mp.release();

    }

    @Override
    public void onDestroy () {
        handler.removeCallbacks(runnable);
        super.onDestroy();
    }

    public void updateTotal()
    {
        int total=0;
        VendingDatabase vendingDatabase=new VendingDatabase(this);
        try {
            vendingDatabase.open();
            total=vendingDatabase.getTotalSum();
            vendingDatabase.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        TextView cart_amount = (TextView) findViewById(R.id.cart_amount);
        cart_amount.setText("Total: "+getString(R.string.Rs) +total);

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
