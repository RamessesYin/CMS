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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.alexvasilkov.android.commons.adapters.ItemsAdapter;
import com.alexvasilkov.foldablelayout.sample.R;
import com.alexvasilkov.foldablelayout.sample.activities.BaseActivity;
import com.alexvasilkov.foldablelayout.sample.activities.BaseFragment;
import com.alexvasilkov.foldablelayout.sample.activities.CardEditActivity;
import com.alexvasilkov.foldablelayout.sample.activities.FoldableListActivity;
import com.alexvasilkov.foldablelayout.sample.data.Tag;
import com.alexvasilkov.foldablelayout.sample.data.HttpClient;
import com.alexvasilkov.foldablelayout.sample.data.User;
import com.alexvasilkov.foldablelayout.sample.items.TagsAdapter;
import com.alexvasilkov.foldablelayout.sample.utils.GlideHelper;

import org.json.HTTP;
import org.w3c.dom.Text;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;


public class Fragment3 extends BaseFragment {
    private static final int SELECT_LOCAL_IMAGE_RESULT_CODE = 1;
    private static final int SELECT_IMAGE_RESULT_CODE = 2;

    private static final int card_info_count = 6;
    private ImageView card_img;
    private Button btn_card_img;
    private Button btn_save_card;
    private Button btn_cancel;
    private ImageView btn_add_tag;

    private ListView tag_info;
    private Bitmap img_bitmap;
    private AlertDialog dialog;

    private TagsAdapter tag_adapter;

    private EditText card_edittext_name;
    private EditText card_edittext_mobile_phone;
    private EditText card_edittext_email;
    private EditText card_edittext_address;
    private EditText card_edittext_company;
    private EditText card_edittext_title;

    private int image_id = 0;

    private f3_clickListener btn_click_listener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View my_view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment3, container, false);
        card_img = (ImageView)my_view.findViewById(R.id.my_card_img);
        btn_card_img = (Button)my_view.findViewById(R.id.btn_import_image);
        btn_save_card = (Button)my_view.findViewById(R.id.btn_save_card);
        btn_cancel = (Button)my_view.findViewById(R.id.btn_cancel);
        btn_add_tag = (ImageView) my_view.findViewById(R.id.add_tags);

        card_edittext_name = (EditText)my_view.findViewById(R.id.card_edit_username);
        card_edittext_mobile_phone = (EditText)my_view.findViewById(R.id.card_edit_mobile_phone);
        card_edittext_email = (EditText)my_view.findViewById(R.id.card_edit_email);
        card_edittext_address = (EditText)my_view.findViewById(R.id.card_edit_address);
        card_edittext_company = (EditText)my_view.findViewById(R.id.card_edit_company);
        card_edittext_title = (EditText)my_view.findViewById(R.id.card_edit_title);
        InitSelfCard();

        btn_click_listener = new f3_clickListener();
        btn_card_img.setOnClickListener(btn_click_listener);
        btn_save_card.setOnClickListener(btn_click_listener);
        btn_cancel.setOnClickListener(btn_click_listener);
        btn_add_tag.setOnClickListener(btn_click_listener);

        tag_info = (ListView) my_view.findViewById(R.id.list_tag_view);
        tag_adapter = new TagsAdapter(this.getContext(),HttpClient.user.getTags());
        tag_info.setAdapter(tag_adapter);
        //card_tableLayout = (TableLayout) my_view.findViewById(R.id.Card_TableLayout);

//        card_edittext_name.addTextChangedListener(new text_watcher(this.getContext(), card_edittext_name, 1));
//        card_edittext_mobile_phone.addTextChangedListener(new text_watcher(this.getContext(), card_edittext_mobile_phone, 2));
//        card_edittext_email.addTextChangedListener(new text_watcher(this.getContext(), card_edittext_email, 3));
//        card_edittext_address.addTextChangedListener(new text_watcher(this.getContext(), card_edittext_address, 4));
//        card_edittext_company.addTextChangedListener(new text_watcher(this.getContext(), card_edittext_company, 5));
//        card_edittext_title.addTextChangedListener(new text_watcher(this.getContext(), card_edittext_title, 6));

        //for (int i = 0; i < card_info_count; i++){
//            TableRow tr = new TableRow(card_tableLayout.getContext());
//            TextView card_key = new TextView(tr.getContext());
//            card_key.setTextColor(0xfff);
//            card_key.setText("Name:");
//            layout_param = card_key.getLayoutParams();
//            layout_param.height = FrameLayout.LayoutParams.WRAP_CONTENT;
//            layout_param.width = FrameLayout.LayoutParams.MATCH_PARENT;
//            card_key.setLayoutParams(layout_param);
//            card_key.setTextSize(18);
//
//            card_value.setTextColor(0xfff);
//            layout_param = card_value.getLayoutParams();
//            layout_param.height = FrameLayout.LayoutParams.WRAP_CONTENT;
//            layout_param.width = FrameLayout.LayoutParams.MATCH_PARENT;
//            card_value.setLayoutParams(layout_param);
//            card_value.setTextSize(18);
//            card_value.setHorizontallyScrolling(true);
//            card_value.setTheme();


        //}

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

    @Override
    public void onVisible(){

    }

    public void InitSelfCard(){
        image_id = HttpClient.user.self_card.getImage();
        GlideHelper.loadPaintingImage(card_img, image_id);
        card_edittext_name.setText(HttpClient.user.getUser_name());
        card_edittext_mobile_phone.setText(HttpClient.user.getMobile_phone());
        card_edittext_email.setText(HttpClient.user.getEmail());
        card_edittext_address.setText(HttpClient.user.getAddress());
        card_edittext_company.setText(HttpClient.user.getCompany());
        card_edittext_title.setText(HttpClient.user.getTitle());
    }

