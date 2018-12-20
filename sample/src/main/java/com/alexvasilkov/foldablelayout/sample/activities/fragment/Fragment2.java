package com.alexvasilkov.foldablelayout.sample.activities.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alexvasilkov.android.commons.ui.Views;
import com.alexvasilkov.foldablelayout.sample.R;
import com.alexvasilkov.foldablelayout.sample.activities.BaseFragment;
import com.alexvasilkov.foldablelayout.sample.activities.ScanQRCodeActivity;
import com.alexvasilkov.foldablelayout.sample.data.Card;
import com.alexvasilkov.foldablelayout.sample.data.HttpClient;
import com.alexvasilkov.foldablelayout.sample.data.User;
import com.blikoon.qrcodescanner.QrCodeActivity;
import com.google.zxing.client.android.Intents;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class Fragment2 extends BaseFragment {
    private static final int REQUEST_CODE_QR_SCAN = 1;
    private View scan_view;
    public Handler handler = new Handler();
    //private f2_clickListener click_listener;
    private AlertDialog dialog;
    static private boolean visible = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        scan_view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment2, container, false);
        Button btn_test = (Button) scan_view.findViewById(R.id.test_button);
        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanAddress();
            }
        });
//        if (visible == false) {
//            scanAddress();
//            visible = true;
//        }

        //ScanQRCodeActivity qr_activity = new ScanQRCodeActivity();
        //receiveBroadCast = new ReceiveBroadCast(this.getContext());
        //onAttach(qr_activity);
        //qr_activity.scanAddress();
        return scan_view;
    }

//    private ReceiveBroadCast receiveBroadCast;
//
//    @Override
//    public void onAttach(Activity activity) {
//
//        /** 注册广播 */
//        IntentFilter filter = new IntentFilter();
//        filter.addAction("com.alexvasilkov.foldablelayout.sample.activities.fragment.Fragment2");    //只有持有相同的action的接受者才能接收此广播
//        activity.registerReceiver(receiveBroadCast, filter);
//        super.onAttach(activity);
//    }
//
//    class ReceiveBroadCast extends BroadcastReceiver
//    {
//        private Context m_context;
//        ReceiveBroadCast(Context m_context){
//            this.m_context = m_context;
//        }
//
//        @Override
//        public void onReceive(Context context, Intent intent)
//        {
//            String gasname = intent.getExtras().getString("result");
//            Log.i("log","在discoverFragment中获取的扫描值"+ gasname);
//            String card_id = gasname;
//            Log.d("scan_card", card_id);
//
//            TextView debug_text = (TextView) scan_view.findViewById(R.id.debug_text);
//            debug_text.setText(card_id);
//            Toast.makeText(m_context, card_id, Toast.LENGTH_LONG).show();
//
//            AlertDialog.Builder builder = new AlertDialog.Builder(m_context);
//            builder.setTitle("");
//            builder.setMessage("确认要添加CardId为" + card_id + "的名片？");
//            builder.setNegativeButton("Cancel", (dialog, which) -> {
//                dialog.dismiss();
//            });
//            builder.setPositiveButton("OK", (dialog, which) -> {
//                Log.d("scan_card", card_id);
//                HttpClient.getCard(Long.valueOf(card_id).longValue(),(card_data)-> {
//                    Card card = (Card) card_data;
//                    if (card == null) {
//                        Log.d("HttpClient", "get card failed.");
//                        return;
//                    }
//                    else{
//                        Log.d("scan_card", card.toString());
//                        HttpClient.user.cards.add(card);
//                        HttpClient.updateUser(HttpClient.user, (user_data) -> {
//                            if (user_data == null) {
//                                Log.d("HttpClient", "update user failed!");
//                            }
//                            else{
//                                Log.d("scan_card", user_data.toString());
//                            }
//                            return;
//                        });
//                    }
//                });
//                dialog.dismiss();
//            });
//            builder.create().show();
//        }
//    }

    private void scanAddress() {
//        new IntentIntegrator(getActivity())
//                // 扫码的类型,条形码或者二维码
//                .setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES)
//                //这个参数可以设置也可以不设置，设置后使用自定义的二维码扫描框，不设置使用默认的
//                .setPrompt("将二维码放到框内")
//                //是否在扫描完成后有提示音
//                .setBeepEnabled(true)
//                //扫完码之后生成二维码的图片
//                .setBarcodeImageEnabled(true)
//                //初始化扫描
//                .initiateScan();

        Intent i = new Intent(this.getActivity(), QrCodeActivity.class);
        startActivityForResult(i, REQUEST_CODE_QR_SCAN);
    }

    @Override
    public void onVisible(){
//        if (visible == true) {
//            scanAddress();
//            visible = false;
//        }
//        else{
//            visible = true;
//        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(this.getContext(), String.valueOf(resultCode), Toast.LENGTH_LONG).show();
        if (resultCode == Activity.RESULT_OK){
            switch(requestCode){
                case REQUEST_CODE_QR_SCAN:
                    if (data != null){
                        String card_id = data.getStringExtra("com.blikoon.qrcodescanner.got_qr_scan_relult");
                        Log.d("scan_card", card_id);

 //                       TextView debug_text = (TextView) scan_view.findViewById(R.id.debug_text);
//                        debug_text.setText(card_id);
//                        Toast.makeText(this.getActivity(), card_id, Toast.LENGTH_LONG).show();
//
//                        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
//                        builder.setTitle("");
//                        builder.setMessage("确认要添加CardId为" + card_id + "的名片？");
//                        builder.setNegativeButton("Cancel", (dialog, which) -> {
//                            dialog.dismiss();
//                        });
//                        builder.setPositiveButton("OK", (dialog, which) -> {
//                            Log.d("scan_card", card_id);
//                            HttpClient.getCard(Long.valueOf(card_id).longValue(),(card_data)-> {
//                                Card card = (Card) card_data;
//                                if (card == null) {
//                                    Log.d("HttpClient", "get card failed.");
//                                    return;
//                                }
//                                else{
//                                    Log.d("scan_card", card.toString());
//                                    HttpClient.user.cards.add(card);
//                                    HttpClient.updateUser(HttpClient.user, (user_data) -> {
//                                        if (user_data == null) {
//                                            Log.d("HttpClient", "update user failed!");
//                                        }
//                                        else{
//                                            Log.d("scan_card", user_data.toString());
//                                        }
//                                        return;
//                                    });
//                                }
//                            });
//                            dialog.dismiss();
//                        });
//                        builder.create().show();
                        //setAlertDialog(scan_view, card_id.toString());
                        //dialog.show();
                    }
                    break;
                default:
                    break;
            }
        }
    }

