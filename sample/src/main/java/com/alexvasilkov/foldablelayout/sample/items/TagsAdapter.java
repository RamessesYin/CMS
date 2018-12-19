package com.alexvasilkov.foldablelayout.sample.items;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alexvasilkov.android.commons.adapters.ItemsAdapter;
import com.alexvasilkov.android.commons.ui.ContextHelper;
import com.alexvasilkov.android.commons.ui.Views;
import com.alexvasilkov.foldablelayout.sample.R;
import com.alexvasilkov.foldablelayout.sample.data.HttpClient;
import com.alexvasilkov.foldablelayout.sample.data.Tag;

public class TagsAdapter extends ItemsAdapter<Tag, TagsAdapter.ViewHolder>
        implements View.OnClickListener{

    public TagsAdapter(Context context) {
        setItemsList(HttpClient.user.getTags());
    }

    public void resetTags(){
        setItemsList(HttpClient.user.getTags());
    }

    @Override
    protected TagsAdapter.ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        final TagsAdapter.ViewHolder holder = new TagsAdapter.ViewHolder(parent);
        holder.tag_thumb_up.setOnClickListener(this);
        return holder;
    }

    @Override
    protected void onBindHolder(TagsAdapter.ViewHolder holder, int position) {
        final Tag item = getItem(position);

        holder.tag_thumb_up.setTag(R.id.list_item_thumb_up, item);
        holder.tag_thumb_up.setTag(R.id.list_item_tag_count, holder.tag_count);
        holder.tag_name.setText(item.getText());
        holder.tag_count.setText(String.valueOf(item.getCount()) );
        //holder.tag_count.setText("1");
    }

    @Override
    public void onClick(View view) {
        final Tag tag = (Tag) view.getTag(R.id.list_item_thumb_up);
        final TextView count = (TextView) view.getTag(R.id.list_item_tag_count);
        final Activity activity = ContextHelper.asActivity(view.getContext());

//        tag.setCount(1);
        tag.setCount(tag.getCount() + 1);
        HttpClient.updateTag(tag, (data)->{
            if(data == null) {
                Log.d("HttpClient","update tag failed!");
                return;
            }
        });

        count.setText(String.valueOf(tag.getCount()));
    }

    static class ViewHolder extends ItemsAdapter.ViewHolder {
        final ImageView tag_thumb_up;
        final TextView tag_name;
        final TextView tag_count;

        ViewHolder(ViewGroup parent) {
            super(Views.inflate(parent, R.layout.list_tag_item));
            tag_name = Views.find(itemView, R.id.list_item_tag_name);
            tag_count = Views.find(itemView, R.id.list_item_tag_count);
            tag_thumb_up = Views.find(itemView, R.id.list_item_thumb_up);
        }
    }

}
