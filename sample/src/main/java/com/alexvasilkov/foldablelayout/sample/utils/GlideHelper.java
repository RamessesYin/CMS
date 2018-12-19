package com.alexvasilkov.foldablelayout.sample.utils;

import android.app.Activity;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.widget.ImageView;

import com.alexvasilkov.foldablelayout.sample.R;
import com.alexvasilkov.foldablelayout.sample.items.Painting;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class GlideHelper {

    private static TypedArray images;
    private static Resources res;
    private GlideHelper(Resources res) {

    }

    public  static void init(Resources res){
        GlideHelper.res = res;
    }

    public static void loadPaintingImage(ImageView image, int imageId) {


        if(images ==null)
            images = res.obtainTypedArray(R.array.paintings_images);
        String[] titles = res.getStringArray(R.array.paintings_titles);


        imageId %=titles.length;

        Glide.with(image.getContext().getApplicationContext())
                .load(images.getResourceId(imageId, -1))
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(image);
    }

}
