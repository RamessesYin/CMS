package com.alexvasilkov.foldablelayout.sample.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alexvasilkov.foldablelayout.sample.R;
import com.alexvasilkov.foldablelayout.sample.data.HttpClient;
import com.alexvasilkov.foldablelayout.sample.data.Tag;
import com.alexvasilkov.foldablelayout.sample.data.User;
import com.alexvasilkov.foldablelayout.sample.items.TagsAdapter;
import com.blikoon.qrcodescanner.QrCodeActivity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TagsActivity extends AppCompatActivity {

    private ListView tag_info;
    private TagsAdapter tag_adapter;
    private User user;

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tags);

        Intent i =getIntent();
        long id = i.getLongExtra("user",0);
        tag_info = (ListView) findViewById(R.id.list_tag_view);

        HttpClient.getUser(id,(data)->{
            user = (User) data;
            if(user==null)
                return;
            mHandler.post(()->{

                tag_adapter = new TagsAdapter(this);
                tag_adapter.setItemsList(user.tags);
                tag_adapter.notifyDataSetChanged();
                tag_info.setAdapter(tag_adapter);
            });
        });



        TextView btn_add_tag = (TextView) findViewById(R.id.text_add_tag);
        btn_add_tag.setOnClickListener((view) -> {

            EditText txtNewTag = new EditText(this);
            txtNewTag.setHint("添加新标签");
            txtNewTag.setText("好人");
            // EditText txtPort = new EditText(this);
            // txtPort.setHint("Port");
            // txtPort.setText("8080");

            LinearLayout root = new LinearLayout(this);
            root.setOrientation(LinearLayout.VERTICAL);
            root.addView(txtNewTag);
            // root.addView(txtPort);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("添加新标签");
            builder.setView(root);
            builder.setNegativeButton("取消", (dialog, which) -> {
                dialog.dismiss();
            });
            builder.setPositiveButton("确认", (dialog, which) -> {

                if(user==null)
                    return;

                Tag new_tag = new Tag();
                new_tag.setText(txtNewTag.getText().toString());
                new_tag.setCount(1);
                List<Long> taggedto = new LinkedList<>();
                taggedto.add(user.getId());
                new_tag.setTaggedto(taggedto);
                Log.d("TAGS", "add tag");


                HttpClient.addTag(new_tag, (data) -> {
                    if (data == null) {
                        Log.d("HttpClient", "add new tag failed!");
                    } else {
                        List<Tag> tag_list = user.getTags();
                        if(tag_list==null)
                            tag_list = new ArrayList<>();
                        tag_list.add((Tag) data);
                        user.setTags(tag_list);
                        Log.d("TAGS", user.toString());

                        mHandler.post(() -> tag_adapter.notifyDataSetChanged());

                        HttpClient.updateUser(user, (data_1) -> {
                            if (data_1 == null) {
                                Log.d("HttpClient", "update when adding new tag failed!");
                                return;
                            }
                        });
                    }
                });

            });
            builder.create().show();

        });

    }
}
