package com.alexvasilkov.foldablelayout.sample.activities;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.alexvasilkov.android.commons.ui.Views;
import com.alexvasilkov.foldablelayout.sample.BuildConfig;
import com.alexvasilkov.foldablelayout.sample.R;
import com.alexvasilkov.foldablelayout.sample.data.BusinessCardOCR;
import com.alexvasilkov.foldablelayout.sample.data.Card;
import com.alexvasilkov.foldablelayout.sample.utils.IoUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CardImportActivity extends BaseActivity{
    private static final int TAKE_PHOTO_RESULT_CODE = 1;
    private Bitmap img_bitmap;
    private ImageView card_photo_img;
    private static String TEMP_DIR_PATH = "";
    private Card new_card;
    //public static String DIRECTORY_DCIM = "DCIM";

    public Uri photoUri;
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
        setContentView(R.layout.take_card_photo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        card_photo_img = (ImageView) Views.find(this, R.id.card_photo_image);

        Log.d("GetCurrentDir", this.getFilesDir().toString());
        TEMP_DIR_PATH = this.getFilesDir().toString();
        //checkPermissions();
        //path = Environment.getExternalStorageDirectory() + File.separator + Environment.DIRECTORY_DCIM + File.separator;

        takePhoto();
    }

    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return "IMG_" + dateFormat.format(date);
    }

    private void takePhoto() {
        String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        Toast.makeText(this, dir, Toast.LENGTH_LONG);

        String fileName = dir + '/' + getPhotoFileName() + ".jpg";
        File newfile = new File(fileName);
        try{
            newfile.createNewFile();

        }
        catch (IOException e)
        {
            Log.d("cardimport", "new file created failed!");
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", newfile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        startActivityForResult(intent, TAKE_PHOTO_RESULT_CODE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            switch ((requestCode)){
                case TAKE_PHOTO_RESULT_CODE:
                    Uri uri = null;
                    if (data != null && data.getData() != null) {
                        uri = data.getData();
                        //Toast.makeText(this, "get photo data", Toast.LENGTH_LONG);
                    }
                    else{
                        if (photoUri != null) {
                            uri = photoUri;
                            //Toast.makeText(this, "get photo data", Toast.LENGTH_LONG);
                        }
                    }

                    if (uri != null) {
                        String image_path = getFilePathFromURI(this, uri);
                        showImage(image_path);

                        new BusinessCardOCR().ScanBusinessCard(image_path, (card)->{
                            new_card = (Card) card;
                            new_card.setImage(1);

                            Intent intent = new Intent();
                            intent.setComponent(new ComponentName(this, CardEditActivity.class));
                            intent.putExtra("card", new_card.toString());
                            startActivity(intent);
                        });

                    }

                    //Todo: 识别名片
                    break;
                default:
                    break;
            }

        }
    }

    public static String getFilePathFromURI(Context context, Uri contentUri) {
        //copy file and send new file path
        String fileName = getFileName(contentUri);
        if (!TextUtils.isEmpty(fileName)) {
            File copyFile = new File(TEMP_DIR_PATH + File.separator + fileName);
            copy(context, contentUri, copyFile);
            return copyFile.getAbsolutePath();
        }
        return null;
    }

    public static String getFileName(Uri uri) {
        if (uri == null) return null;
        String fileName = null;
        String path = uri.getPath();
        int cut = path.lastIndexOf('/');
        if (cut != -1) {
            fileName = path.substring(cut + 1);
        }
        return fileName;
    }

    public static void copy(Context context, Uri srcUri, File dstFile) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(srcUri);
            if (inputStream == null) return;
            OutputStream outputStream = new FileOutputStream(dstFile);
            try {
                IoUtils.copy(inputStream, outputStream);
            }catch (Exception e){

            }

            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void showImage(String image_path){
        img_bitmap = BitmapFactory.decodeFile(image_path);
        if (img_bitmap == null){
            Toast.makeText(this, image_path, Toast.LENGTH_LONG).show();
        }
        card_photo_img.setImageBitmap(img_bitmap);
    }

//    String[] permissions= new String[]{
//            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            Manifest.permission.CAMERA,
//            Manifest.permission.ACCESS_COARSE_LOCATION,
//            Manifest.permission.ACCESS_FINE_LOCATION};


//    private static final int MULTIPLE_PERMISSIONS = 10;

//    private  boolean checkPermissions() {
//        int result;
//        List<String> listPermissionsNeeded = new ArrayList<>();
//        for (String p:permissions) {
//            result = ContextCompat.checkSelfPermission(this,p);
//            if (result != PackageManager.PERMISSION_GRANTED) {
//                listPermissionsNeeded.add(p);
//            }
//        }
//        if (!listPermissionsNeeded.isEmpty()) {
//            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),MULTIPLE_PERMISSIONS );
//            return false;
//        }
//        return true;
//    }
//
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case MULTIPLE_PERMISSIONS:{
//                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                    // permissions granted.
//                } else {
////                    Toast.makeText(this, "Go to settings and enable permissions", Toast.LENGTH_LONG)
////                            .show();
//                }
//                // permissions list of don't granted permission
//            }
//            return;
//        }
//    }


}
