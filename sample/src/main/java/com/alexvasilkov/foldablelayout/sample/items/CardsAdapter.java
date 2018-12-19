package com.alexvasilkov.foldablelayout.sample.items;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alexvasilkov.android.commons.adapters.ItemsAdapter;
import com.alexvasilkov.android.commons.ui.ContextHelper;
import com.alexvasilkov.android.commons.ui.Views;
import com.alexvasilkov.foldablelayout.UnfoldableView;
import com.alexvasilkov.foldablelayout.sample.R;
import com.alexvasilkov.foldablelayout.sample.activities.FoldableListActivity;
import com.alexvasilkov.foldablelayout.sample.activities.RecommondUserActivity;
import com.alexvasilkov.foldablelayout.sample.activities.UnfoldableDetailsActivity;
import com.alexvasilkov.foldablelayout.sample.activities.fragment.Fragment1;
import com.alexvasilkov.foldablelayout.sample.data.Card;
import com.alexvasilkov.foldablelayout.sample.data.HttpClient;
import com.alexvasilkov.foldablelayout.sample.utils.GlideHelper;

import java.util.Arrays;

public class CardsAdapter extends ItemsAdapter<Card, CardsAdapter.ViewHolder>
        implements View.OnClickListener {

    private Fragment1 fragment1;
    public Painting [] paintings;


    public CardsAdapter(Context context, Fragment1 fragment) {
        setItemsList(HttpClient.user.getCards());
        fragment1 = fragment;
        paintings = Painting.getAllPaintings(context.getResources());
    }
    public void resetCards(){
        setItemsList(HttpClient.user.getCards());
    }

    @Override
    protected CardsAdapter.ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        final CardsAdapter.ViewHolder holder = new CardsAdapter.ViewHolder(parent);
        holder.image.setOnClickListener(this);
        holder.btn_add.setOnClickListener(this);
        return holder;
    }

    @Override
    protected void onBindHolder(CardsAdapter.ViewHolder holder, int position) {
        final Card item = getItem(position);

        holder.image.setTag(R.id.list_item_image, item);

        int imgId = paintings[item.getImage()% paintings.length].getImageId();
        GlideHelper.loadPaintingImage(holder.image, imgId);
        holder.title.setText(item.getName());
        if (fragment1 != null){
            holder.btn_add.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        final Card item = (Card) view.getTag(R.id.list_item_image);
        final Activity activity = ContextHelper.asActivity(view.getContext());

        switch (view.getId()) {
            case R.id.btn_add_new_card:
                //view.
                break;
            case R.id.list_item_image:
                if (activity instanceof UnfoldableDetailsActivity) {
                    fragment1.openDetails(view, item);
                } else if (activity instanceof FoldableListActivity) {
                    Toast.makeText(activity, item.getName(), Toast.LENGTH_SHORT).show();
                } else if (activity instanceof RecommondUserActivity) {
                    ((RecommondUserActivity) activity).openDetails(view, item);
                }
                break;
            default:
                break;
        }
    }

    static class ViewHolder extends ItemsAdapter.ViewHolder {
        final ImageView image;
        final TextView title;
        final Button btn_add;

        ViewHolder(ViewGroup parent) {
            super(Views.inflate(parent, R.layout.list_item));
            image = Views.find(itemView, R.id.list_item_image);
            title = Views.find(itemView, R.id.list_item_title);
            btn_add = Views.find(itemView, R.id.btn_add_new_card);
        }
    }

}
