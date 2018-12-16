package com.alexvasilkov.foldablelayout.sample.activities;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.Toast;

import com.alexvasilkov.foldablelayout.sample.R;
import com.alexvasilkov.foldablelayout.sample.activities.fragment.Fragment3;
import com.alexvasilkov.foldablelayout.sample.data.Card;

/**
 * Created by coder-pig on 2015/7/16 0016.
 */
public class CardEditText extends android.support.v7.widget.AppCompatEditText {

    private Context mContext;
    private Card my_card;
    private Fragment fragment3;

    public CardEditText(Context context, Fragment3 fragment, Card my_card) {
        super(context);
        this.mContext = context;
        this.my_card = my_card;
        init();
    }

    private void init() {
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Toast.makeText(fragment3.getActivity(), "plus selected", Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

}