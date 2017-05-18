package com.android.grubox.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.grubox.Parameters;
import com.android.grubox.R;
import com.android.grubox.activity.ProductListing;
import com.android.grubox.models.ProductResponse;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;

import java.util.List;
import java.util.Random;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by root on 13/7/16.
 */
public class ProductMainPageAdapter extends RecyclerView.Adapter<ProductMainPageAdapter.ViewHolder> implements
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener{

    Context context;
    ProductListing productListing;
    List<ProductResponse> productModels;
    int position_clciked=0;
    private GestureDetectorCompat mDetector;




//    @Override
//    public boolean onTouchEvent(MotionEvent event){
//        this.mDetector.onTouchEvent(event);
//        // Be sure to call the superclass implementation
//        return super.onTouchEvent(event);
//    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        productListing.addtoCart(productModels.get(position_clciked));
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        //productListing.replace_fragment_upper(productModels.get(position_clciked));

        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        View v,left_shadow,right_shadow;
        View upper_shadow,lower_shadow;
        LinearLayout main_layout;
        TextView name,price;
        TextView fname;
        ImageView image;
        Button addtoCart;
        GifImageView gifImageView;
        View card;


        public ViewHolder(View v, ProductMainPageAdapter myAdapter) {
            super(v);
            this.v = v;
            image=(ImageView) v.findViewById(R.id.product_image);
            name=(TextView) v.findViewById(R.id.product_name);
            price=(TextView) v.findViewById(R.id.product_price);
            addtoCart=(Button) v.findViewById(R.id.add_to_cart);
            fname=(TextView)v.findViewById(R.id.product_name_fname);
            main_layout=(LinearLayout)v.findViewById(R.id.recycleitem_linearlayout_product_list);
;           card=v.findViewById(R.id.card_item);
            gifImageView=(GifImageView)v.findViewById(R.id.gif_panda);
//            upper_shadow=v.findViewById(R.id.upper_shadow);
//            lower_shadow=v.findViewById(R.id.bottom_shadow);
//            right_shadow=v.findViewById(R.id.right_shadow);
//            left_shadow=v.findViewById(R.id.left_shadow);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ProductMainPageAdapter(ProductListing productListing, List<ProductResponse> productModels, Context context) {

        this.context = context;
        this.productListing=productListing;
        this.productModels=productModels;
        mDetector = new GestureDetectorCompat(context,this);
        mDetector.setOnDoubleTapListener(this);



    }

    // Create new views (invoked by the layout manager)
    @Override
    public ProductMainPageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
        // create a new view
        View v;

            v= LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycleitem_product_list, parent, false);

        ViewHolder vh = new ViewHolder(v, this);

        final ViewGroup mContainer = (ViewGroup) v.findViewById(android.R.id.content).getRootView();
        final Typeface mFont = Typeface.createFromAsset(context.getAssets(), "Quicksand-Regular.otf");
        Parameters.setAppFont(mContainer, mFont);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int pos) {

        if(pos%2==0) {
            holder.card.setVisibility(View.VISIBLE);
            holder.main_layout.setVisibility(View.VISIBLE);
            holder.gifImageView.setVisibility(View.GONE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                holder.main_layout.setBackground(productListing.getResources().getDrawable(android.R.color.transparent));
            }
//            holder.upper_shadow.setVisibility(View.VISIBLE);
//            holder.lower_shadow.setVisibility(View.VISIBLE);
//            holder.right_shadow.setVisibility(View.VISIBLE);
//            holder.left_shadow.setVisibility(View.VISIBLE);
            final int position = pos / 2;

//            holder.v.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    productListing.replace_fragment_upper(productModels.get(position));
//                }
//            });

            holder.v.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    mDetector.onTouchEvent(motionEvent);
                    // Be sure to call the superclass implementation
                    position_clciked=position;
                    return true;
                }
            });

            holder.addtoCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    productListing.addtoCart(productModels.get(position));
                }
            });
            holder.name.setText(productModels.get(position).getB_name());
            holder.fname.setText(productModels.get(position).getF_name());
            holder.image.setImageBitmap(productModels.get(position).getImage());
            holder.price.setText(productModels.get(position).getMrp() + "");
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                holder.addtoCart.setBackgroundResource(R.drawable.add);
//            }
//            else
//            {
//                holder.addtoCart.setBackgroundResource(R.drawable.add);
//            }
//            YoYo.with(Techniques.ZoomIn)
//                    .duration(4000)
//                    .playOn(holder.image);
//            YoYo.with(Techniques.ZoomOut)
//                    .delay(4000)
//                    .duration(4000)
//                    .playOn(holder.image);
//          startAnimation(holder.image);


        }
        else
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                holder.v.setBackgroundColor(context.getColor(android.R.color.transparent));
            }
            else
            {
                holder.v.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
            }
           // holder.addtoCart.setVisibility(View.INVISIBLE);
            holder.card.setVisibility(View.GONE);
            Random random=new Random();
            int n=random.nextInt(5);
            if(n==3||n==2) {
                holder.gifImageView.setVisibility(View.VISIBLE);
                if(n==3)
                holder.gifImageView.setImageResource(R.drawable.gifpanda);
            }
            else
                holder.gifImageView.setVisibility(View.GONE);

//            holder.upper_shadow.setVisibility(View.INVISIBLE);
//            holder.lower_shadow.setVisibility(View.INVISIBLE);
//            holder.right_shadow.setVisibility(View.INVISIBLE);
//            holder.left_shadow.setVisibility(View.INVISIBLE);
        }

    }



    public void startAnimation(final ImageView image_product)
    {
//        ViewAnimator
//                .animate(image_product)
//                .scale(1f, 1f, 0.75f)
//                .accelerate()
//                .duration(2000)
//                .thenAnimate(image_product)
//                .wobble()
//                .thenAnimate(image_product)
//                .scale(0.75f,1f,1f)
//                .accelerate()
//                .duration(2000)
//                .start()
//                .onStop(new AnimationListener.Stop() {
//                    @Override
//                    public void onStop() {
//                        startAnimation(image_product);
//                    }
//                });

        int delays[]=new int[]{0,1000,2000,500,1500};
        Random random=new Random();
        ViewAnimator
                .animate(image_product)
                .bounce()
                .thenAnimate(image_product)
                .wobble()
                .start()
                .startDelay(delays[random.nextInt(5)])
                .onStop(new AnimationListener.Stop() {
                    @Override
                    public void onStop() {
                        startAnimation(image_product);
                    }
                });

    }



    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return productModels.size()*2;

    }
}


