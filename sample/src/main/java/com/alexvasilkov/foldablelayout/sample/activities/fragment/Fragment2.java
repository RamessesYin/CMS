package com.alexvasilkov.foldablelayout.sample.activities.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alexvasilkov.android.commons.texts.SpannableBuilder;
import com.alexvasilkov.android.commons.ui.Views;
import com.alexvasilkov.foldablelayout.UnfoldableView;
import com.alexvasilkov.foldablelayout.sample.R;
import com.alexvasilkov.foldablelayout.sample.activities.BaseFragment;
import com.alexvasilkov.foldablelayout.sample.activities.ScanQRCodeActivity;
import com.alexvasilkov.foldablelayout.sample.data.Card;
import com.alexvasilkov.foldablelayout.sample.data.HttpClient;
import com.alexvasilkov.foldablelayout.sample.data.User;
import com.alexvasilkov.foldablelayout.sample.data.UserRecommand;
import com.alexvasilkov.foldablelayout.sample.items.CardsAdapter;
import com.alexvasilkov.foldablelayout.sample.utils.GlideHelper;
import com.alexvasilkov.foldablelayout.shading.GlanceFoldShading;
import com.blikoon.qrcodescanner.QrCodeActivity;


import java.util.ArrayList;
import java.util.List;

public class Fragment2 extends BaseFragment implements CardsAdapter.Unfordable, View.OnClickListener {
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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View contacts_view = LayoutInflater.from(getActivity()).inflate(R.layout.recommond_user, container, false);

        listView = (ListView) contacts_view.findViewById(R.id.list_view);
        card_adapter = new CardsAdapter(this.getContext(), this);
        card_adapter.setItemsList(cards);
        listView.setAdapter(card_adapter);

        List<Long> ids = UserRecommand.recommandUser(HttpClient.user,5);

        for (long id : ids){
            handler.postDelayed(()->{
                Log.d("UserRecommand", ""+id);
                HttpClient.getUser(id,(data)->{
                    if(data!=null)
                        handler.post(() -> {
                            User user = (User) data;
                            Log.d("UserRecommand", ""+user.self_card.toString());
                            cards.add(user.self_card);
                            card_adapter.notifyDataSetChanged();
                        });
                    else
                        Toast.makeText(this.getActivity(),"链接超时",Toast.LENGTH_LONG).show();
                });
            },200);
        }


        listTouchInterceptor = contacts_view.findViewById(R.id.touch_interceptor_view);
        listTouchInterceptor.setClickable(false);

        detailsLayout = contacts_view.findViewById(R.id.details_layout);
        detailsLayout.setVisibility(View.INVISIBLE);

        unfoldableView = (UnfoldableView) contacts_view.findViewById(R.id.unfoldable_view);

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

        return contacts_view;
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
//

    public void openDetails(View coverView, Card card) {
        final ImageView image = Views.find(detailsLayout, R.id.details_image);
        final TextView title = Views.find(detailsLayout, R.id.details_title);
        final TextView description = Views.find(detailsLayout, R.id.details_text);

        GlideHelper.loadPaintingImage(image, card.getImage());
        title.setText(card.getName());

        SpannableBuilder builder = new SpannableBuilder(this.getContext());
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

    @Override
    public void onClick(View view) {
        Card new_card = (Card) view.getTag(R.id.list_item_image);
        Log.d("add recommoned card", "add recommond card");
        if(new_card != null) {
            Log.d("add recommoned card", "add recommond card");
            cards.remove(new_card);
            card_adapter.notifyDataSetChanged();
            List<Card> self_card_list = HttpClient.user.getCards();
            for (Card card_item : self_card_list){
                if (card_item.getId() == new_card.getId()){
                    Toast.makeText(this.getActivity(), "你已经添加过该名片", Toast.LENGTH_LONG).show();
                    return;
                }
            }

            Log.d("card_check",new_card.toString());
            HttpClient.user.setCards(self_card_list);
            Log.d("card_check",HttpClient.user.toString());
            HttpClient.updateUser(HttpClient.user, (data_1)->{
                if(data_1 == null) {
                    Log.d("HttpClient","update user  failed after adding new card!");
                    return;
                }
                else{
                    self_card_list.add((Card)new_card);
                }
            });
        }
        else{
            Log.d("HttpClient","add new card failed!");
        }
        return;
    }
}
