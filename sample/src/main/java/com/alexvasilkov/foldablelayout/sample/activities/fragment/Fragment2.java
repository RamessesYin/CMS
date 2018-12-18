package com.alexvasilkov.foldablelayout.sample.activities.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alexvasilkov.foldablelayout.sample.R;
import com.alexvasilkov.foldablelayout.sample.activities.BaseFragment;
import com.alexvasilkov.foldablelayout.sample.data.Card;
import com.blikoon.qrcodescanner.QrCodeActivity;

public class Fragment2 extends BaseFragment {
    private static final int REQUEST_CODE_QR_SCAN = 1;
    private View scan_view;
    private f2_clickListener click_listener;
    private AlertDialog dialog;
    static private boolean visible = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        scan_view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment2, container, false);
        scanAddress();
        return scan_view;
    }

    private void scanAddress() {
        Intent i = new Intent(this.getActivity(), QrCodeActivity.class);
        startActivityForResult(i, REQUEST_CODE_QR_SCAN);
    }

    @Override
    public void onVisible(){
        if (visible == true) {
            scanAddress();
            visible = false;
        }
        else{
            visible = true;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            switch(requestCode){
                case REQUEST_CODE_QR_SCAN:
                    if (data != null){
                        Uri card_id = data.getData();
                        Toast.makeText(this.getActivity(), card_id.toString(), Toast.LENGTH_LONG).show();
                        //setAlertDialog(scan_view, card_id.toString());
                        //dialog.show();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void setAlertDialog(final View view, String card_id) {
        LayoutInflater factory = LayoutInflater.from(getActivity().getApplicationContext());
        View contview = factory.inflate(R.layout.scan_card_dialog, null);
        contview.setBackgroundColor(Color.WHITE);// 设置该外部布局的背景
        final TextView text = (TextView) contview
                .findViewById(R.id.card_scan_text);// 找到该外部布局对应的EditText控件
        text.setText("是否要添加CardId为" + card_id + "的名片？");
        Button btn_OK = (Button) contview.findViewById(R.id.btn_OK_dialog);
        Button btn_cancel = (Button) contview.findViewById(R.id.btn_Cancel_dialog);

        click_listener = new f2_clickListener(card_id);
        btn_OK.setOnClickListener(click_listener);
        btn_cancel.setOnClickListener(click_listener);

        dialog = new AlertDialog.Builder(getActivity()).setView(contview)
                .create();
    }

    protected class f2_clickListener implements  View.OnClickListener{
        String card_id;

        public f2_clickListener(String card_id){
            this.card_id = card_id;
        }

        @Override
        public void onClick(View v){
            switch (v.getId()){
                case R.id.btn_OK_dialog:
                    //Card new_card =

                    dialog.dismiss();
                    break;
                case R.id.btn_Cancel_dialog:
                    dialog.dismiss();
                    break;
                default:
                    break;
            }
        }

    }


}
