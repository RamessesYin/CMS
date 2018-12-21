package com.alexvasilkov.foldablelayout.sample.activities.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alexvasilkov.android.commons.texts.SpannableBuilder;
import com.alexvasilkov.android.commons.ui.Views;
import com.alexvasilkov.foldablelayout.UnfoldableView;
import com.alexvasilkov.foldablelayout.sample.R;
import com.alexvasilkov.foldablelayout.sample.activities.BaseFragment;
import com.alexvasilkov.foldablelayout.sample.activities.BottomBar;
import com.alexvasilkov.foldablelayout.sample.activities.TagsActivity;
import com.alexvasilkov.foldablelayout.sample.activities.UnfoldableDetailsActivity;
import com.alexvasilkov.foldablelayout.sample.data.Card;
import com.alexvasilkov.foldablelayout.sample.data.HttpClient;
import com.alexvasilkov.foldablelayout.sample.data.QRCode;
import com.alexvasilkov.foldablelayout.sample.data.User;
import com.alexvasilkov.foldablelayout.sample.items.CardsAdapter;
import com.alexvasilkov.foldablelayout.sample.items.Painting;
import com.alexvasilkov.foldablelayout.sample.items.PaintingsAdapter;
import com.alexvasilkov.foldablelayout.sample.utils.GlideHelper;
import com.alexvasilkov.foldablelayout.shading.GlanceFoldShading;

import org.json.HTTP;

import java.util.ArrayList;

public class Fragment1 extends BaseFragment implements CardsAdapter.Unfordable {
    private View listTouchInterceptor;
    private View detailsLayout;
    private UnfoldableView unfoldableView;
    private CardsAdapter card_adapter;
    private ImageView btn_share;
    private ImageView btn_tag;
    private f1_clickListener btn_click_listener;
    private static boolean is_check = false;
    private Card card;

    private BottomBar bottom_bar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contacts_view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment1, container, false);

        ListView listView = (ListView) contacts_view.findViewById(R.id.list_view);
        card = null;

        //1544704862401l  example
        //1545311083139l  libai
        HttpClient.getUser(1545311906491l, (data) -> {
            User user = (User) data;
            if (user == null) {
                Log.d("HttpClient", "get user failed.");
                HttpClient.user = new User();
                return;
            }
            Log.d("HttpClient", user.toString());
            if (user.getCards() == null)
                user.cards = new ArrayList<Card>();
            HttpClient.user = user;

            Fragment1 fragment1 = this;
            UnfoldableDetailsActivity main = (UnfoldableDetailsActivity) this.getActivity();
            main.handler.post(new Runnable() {
                @Override
                public void run() {
                    card_adapter = new CardsAdapter(main, fragment1);
                    listView.setAdapter(card_adapter);
                }
            });
        });


        listTouchInterceptor = contacts_view.findViewById(R.id.touch_interceptor_view);
        listTouchInterceptor.setClickable(false);

        detailsLayout = contacts_view.findViewById(R.id.details_layout);
        detailsLayout.setVisibility(View.INVISIBLE);

        unfoldableView = (UnfoldableView) contacts_view.findViewById(R.id.unfoldable_view);

        Bitmap glance = BitmapFactory.decodeResource(getResources(), R.drawable.unfold_glance);
        unfoldableView.setFoldShading(new GlanceFoldShading(glance));

        btn_click_listener = new f1_clickListener(this.getContext());
        btn_share = (ImageView) contacts_view.findViewById(R.id.iv_share);
        //btn_share.setOnClickListener(btn_click_listener);
        bottom_bar = (BottomBar) Views.find(this.getActivity(), R.id.bottom_bar);

        btn_tag = (ImageView) contacts_view.findViewById(R.id.iv_edit);


        //QRCode.showQRCode("hello world!",this.getActivity());

//        ImageView share = (ImageView) contacts_view.findViewById(R.id.iv_share);
//        share.setOnClickListener((view)->{
//            QRCode.showQRCode("hello world!",this.getContext());
//
//        });

        unfoldableView.setOnFoldingListener(new UnfoldableView.SimpleFoldingListener() {
            @Override
            public void onUnfolding(UnfoldableView unfoldableView) {
                listTouchInterceptor.setClickable(true);
                bottom_bar.setVisibility(View.INVISIBLE);
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
                bottom_bar.setVisibility(View.VISIBLE);
                detailsLayout.setVisibility(View.INVISIBLE);
            }
        });

        return contacts_view;
        //return LayoutInflater.from(getActivity()).inflate(R.layout.fragment1, container, false);
    }

    protected class f1_clickListener implements View.OnClickListener {
        Context m_context;

        public f1_clickListener(Context m_context) {
            this.m_context = m_context;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_share:
                    if (card != null) {
                        QRCode.showQRCode(String.valueOf(card.id), m_context);
                    }
                    break;
                default:
                    break;
            }

        }
    }

    @Override
    public void onVisible() {
//        HttpClient.getUser(1544704862401l,(data)->{
//            User user = (User) data;
//            if(user==null) {
//                Log.d("HttpClient","get user failed.");
//                HttpClient.user = new User();
//                return;
//            }
//            Log.d("HttpClient",user.toString());
//            if(user.getCards()==null)
//                user.cards = new ArrayList<Card>();
//            HttpClient.user = user;
//            //is_check = true;
//        });
        if (card_adapter != null) {
            card_adapter.resetCards();
            card_adapter.notifyDataSetChanged();
        }
//        while (is_check == true) {
//            card_adapter.resetCards();
//            card_adapter.notifyDataSetChanged();
//        }
//        is_check = false;
    }

    @Override
    public void openDetails(View coverView, Card card) {
        final ImageView image = Views.find(detailsLayout, R.id.details_image);
        final TextView title = Views.find(detailsLayout, R.id.details_title);
        final TextView description = Views.find(detailsLayout, R.id.details_text);
        this.card = card;

        GlideHelper.loadPaintingImage(image, card.getImage());
        title.setText(card.getName());

        SpannableBuilder builder = new SpannableBuilder(this.getActivity());
        builder
                .createStyle().setFont(Typeface.DEFAULT_BOLD).apply()
                .append(R.string.mobile_phone).append(": ")
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
                    .append(card.getTitle());
        description.setText(builder.build());

        btn_tag.setOnClickListener((view) -> {
            Intent i = new Intent(getContext(), TagsActivity.class);
            i.putExtra("user", card.getOwner());
            startActivity(i);
        });

        btn_share.setOnClickListener((view) -> QRCode.showQRCode(String.valueOf(card.id), getContext()));

        unfoldableView.unfold(coverView, detailsLayout);
    }

}
