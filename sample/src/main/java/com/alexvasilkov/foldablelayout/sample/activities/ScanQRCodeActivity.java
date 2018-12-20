package com.alexvasilkov.foldablelayout.sample.activities;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.blikoon.qrcodescanner.QrCodeActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ScanQRCodeActivity extends BaseActivity{
    private static final int REQUEST_CODE_QR_SCAN = 1;

    public void scanAddress() {
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

        Intent i = new Intent(this, QrCodeActivity.class);
        startActivityForResult(i, REQUEST_CODE_QR_SCAN );
    }

    @Override
    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        return registerReceiver(receiver, filter, null, null);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
            } else {
                String result = intentResult.getContents();
//                DiscoverFragment fragment = new DiscoverFragment();
//                fragment.setResult(result);
                Intent intent = new Intent();
                intent.setAction("com.alexvasilkov.foldablelayout.sample.activities.fragment.Fragment2"); // 设置你这个广播的action
                intent.putExtra("result", result);
                sendBroadcast(intent);
                Log.i("QRCode", "进入onActivityResult" + result);
            }
        }
    }
}
