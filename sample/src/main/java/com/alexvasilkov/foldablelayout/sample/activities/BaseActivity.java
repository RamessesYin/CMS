package com.alexvasilkov.foldablelayout.sample.activities;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.alexvasilkov.foldablelayout.sample.R;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.plus_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.plus:
                Toast.makeText(BaseActivity.this, "plus selected", Toast.LENGTH_SHORT).show();
                View popupView = this.getLayoutInflater().inflate(R.layout.popupwindow, null);
                final PopupWindow popWindow = new PopupWindow(popupView,
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                popWindow.setTouchable(true);
                popWindow.setTouchInterceptor(new BaseActivity.base_touchListerner());
                popWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
                popWindow.showAsDropDown(popupView, 50, 50);


                Button add_card_btn = (Button) popupView.findViewById(R.id.btn_edit_card);
                add_card_btn.setOnClickListener(new BaseActivity.base_clickListener());

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected class base_touchListerner implements View.OnTouchListener{
        @Override
        public boolean onTouch(View v, MotionEvent event){
            return false;
        }
    }

    protected class base_clickListener implements View.OnClickListener{
        @Override
        public void onClick(View v){
            switch (v.getId()){
                case R.id.btn_edit_card:
                    Intent intent = new Intent();
                    intent.setComponent(new ComponentName(BaseActivity.this, CardEditActivity.class));
                    startActivity(intent);
                    break;
                case R.id.btn_scan:
                    break;
                default:
                    break;
            }
        }
    }

    @NonNull
    @Override
    public ActionBar getSupportActionBar() {
        // Making getSupportActionBar() method to be @NonNull
        ActionBar actionBar = super.getSupportActionBar();
        if (actionBar == null) {
            throw new NullPointerException("Action bar was not initialized");
        }
        return actionBar;
    }

}
