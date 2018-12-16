package com.alexvasilkov.foldablelayout.sample.activities.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.alexvasilkov.foldablelayout.sample.R;
import com.alexvasilkov.foldablelayout.sample.activities.MainActivity;
import com.blikoon.qrcodescanner.QrCodeActivity;

public class Fragment2 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getActivity()).inflate(R.layout.fragment2, container, false);

    }

    private void scanAddress() {
//        Intent i = new Intent(MainActivity.this, QrCodeActivity.class);
//        startActivityForResult( i,REQUEST_CODE_QR_SCAN);
        }
        }
