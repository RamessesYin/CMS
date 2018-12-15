package com.alexvasilkov.foldablelayout.sample.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.alexvasilkov.android.commons.ui.Views;
import com.alexvasilkov.foldablelayout.sample.R;

public class CardEditActivity extends BaseActivity{

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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //ImageView loading_image_view = Views.find(this, R.id.loading_image);
        //View title_view = Views.find(this, R.id.edit_title);

        //loading_image_view.setOnClickListener(new ce_clickListener());

    }

    protected class ce_clickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

        }
    }




}