//    private void setAlertDialog(final View view, String card_id) {
//        card_id = "1545195612711";
//        LayoutInflater factory = LayoutInflater.from(getActivity().getApplicationContext());
//        View contview = factory.inflate(R.layout.scan_card_dialog, null);
//        contview.setBackgroundColor(Color.WHITE);// 设置该外部布局的背景
//        final TextView text = (TextView) contview
//                .findViewById(R.id.card_scan_text);// 找到该外部布局对应的EditText控件
//        text.setText("是否要添加CardId为" + card_id + "的名片？");
//        Button btn_OK = (Button) contview.findViewById(R.id.btn_OK_dialog);
//        Button btn_cancel = (Button) contview.findViewById(R.id.btn_Cancel_dialog);
//
//        click_listener = new f2_clickListener(card_id);
//        btn_OK.setOnClickListener(click_listener);
//        btn_cancel.setOnClickListener(click_listener);
//
//        dialog = new AlertDialog.Builder(getActivity()).setView(contview)
//                .create();
//    }
//
//    protected class f2_clickListener implements  View.OnClickListener{
//        String card_id;
//
//        public f2_clickListener(String card_id){
//            this.card_id = card_id;
//        }
//
//        @Override
//        public void onClick(View v){
//            switch (v.getId()){
//                case R.id.btn_OK_dialog:
//                    Log.d("scan_card", card_id);
//                    HttpClient.getCard(Long.valueOf(card_id).longValue(),(data)-> {
//                                Card card = (Card) data;
//                                if (card == null) {
//                                    Log.d("HttpClient", "get card failed.");
//                                    return;
//                                }
//                                else{
//                                    HttpClient.user.cards.add(card);
//                                    HttpClient.updateUser(HttpClient.user, (data_1) -> {
//                                        if (data_1 == null) {
//                                            Log.d("HttpClient", "update user failed!");
//                                        }
//                                        else{
//                                            Log.d("scan_card", data_1.toString());
//                                        }
//                                        return;
//                                    });
//                                }
//                            });
//
//                    //Card new_card =
//
//                    dialog.dismiss();
//                    break;
//                case R.id.btn_Cancel_dialog:
//                    dialog.dismiss();
//                    break;
//                default:
//                    break;
//            }
//        }
//
//    }


}
