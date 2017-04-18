package com.android.grubox.adapter;

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
import com.android.grubox.fragments.TagFragment;
import com.android.grubox.models.ProductResponse;
import com.android.grubox.models.TagModel;
import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by root on 13/7/16.
 */
public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ViewHolder> {


    ProductListing productListing;
    List<TagModel> tagModels;
    TagFragment tagFragment;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        View v;
        TextView name;

        public ViewHolder(View v, TagAdapter myAdapter) {
            super(v);
            this.v = v;
            name=(TextView)v.findViewById(R.id.tv_recycleitem_tag);
;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TagAdapter(ProductListing productListing, List<TagModel> tagModels, TagFragment tagFragment) {

        this.tagModels=tagModels;
        this.productListing=productListing;
        this.tagFragment=tagFragment;


    }

    // Create new views (invoked by the layout manager)
    @Override
    public TagAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                    int viewType) {
        // create a new view
        View v;

            v= LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycleitem_tag, parent, false);

        ViewHolder vh = new ViewHolder(v, this);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.name.setText("#"+tagModels.get(position).getName());
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productListing.replacewithTag(tagModels.get(position));
            }
        });

        animateit(holder.v);

    }

    private void animateit(final View v) {
        ViewAnimator
                .animate(v)
                .pulse()
                .start()
                .onStop(new AnimationListener.Stop() {
                    @Override
                    public void onStop() {
                        animateit(v);
                    }
                });
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return tagModels.size();

    }
}


