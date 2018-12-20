package com.alexvasilkov.foldablelayout.sample.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.alexvasilkov.android.commons.ui.Views;
import com.alexvasilkov.foldablelayout.sample.R;
import com.alexvasilkov.foldablelayout.sample.data.Card;
import com.alexvasilkov.foldablelayout.sample.data.HttpClient;
import com.alexvasilkov.foldablelayout.sample.utils.GlideHelper;
import com.google.gson.Gson;

import java.util.List;

public class CardEditActivity extends BaseActivity{
    private static final int SELECT_LOCAL_IMAGE_RESULT_CODE = 1;
    private static final int SELECT_IMAGE_RESULT_CODE = 2;

    private Card photo_card;
    private ImageView card_img;
    //private ListView tag_info;
    private Bitmap img_bitmap;
    private AlertDialog dialog;


    private Button btn_card_img;
    private Button btn_save_card;
    private Button btn_cancel;

    private EditText card_edittext_name;
    private EditText card_edittext_mobile_phone;
    private EditText card_edittext_email;
    private EditText card_edittext_address;
    private EditText card_edittext_company;
    private EditText card_edittext_title;

    private ce_clickListener btn_click_listener;

    private int image_id = 0;

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

        card_img = (ImageView)Views.find(this, R.id.my_card_img);
        btn_card_img = (Button)Views.find(this, R.id.btn_import_image);
        btn_save_card = (Button)Views.find(this, R.id.btn_save_card);
        btn_cancel = (Button)Views.find(this, R.id.btn_cancel);

        card_edittext_name = (EditText)Views.find(this, R.id.card_edit_username);
        card_edittext_mobile_phone = (EditText)Views.find(this, R.id.card_edit_mobile_phone);
        card_edittext_email = (EditText)Views.find(this, R.id.card_edit_email);
        card_edittext_address = (EditText)Views.find(this, R.id.card_edit_address);
        card_edittext_company = (EditText)Views.find(this, R.id.card_edit_company);
        card_edittext_title = (EditText)Views.find(this, R.id.card_edit_title);

        Intent intent = getIntent();
        String card_str = intent.getStringExtra("card");
        photo_card = new Gson().fromJson(card_str,Card.class);
        if (photo_card != null){
            InitCard(photo_card);
        }
        else{
            GlideHelper.loadPaintingImage(card_img, 1);
        }

        btn_click_listener = new ce_clickListener();
        btn_card_img.setOnClickListener(btn_click_listener);
        btn_save_card.setOnClickListener(btn_click_listener);
        btn_cancel.setOnClickListener(btn_click_listener);

        card_img.setOnClickListener(view -> {
            pickLocalPhoto();
            //return false;
        });

        //ImageView loading_image_view = Views.find(this, R.id.loading_image);
        //View title_view = Views.find(this, R.id.edit_title);

        //loading_image_view.setOnClickListener(new ce_clickListener());

    }

    public void InitCard(Card photo_card){
        image_id = photo_card.getImage();
        GlideHelper.loadPaintingImage(card_img, image_id);
        card_edittext_name.setText(photo_card.getName());
        card_edittext_mobile_phone.setText(photo_card.getMobile_phone());
        card_edittext_email.setText(photo_card.getEmail());
        card_edittext_address.setText(photo_card.getAddress());
        card_edittext_company.setText(photo_card.getCompany());
        card_edittext_title.setText(photo_card.getTitle());
    }

    protected class ce_clickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_import_image:
                    pickLocalPhoto();
                    break;
                case R.id.btn_save_card:

                    Card new_card = new Card();
                    new_card.setImage(image_id);
                    new_card.setName(card_edittext_name.getText().toString());
                    new_card.setMobile_phone(card_edittext_mobile_phone.getText().toString());
                    new_card.setEmail(card_edittext_email.getText().toString());
                    new_card.setAddress(card_edittext_address.getText().toString());
                    new_card.setCompany(card_edittext_company.getText().toString());
                    new_card.setTitle(card_edittext_title.getText().toString());
                    HttpClient.addCard(new_card, (data)->{
                        if(data != null) {
                            HttpClient.user.cards.add((Card)data);
                            Log.d("card_check",data.toString());
                            Log.d("card_check",HttpClient.user.toString());
                            HttpClient.updateUser(HttpClient.user, (data_1)->{
                                if(data_1 == null) {
                                    Log.d("HttpClient","update user  failed after adding new card!");
                                    return;
                                }
                                else{
                                    finish();
                                }
                            });
                            return;
                        }
                        else{
                            Log.d("HttpClient","add new card failed!");
                            return;
                        }
                    });

                    break;
                case R.id.btn_cancel:
                    card_edittext_name.setText("");
                    card_edittext_mobile_phone.setText("");
                    card_edittext_email.setText("");
                    card_edittext_address.setText("");
                    card_edittext_company.setText("");
                    card_edittext_title.setText("");
                default:
                    break;
            }
        }
    }

    private void pickLocalPhoto(){
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(this, FoldableListActivity.class));
        startActivityForResult(intent, SELECT_LOCAL_IMAGE_RESULT_CODE);
    }

    private void pickPhoto(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, SELECT_IMAGE_RESULT_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            switch(requestCode){
                case SELECT_LOCAL_IMAGE_RESULT_CODE:
                    if (data != null){
                        image_id = data.getExtras().getInt("img_id");
                        //Toast.makeText(this, image_id, Toast.LENGTH_LONG).show();
                        GlideHelper.loadPaintingImage(card_img, image_id);
                    }
                    else{
                        Toast.makeText(this, "data is null", Toast.LENGTH_LONG).show();
                    }
                    break;
                case SELECT_IMAGE_RESULT_CODE:
                    if (data != null){
                        //Toast.makeText(this.getActivity(), "Succeed!", Toast.LENGTH_LONG).show();
                        Uri img = data.getData();
                        String[] filePathColumns = {MediaStore.Images.Media.DATA};
                        Cursor c = this.getContentResolver().query(img, filePathColumns, null, null, null);
                        c.moveToFirst();
                        int columnIndex = c.getColumnIndex(filePathColumns[0]);
                        String image_path = c.getString(columnIndex);
                        showImage(image_path);
                        c.close();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void showImage(String image_path){
        img_bitmap = BitmapFactory.decodeFile(image_path);
        if (img_bitmap == null){
            Toast.makeText(this, image_path, Toast.LENGTH_LONG).show();
        }
        card_img.setImageBitmap(img_bitmap);
    }




}
