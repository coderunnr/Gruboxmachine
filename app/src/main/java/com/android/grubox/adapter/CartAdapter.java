package com.android.grubox.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.grubox.R;
import com.android.grubox.activity.ProductListing;
import com.android.grubox.databaseutils.VendingDatabase;
import com.android.grubox.fragments.CartFragment;
import com.android.grubox.models.ProductResponse;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by root on 13/7/16.
 */
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    Context context;
    ProductListing productListing;
    List<ProductResponse> productModels;
    CartFragment cartFragment;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        View v;
        TextView name,price,quantity;
        ImageView image,remove_cart;

        public ViewHolder(View v, CartAdapter myAdapter) {
            super(v);
            this.v = v;
            image=(ImageView) v.findViewById(R.id.product_image);
            name=(TextView) v.findViewById(R.id.product_name);
            price=(TextView) v.findViewById(R.id.product_price);
            quantity=(TextView) v.findViewById(R.id.tv_total_quantity);
            remove_cart=(ImageView) v.findViewById(R.id.remove_cart_product);
            //plus=(ImageView) v.findViewById(R.id.add__quantity_cart);
            //minus=(ImageView) v.findViewById(R.id.minus_quantity_cart);
;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CartAdapter(ProductListing productListing, List<ProductResponse> productModels, Context context,CartFragment cartFragment) {

        this.context = context;
        this.productListing=productListing;
        this.productModels=productModels;
        this.cartFragment=cartFragment;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v;

            v= LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycleitem_cart_list, parent, false);

        ViewHolder vh = new ViewHolder(v, this);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        holder.name.setText(productModels.get(position).getF_name());
        holder.image.setImageBitmap(productModels.get(position).getImage());
        holder.price.setText(productModels.get(position).getMrp()+"");
        //holder.quantity.setText(productModels.get(position).getQuantity_cart()+"");
        holder.remove_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productListing.deleteProductFromCart(productModels.get(position));
                //Added for debugging
                notifyItemRemoved(position);
            }
        });

        //Removing the functionality to change the quantity in the cart
//        holder.plus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                VendingDatabase vendingDatabase=new VendingDatabase(productListing);
//                try {
//                    vendingDatabase.open();
//                    vendingDatabase.updateEntryQuantity(productModels.get(position).getRow_id(),productModels.get(position).getQuantity_cart()+1);
//                    vendingDatabase.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//                productModels.get(position).setQuantity_cart(productModels.get(position).getQuantity_cart()+1);
//                holder.quantity.setText(productModels.get(position).getQuantity_cart()+"");
//                cartFragment.updateTotal();
//                if(context instanceof ProductListing){
//                    ((ProductListing)context).updateTotal();
//                }
//            }
//        });
//        holder.minus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (productModels.get(position).getQuantity_cart() > 1) {
//                    VendingDatabase vendingDatabase = new VendingDatabase(productListing);
//                    try {
//                        vendingDatabase.open();
//                        vendingDatabase.updateEntryQuantity(productModels.get(position).getRow_id(), productModels.get(position).getQuantity_cart() - 1);
//                        vendingDatabase.close();
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                    }
//                    productModels.get(position).setQuantity_cart(productModels.get(position).getQuantity_cart() - 1);
//                    holder.quantity.setText(productModels.get(position).getQuantity_cart() + "");
//                    cartFragment.updateTotal();
//                    if(context instanceof ProductListing){
//                        ((ProductListing)context).updateTotal();
//                    }
//                }
//            }
//        });
    }



    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return productModels.size();

    }

//    public void updateTotal()
//    {
//        int total=0;
//        VendingDatabase vendingDatabase=new VendingDatabase(this);
//        try {
//            vendingDatabase.open();
//            total=vendingDatabase.getTotalSum();
//            vendingDatabase.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        TextView cart_amount = (TextView) findViewById(R.id.cart_amount);
//        cart_amount.setText("Total: "+getString(R.string.Rs) +total);
//
//    }
}


