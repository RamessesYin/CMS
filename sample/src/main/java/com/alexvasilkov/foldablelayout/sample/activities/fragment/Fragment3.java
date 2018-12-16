package com.alexvasilkov.foldablelayout.sample.activities.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alexvasilkov.android.commons.adapters.ItemsAdapter;
import com.alexvasilkov.foldablelayout.sample.R;
import com.alexvasilkov.foldablelayout.sample.activities.BaseActivity;
import com.alexvasilkov.foldablelayout.sample.activities.CardEditActivity;
import com.alexvasilkov.foldablelayout.sample.data.Card;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;


public class Fragment3 extends Fragment {
    private static final int SELECT_IMAGE_RESULT_CODE = 2;
    private ImageView card_img;
    private Button btn_card_img;
    private ListView card_info;
    private Bitmap img_bitmap;
    private AlertDialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View my_view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment3, container, false);
        card_img = (ImageView)my_view.findViewById(R.id.my_card_img);
        btn_card_img = (Button)my_view.findViewById(R.id.btn_import_image);

        btn_card_img.setOnClickListener(new f3_clickListener());

        //card_info = (ListView)my_view.findViewById(R.id.my_card_info);

//        String[] card_info_str = {"姓名：李四", "电话：123456789", "邮箱：123@qq.com"};
//        ArrayAdapter<String> card_adap = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_expandable_list_item_1, card_info_str);
//        card_info.setAdapter(card_adap);
//        card_info.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view,
//                                           int position, long id) {
//                setAlertDialog(view);
//                dialog.show();
//                return false;
//            }
//        });
        return my_view;
    }

    protected class f3_clickListener implements View.OnClickListener{
        @Override
        public void onClick(View v){
            switch (v.getId()){
                case R.id.btn_import_image:
                    pickPhoto();
                    break;
                default:
                    break;
            }
        }
    }

//    public class CardAdapter extends BaseAdapter{
//        private Card card_data;
//        private Context m_context;
//
//        public CardAdapter(Card card_data,Context m_context){
//            this.card_data = card_data;
//            this.m_context = m_context;
//        }
//
//        @Override
//        public int getCount(){
//            return card_data.length;
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent){
//            ItemsAdapter.ViewHolder holder = null;
//            if (convertView == null) {
//                convertView = LayoutInflater.from(m_context).inflate(R.layout.item_list, parent, false);
//                holder = new ItemsAdapter.ViewHolder();
//                holder.img_icon = (ImageView) convertView.findViewById(R.id.img_icon);
//                holder.txt_content = (TextView) convertView.findViewById(R.id.txt_content);
//                convertView.setTag(holder);
//            } else {
//                holder = (ViewHolder) convertView.getTag();
//            }
//            holder.img_icon.setImageResource(mData.get(position).getImgId());
//            holder.txt_content.setText(mData.get(position).getContent());
//            return convertView;
//
//        }
//    }

    private void pickPhoto(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, SELECT_IMAGE_RESULT_CODE);
    }

    private void takePhoto() {
        // 执行拍照前，应该先判断SD卡是否存在
        String SDState = Environment.getExternalStorageState();
        if (SDState.equals(Environment.MEDIA_MOUNTED)) {

            //通过指定图片存储路径，解决部分机型onActivityResult回调 data返回为null的情况

            //获取与应用相关联的路径
            String imageFilePath = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
            //根据当前时间生成图片的名称
            String timestamp = "/"+formatter.format(new Date())+".png";
            File imageFile = new File(imageFilePath,timestamp);// 通过路径创建保存文件
            String mImagePath = imageFile.getAbsolutePath();
            Uri imageFileUri = Uri.fromFile(imageFile);// 获取文件的Uri
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,imageFileUri);// 告诉相机拍摄完毕输出图片到指定的Uri
            startActivityForResult(intent, SELECT_IMAGE_RESULT_CODE);
        } else {
            Toast.makeText(this.getActivity(), "内存卡不存在!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            switch(requestCode){
                case SELECT_IMAGE_RESULT_CODE:
                    if (data != null){
                        //Toast.makeText(this.getActivity(), "Succeed!", Toast.LENGTH_LONG).show();
                        Uri img = data.getData();
                        String[] filePathColumns = {MediaStore.Images.Media.DATA};
                        Cursor c = getActivity().getContentResolver().query(img, filePathColumns, null, null, null);
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
            Toast.makeText(this.getActivity(), image_path, Toast.LENGTH_LONG).show();
        }
        card_img.setImageBitmap(img_bitmap);
    }

    private void setAlertDialog(final View view) {
        LayoutInflater factory = LayoutInflater.from(getActivity().getApplicationContext());
        View contview = factory.inflate(R.layout.test_dialog, null);
        contview.setBackgroundColor(Color.WHITE);// 设置该外部布局的背景
        final EditText edit = (EditText) contview
                .findViewById(R.id.edit_dialog);// 找到该外部布局对应的EditText控件
        Button btOK = (Button) contview.findViewById(R.id.btOK_dialog);
        btOK.setOnClickListener(new View.OnClickListener() {// 设置按钮的点击事件

            @Override
            public void onClick(View v) {
                //TextView text = my_view.
                ((TextView) view).setText(edit.getText().toString());
                dialog.dismiss();
            }
        });
        dialog = new AlertDialog.Builder(getActivity()).setView(contview)
                .create();
    }


}
