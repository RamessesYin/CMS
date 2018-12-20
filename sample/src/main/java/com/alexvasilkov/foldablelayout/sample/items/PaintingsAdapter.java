package com.alexvasilkov.foldablelayout.sample.items;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alexvasilkov.android.commons.adapters.ItemsAdapter;
import com.alexvasilkov.android.commons.ui.ContextHelper;
import com.alexvasilkov.android.commons.ui.Views;
import com.alexvasilkov.foldablelayout.sample.R;
import com.alexvasilkov.foldablelayout.sample.activities.FoldableListActivity;
import com.alexvasilkov.foldablelayout.sample.activities.UnfoldableDetailsActivity;
import com.alexvasilkov.foldablelayout.sample.activities.fragment.Fragment1;
import com.alexvasilkov.foldablelayout.sample.activities.fragment.Fragment2;
import com.alexvasilkov.foldablelayout.sample.utils.GlideHelper;

import java.util.Arrays;

public class PaintingsAdapter extends ItemsAdapter<Painting, PaintingsAdapter.ViewHolder>
        implements View.OnClickListener {

    private static final int SELECT_LOCAL_IMAGE_RESULT_CODE = 1;

    private Fragment1 fragment1;
    private AlertDialog dialog;
    private pa_clickListener btn_click_listener;

    public PaintingsAdapter(Context context, Fragment1 fragment) {
        setItemsList(Arrays.asList(Painting.getAllPaintings(context.getResources())));
        fragment1 = fragment;
    }

    @Override
    protected ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        final ViewHolder holder = new ViewHolder(parent);
        holder.image.setOnClickListener(this);
        return holder;
    }

    @Override
    protected void onBindHolder(ViewHolder holder, int position) {
        final Painting item = getItem(position);

        holder.image.setTag(R.id.list_item_image, item);
        GlideHelper.loadPaintingImage(holder.image, item.getImageId());
        holder.title.setText(item.getTitle());
        holder.btn_add_image.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View view) {
        final Painting item = (Painting) view.getTag(R.id.list_item_image);
        final Activity activity = ContextHelper.asActivity(view.getContext());


        if (activity instanceof UnfoldableDetailsActivity) {
            //fragment1.openDetails(view, item);
        } else if (activity instanceof FoldableListActivity) {
            Toast.makeText(activity, item.getTitle(), Toast.LENGTH_SHORT).show();
//            TextView textView = new TextView(view.getContext());
//            textView.setText("使用"+ item.getTitle() +"作为名片背景？");
//
//            LinearLayout root = new LinearLayout(view.getContext());
//            root.setOrientation(LinearLayout.VERTICAL);
//            root.addView(textView);
//            // root.addView(txtPort);

            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("使用"+ item.getTitle() +"作为名片背景？");
            //builder.setView(root);
            builder.setNegativeButton("取消", (dialog, which) -> {
                dialog.dismiss();
            });
            builder.setPositiveButton("确认", (dialog, which) -> {
                Intent in = new Intent();
                activity.setResult(Activity.RESULT_OK, in.putExtra("img_id", item.getImageId()));
                dialog.dismiss();
                activity.finish();
            });
            builder.create().show();
        }
    }

    static class ViewHolder extends ItemsAdapter.ViewHolder {
        final ImageView image;
        final TextView title;
        final Button btn_add_image;

        ViewHolder(ViewGroup parent) {
            super(Views.inflate(parent, R.layout.list_item));
            image = Views.find(itemView, R.id.list_item_image);
            title = Views.find(itemView, R.id.list_item_title);
            btn_add_image = Views.find(itemView, R.id.btn_add_new_card);
        }
    }

    private void setAlertDialog(final View view, String title, int image_id) {
        final Activity activity = ContextHelper.asActivity(view.getContext());
        LayoutInflater factory = LayoutInflater.from(activity. getApplicationContext());
        View contview = factory.inflate(R.layout.scan_card_dialog, null);
        contview.setBackgroundColor(Color.WHITE);// 设置该外部布局的背景
        final TextView text = (TextView) contview
                .findViewById(R.id.card_scan_text);// 找到该外部布局对应的EditText控件
        text.setText("是否选择当前图片"+ title +"作为名片背景？");
        Button btn_OK = (Button) contview.findViewById(R.id.btn_OK_dialog);
        Button btn_cancel = (Button) contview.findViewById(R.id.btn_Cancel_dialog);

        btn_click_listener = new pa_clickListener(activity, image_id);
        btn_OK.setOnClickListener(btn_click_listener);
        btn_cancel.setOnClickListener(btn_click_listener);

        dialog = new AlertDialog.Builder(activity).setView(contview)
                .create();
    }

    protected class pa_clickListener implements  View.OnClickListener{
        final Activity activity;
        private int image_id;

        public pa_clickListener(Activity activity, int image_id){
            this.activity = activity;
            this.image_id = image_id;
        }

        @Override
        public void onClick(View v){
            switch (v.getId()){
                case R.id.btn_OK_dialog:
                    Intent in = new Intent();
                    activity.setResult(Activity.RESULT_OK, in.putExtra("img_id", image_id));
                    dialog.dismiss();
                    activity.finish();
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
