package com.alexvasilkov.foldablelayout.sample.activities;

import android.util.Base64;
import android.util.Log;

import com.baidu.aip.client.BaseClient;
import com.baidu.aip.error.AipError;
import com.baidu.aip.http.AipRequest;
import com.baidu.aip.ocr.AipOcr;
import com.baidu.aip.util.Base64Util;
import com.baidu.aip.util.Util;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import org.json.JSONObject;

public class AipOcr_test extends AipOcr {
    public AipOcr_test(String appId, String apiKey, String secretKey) {
        super(appId, apiKey, secretKey);
    }

    @Override
    public JSONObject businessCard(byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        this.preOperation(request);
        String base64Content = Base64.encodeToString(image, Base64.NO_WRAP);
        Log.d("image_str", base64Content);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }

        request.setUri("https://aip.baidubce.com/rest/2.0/ocr/v1/business_card");
        this.postOperation(request);
        return this.requestServer(request);
    }

}
