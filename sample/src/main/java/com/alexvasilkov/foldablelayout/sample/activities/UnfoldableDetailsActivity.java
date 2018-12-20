package com.alexvasilkov.foldablelayout.sample.activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
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
import com.alexvasilkov.foldablelayout.sample.data.Card;
import com.alexvasilkov.foldablelayout.sample.data.HttpClient;
import com.alexvasilkov.foldablelayout.sample.data.User;
import com.alexvasilkov.foldablelayout.sample.items.Painting;
import com.alexvasilkov.foldablelayout.sample.items.PaintingsAdapter;
import com.alexvasilkov.foldablelayout.sample.utils.GlideHelper;
import com.alexvasilkov.foldablelayout.shading.GlanceFoldShading;
import com.alexvasilkov.foldablelayout.sample.activities.fragment.Fragment1;
import com.alexvasilkov.foldablelayout.sample.activities.fragment.Fragment2;
import com.alexvasilkov.foldablelayout.sample.activities.fragment.Fragment3;
import com.alexvasilkov.foldablelayout.sample.activities.BottomBar;
import com.blikoon.qrcodescanner.QrCodeActivity;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;


public class UnfoldableDetailsActivity extends BaseActivity {

    public Handler handler = new Handler();
    private static final int REQUEST_CODE_QR_SCAN = 101;
    private static final int PERMISSIONS_REQUEST_OPEN_ALBUM = 1;
    private static final int PERMISSIONS_REQUEST_CAMERA = 2;
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
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getAuthority();

//        CountDownLatch is_check = new CountDownLatch(1);
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
//            is_check.countDown();
//        });
//
//        try {
//            is_check.await();
            bottomBar = (BottomBar) findViewById(R.id.bottom_bar);
            bottomBar.setContainer(R.id.fl_container)
                    .setTitleBeforeAndAfterColor("#999999", "#3a5775")
                    .addItem(Fragment1.class,
                            "联系人",
                            R.drawable.contacts_icon2,
                            R.drawable.contacts_icon4)
                    .addItem(Fragment2.class,
                            "扫一扫",
                            R.drawable.scan_icon2,
                            R.drawable.scan_icon3)
                    .addItem(Fragment3.class,
                            "我的",
                            R.drawable.my_icon2,
                            R.drawable.my_icon3)
                    .build();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    public void getAuthority(){
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            //权限还没有授予，需要在这里写申请权限的代码
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_OPEN_ALBUM);
        }

        if (ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            //权限还没有授予，需要在这里写申请权限的代码
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, PERMISSIONS_REQUEST_CAMERA);
        }
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Log.d("scan_card", "tttttt");
//        if (data != null) {
//            Toast.makeText(this, data.getData().toString(), Toast.LENGTH_LONG).show();
//        }
//        if (resultCode == Activity.RESULT_OK){
//            if (data != null){
//                String card_id = data.getStringExtra("com.blikoon.qrcodescanner.got_qr_scan_relult");
//                Log.d("scan_card", card_id);
//                Toast.makeText(this, card_id, Toast.LENGTH_LONG).show();
//
////                TextView debug_text = (TextView) Views.find(this, R.id.debug_text);
////                debug_text.setText(card_id);
////                Toast.makeText(this, card_id, Toast.LENGTH_LONG).show();
////
////                AlertDialog.Builder builder = new AlertDialog.Builder(this);
////                builder.setTitle("");
////                builder.setMessage("确认要添加CardId为" + card_id + "的名片？");
////                builder.setNegativeButton("Cancel", (dialog, which) -> {
////                    dialog.dismiss();
////                });
////                builder.setPositiveButton("OK", (dialog, which) -> {
////                    Log.d("scan_card", card_id);
////                    HttpClient.getCard(Long.valueOf(card_id).longValue(),(card_data)-> {
////                        Card card = (Card) card_data;
////                        if (card == null) {
////                            Log.d("HttpClient", "get card failed.");
////                            return;
////                        }
////                        else{
////                            Log.d("scan_card", card.toString());
////                            HttpClient.user.cards.add(card);
////                            HttpClient.updateUser(HttpClient.user, (user_data) -> {
////                                if (user_data == null) {
////                                    Log.d("HttpClient", "update user failed!");
////                                }
////                                else{
////                                    Log.d("scan_card", user_data.toString());
////                                }
////                                return;
////                            });
////                        }
////                    });
////                    dialog.dismiss();
////                });
////                builder.create().show();
//                //setAlertDialog(scan_view, card_id.toString());
//                //dialog.show();
//            }
//        }
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_OPEN_ALBUM || requestCode == PERMISSIONS_REQUEST_CAMERA) {
            if (grantResults != null) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //授权成功
                    Toast.makeText(this, "Permission Passed", Toast.LENGTH_SHORT).show();

                } else {
                    //授权失败
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onResume(){
        super.onResume();
    }

}