//    protected class text_watcher implements TextWatcher {
//        private int card_info_id;
//        private EditText m_edit_text;
//        private Context m_context;
//
//        private static final int card_info_name = 1;
//        private static final int card_info_mobile_phone = 2;
//        private static final int card_info_email = 3;
//        private static final int card_info_address = 4;
//        private static final int card_info_company = 5;
//        private static final int card_info_title = 6;
//
//        public text_watcher(Context m_context, EditText m_edit_text, int card_info_id){
//            this.card_info_id = card_info_id;
//            this.m_edit_text = m_edit_text;
//            this.m_context = m_context;
//        }
//
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//        }
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//            switch (card_info_id){
//                case  card_info_name:
//                    HttpClient.user.self_card.setName(s.toString());
//                    Toast.makeText(m_context, s.toString(), Toast.LENGTH_SHORT).show();
//                    break;
//                case card_info_mobile_phone:
//                    HttpClient.user.self_card.setMobile_phone(s.toString());
//                    break;
//                case card_info_email:
//                    HttpClient.user.self_card.setEmail(s.toString());
//                    break;
//                case card_info_address:
//                    HttpClient.user.self_card.setAddress(s.toString());
//                    break;
//                case card_info_company:
//                    HttpClient.user.self_card.setCompany(s.toString());
//                    break;
//                case card_info_title:
//                    HttpClient.user.self_card.setTitle(s.toString());
//                    break;
//                default:
//                    break;
//            }
//            HttpClient.updateCard(HttpClient.user.self_card, (data)->{
//                if(data == null) {
//                    Log.d("HttpClient","update card failed!");
//                    return;
//                }
//            });
//        }
//    }

    protected class f3_clickListener implements View.OnClickListener{
        private EditText edit = null;
        private TagsAdapter tag_adapter = null;

        public f3_clickListener(){}

        public f3_clickListener(EditText edit, TagsAdapter tag_adapter){
            this.edit = edit;
            this.tag_adapter = tag_adapter;
        }

        @Override
        public void onClick(View v){
            switch (v.getId()){
                case R.id.btn_import_image:
                    pickLocalPhoto();
                    break;
                case R.id.btn_save_card:
                    HttpClient.user.self_card.setName(card_edittext_name.getText().toString());
                    HttpClient.user.self_card.setMobile_phone(card_edittext_mobile_phone.getText().toString());
                    HttpClient.user.self_card.setEmail(card_edittext_email.getText().toString());
                    HttpClient.user.self_card.setAddress(card_edittext_address.getText().toString());
                    HttpClient.user.self_card.setCompany(card_edittext_company.getText().toString());
                    HttpClient.user.self_card.setTitle(card_edittext_title.getText().toString());
                    HttpClient.updateCard(HttpClient.user.self_card, (data)->{
                        if(data == null) {
                            Log.d("HttpClient","update card failed!");
                            return;
                        }
                    });
                    break;
                case R.id.btn_cancel:
                    card_edittext_name.setText(HttpClient.user.self_card.getName());
                    card_edittext_mobile_phone.setText(HttpClient.user.self_card.getMobile_phone());
                    card_edittext_email.setText(HttpClient.user.self_card.getEmail());
                    card_edittext_address.setText(HttpClient.user.self_card.getAddress());
                    card_edittext_company.setText(HttpClient.user.self_card.getCompany());
                    card_edittext_title.setText(HttpClient.user.self_card.getTitle());
                    break;
                case R.id.add_tags:
                    setAlertDialog(v);
                    dialog.show();
                    break;
                case R.id.btn_tag_OK_dialog:
                    if (edit != null) {
                        Tag new_tag = new Tag();
                        new_tag.setText(edit.getText().toString());
                        new_tag.setCount(1);
                        List<Long> taggedto = new LinkedList<>();
                        taggedto.add(HttpClient.user.getId());
                        new_tag.setTaggedto(taggedto);

                        tag_adapter.tags.add(new_tag);
                        tag_adapter.resetTags();
                        HttpClient.addTag(new_tag, (data)->{
                            if(data == null) {
                                Log.d("HttpClient","add new tag failed!");
                            }
                            else{
                                List<Tag> tag_list = HttpClient.user.getTags();
                                tag_list.add((Tag)data);
                                HttpClient.user.setTags(tag_list);
                                HttpClient.updateUser(HttpClient.user, (data_1)->{
                                    if(data_1 == null) {
                                        Log.d("HttpClient","update when adding new tag failed!");
                                        return;
                                    }
                                });
                            }
                        });


                        tag_adapter.notifyDataSetChanged();
                    }

                    dialog.dismiss();
                    break;
                case R.id.btn_tag_cancel_dialog:
                    dialog.dismiss();
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


    private void pickLocalPhoto(){
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(getActivity(), FoldableListActivity.class));
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
                        Toast.makeText(this.getActivity(), image_id, Toast.LENGTH_LONG).show();
                        GlideHelper.loadPaintingImage(card_img, image_id);
                    }
                    else{
                        Toast.makeText(this.getActivity(), "data is null", Toast.LENGTH_LONG).show();
                    }
                    break;
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
        Log.d("pickPhoto", image_path);
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
        Button btn_OK = (Button) contview.findViewById(R.id.btn_tag_OK_dialog);
        Button btn_cancel = (Button) contview.findViewById(R.id.btn_tag_cancel_dialog);
        f3_clickListener btn_tag_click_listener = new f3_clickListener(edit, tag_adapter);
        btn_OK.setOnClickListener(btn_tag_click_listener);
        btn_cancel.setOnClickListener(btn_tag_click_listener);
        dialog = new AlertDialog.Builder(getActivity()).setView(contview)
                .create();
    }


}
