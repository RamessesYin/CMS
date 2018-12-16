package com.alexvasilkov.foldablelayout.sample.utils;

import android.widget.ImageView;

import com.alexvasilkov.foldablelayout.sample.items.Painting;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class GlideHelper {

    private GlideHelper() {}

    public static void loadPaintingImage(ImageView image, int imaageId) {
        Glide.with(image.getContext().getApplicationContext())
                .load(imaageId)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(image);
    }

}
