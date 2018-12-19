package com.alexvasilkov.foldablelayout.sample.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alexvasilkov.android.commons.texts.SpannableBuilder;
import com.alexvasilkov.android.commons.ui.Views;
import com.alexvasilkov.foldablelayout.UnfoldableView;
import com.alexvasilkov.foldablelayout.sample.R;
import com.alexvasilkov.foldablelayout.sample.items.Painting;
import com.alexvasilkov.foldablelayout.sample.items.PaintingsAdapter;
import com.alexvasilkov.foldablelayout.sample.utils.GlideHelper;
import com.alexvasilkov.foldablelayout.shading.GlanceFoldShading;
import com.alexvasilkov.foldablelayout.sample.activities.fragment.Fragment1;
import com.alexvasilkov.foldablelayout.sample.activities.fragment.Fragment2;
import com.alexvasilkov.foldablelayout.sample.activities.fragment.Fragment3;
import com.alexvasilkov.foldablelayout.sample.activities.BottomBar;


public class UnfoldableDetailsActivity extends BaseActivity {

//    private View listTouchInterceptor;
//    private View detailsLayout;
//    private UnfoldableView unfoldableView;
    private BottomBar bottomBar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        GlideHelper.init(getResources());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unfoldable_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bottomBar = (BottomBar) findViewById(R.id.bottom_bar);
        bottomBar.setContainer(R.id.fl_container)
                .setTitleBeforeAndAfterColor("#999999", "#ff5d5e")
                .addItem(Fragment1.class,
                        "首页",
                        R.drawable.item1_before,
                        R.drawable.item1_after)
                .addItem(Fragment2.class,
                        "订单",
                        R.drawable.item2_before,
                        R.drawable.item2_after)
                .addItem(Fragment3.class,
                        "我的",
                        R.drawable.item3_before,
                        R.drawable.item3_after)
                .build();


    }

    @Override
    public void onResume(){
        super.onResume();
    }

}
