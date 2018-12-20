package com.alexvasilkov.foldablelayout.sample.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alexvasilkov.android.commons.texts.SpannableBuilder;
import com.alexvasilkov.android.commons.ui.Views;
import com.alexvasilkov.foldablelayout.UnfoldableView;
import com.alexvasilkov.foldablelayout.sample.R;
import com.alexvasilkov.foldablelayout.sample.data.Card;
import com.alexvasilkov.foldablelayout.sample.data.HttpClient;
import com.alexvasilkov.foldablelayout.sample.data.QRCode;
import com.alexvasilkov.foldablelayout.sample.data.User;
import com.alexvasilkov.foldablelayout.sample.data.UserRecommand;
import com.alexvasilkov.foldablelayout.sample.items.CardsAdapter;
import com.alexvasilkov.foldablelayout.sample.utils.GlideHelper;
import com.alexvasilkov.foldablelayout.shading.GlanceFoldShading;

import java.util.ArrayList;
import java.util.List;

public class RecommondUserActivity extends BaseActivity{
    private CardsAdapter card_adapter;
    private View listTouchInterceptor;
    private View detailsLayout;
    private UnfoldableView unfoldableView;
    private ListView listView;

    private Button btn_submit;
    private Button btn_cancel;
    //private ru_clickListener btn_click_listener;
    private List<Card> cards = new ArrayList<>();
    public Handler handler = new Handler();

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommond_user);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) Views.find(this, R.id.list_view);
        card_adapter = new CardsAdapter(this, null);
        card_adapter.setItemsList(cards);
        listView.setAdapter(card_adapter);

        List<Long> ids = UserRecommand.recommandUser(HttpClient.user,5);
        for (long id : ids){
            Log.d("UserRecommand", ""+id);
            HttpClient.getUser(id,(data)->{
                if(data!=null)
                    handler.post(() -> {
                        User user = (User) data;
                        Log.d("UserRecommand", ""+user.self_card.toString());
                        cards.add(user.self_card);
                        card_adapter.notifyDataSetChanged();
                    });
            });
        }


        listTouchInterceptor = Views.find(this, R.id.touch_interceptor_view);
        listTouchInterceptor.setClickable(false);

        detailsLayout = Views.find(this, R.id.details_layout);
        detailsLayout.setVisibility(View.INVISIBLE);

        unfoldableView = (UnfoldableView) Views.find(this, R.id.unfoldable_view);

        Bitmap glance = BitmapFactory.decodeResource(getResources(), R.drawable.unfold_glance);
        unfoldableView.setFoldShading(new GlanceFoldShading(glance));

        //btn_click_listener = new ru_clickListener(this, card_adapter, listView);

        unfoldableView.setOnFoldingListener(new UnfoldableView.SimpleFoldingListener() {
            @Override
            public void onUnfolding(UnfoldableView unfoldableView) {
                listTouchInterceptor.setClickable(true);
                detailsLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onUnfolded(UnfoldableView unfoldableView) {
                listTouchInterceptor.setClickable(false);
            }

            @Override
            public void onFoldingBack(UnfoldableView unfoldableView) {
                listTouchInterceptor.setClickable(true);
            }

            @Override
            public void onFoldedBack(UnfoldableView unfoldableView) {
                listTouchInterceptor.setClickable(false);
                detailsLayout.setVisibility(View.INVISIBLE);
            }
        });


        }
//    protected class ru_clickListener implements View.OnClickListener{
//        Context m_context;
//        CardsAdapter card_adapter;
//        ListView list_view;
//
//        public ru_clickListener(Context m_context, CardsAdapter card_adpater, ListView list_view){
//            this.m_context = m_context;
//            this.card_adapter = card_adpater;
//            this.list_view = list_view;
//        }
//
//        @Override
//        public void onClick(View v) {
//            switch (v.getId()){
//                case R.id.btn_submit:
//                    //QRCode.showQRCode("hello world!",m_context);
//                    break;
//                case R.id.btn_cancel:
//                    for (int i = 0; i < card_adapter.getCount(); i++){
//                        LinearLayout relativeLayout= (LinearLayout) list_view.getAdapter().getView(i,null,null);
//                        CheckBox cb = (CheckBox)relativeLayout.findViewById(R.id.selected_checkbox);
//                        cb.setChecked(false);
//                    }
//                    //QRCode.showQRCode("hello world!",m_context);
//                    break;
//                default:
//                    break;
//            }
//
//        }
//    }

    public void openDetails(View coverView, Card card) {
        final ImageView image = Views.find(detailsLayout, R.id.details_image);
        final TextView title = Views.find(detailsLayout, R.id.details_title);
        final TextView description = Views.find(detailsLayout, R.id.details_text);

        GlideHelper.loadPaintingImage(image, card.getImage());
        title.setText(card.getName());

        SpannableBuilder builder = new SpannableBuilder(this);
        builder
                .createStyle().setFont(Typeface.DEFAULT_BOLD).apply()
                .append(R.string.phone).append(": ")
                .clearStyle()
                .append(card.getMobile_phone()).append("\n");

        if (card.getEmail() != null)
            builder
                    .createStyle().setFont(Typeface.DEFAULT_BOLD).apply()
                    .append(R.string.email).append(": ")
                    .clearStyle()
                    .append(card.getEmail()).append("\n");

        if (card.getAddress() != null)
            builder
                    .createStyle().setFont(Typeface.DEFAULT_BOLD).apply()
                    .append(R.string.address).append(": ")
                    .clearStyle()
                    .append(card.getAddress()).append("\n");

        if (card.getAddress() != null)
            builder
                    .createStyle().setFont(Typeface.DEFAULT_BOLD).apply()
                    .append(R.string.company).append(": ")
                    .clearStyle()
                    .append(card.getCompany()).append("\n");

        if (card.getAddress() != null)
            builder
                    .createStyle().setFont(Typeface.DEFAULT_BOLD).apply()
                    .append(R.string.title).append(": ")
                    .clearStyle()
                    .append(card.getTitle()).append("\n");
        description.setText(builder.build());

        unfoldableView.unfold(coverView, detailsLayout);
    }
}
