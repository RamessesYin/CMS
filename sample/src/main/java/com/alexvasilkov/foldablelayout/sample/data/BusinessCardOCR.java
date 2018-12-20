package com.alexvasilkov.foldablelayout.sample.data;

import android.util.Log;

import com.baidu.aip.ocr.AipOcr;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BusinessCardOCR {
    public static final String APP_ID = "15113691";
    public static final String API_KEY = "CHc4GtL262uwaCOGMtB9XQTT";
    public static final String SECRET_KEY = "dQISsmGxsFh894sIfhT7lqPYjekmN6an";

    public HashMap opt = new HashMap<String, String>();
    public ArrayList<String> res = new ArrayList<String>();

    public String name;
    public String title;
    public String address;
    public String mobileePhone;
    public String fax;
    public String email;
    public String company;


    public void ScanBusinessCard(byte[] byte_array,HttpClient.OnDataReceived onDataReceived) {
        // 初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
//        client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
//        client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
//        System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");

        new Thread(new Runnable() {
            @Override
            public void run() {
                Card card = new Card();
                try {
                    JSONObject json = client.general(byte_array, new HashMap<String, String>());

                    Log.d("BusinessCardOCR", json.toString(2));
                    JSONArray words = null;
                    words = json.getJSONArray("words_result");

                    for (int i = 0; i < words.length(); i++) {
                        JSONObject j = (JSONObject) words.get(i);
                        res.add(j.get("words").toString());

                    }




        for (String s : res) {
            if (s.matches("^[\u4E00-\u9FA5a-zA-Z]{2,4}")) {
                name = s;
                System.out.println(name);
                break;
            }
        }

        for (String s : res) {
            if (s.contains("地址")) {
                int begin = s.lastIndexOf("地址");
                s = s.substring(begin + 2).replace(":", "");
                address = s;
                System.out.println(address);
                break;
            }
        }

        for (String s : res) {
            Pattern p = Pattern.compile(".*(1[0-9]{10}).*");
            Matcher m = p.matcher(s);
            if (m.matches()) {
                mobileePhone = m.group(1);
                System.out.println(mobileePhone);
                break;
            }
        }

        for (String s : res) {
            Pattern p = Pattern.compile(".*([0-9]{4}-[0-9]{8}).*");
            Matcher m = p.matcher(s);
            if (m.matches()) {
                fax = m.group(1);
                System.out.println(fax);
                break;
            }
        }

        for (String s : res) {
            Pattern p = Pattern.compile("(E-mail|email|e-mail|E-Mail)[ :]?([a-zA-Z0-9_-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}).*");
            Matcher m = p.matcher(s);
            if (m.matches()) {
                email = m.group(2);
                System.out.println(email);
                break;
            }
        }

//                    card.setName(json.getJSONArray("NAME").getString(0));
//                    card.setTitle(json.getJSONArray("TITLE").getString(0));
//                    card.setAddress(json.getJSONArray("ADDR").getString(0));
//                    card.setCompany(json.getJSONArray("COMPANY").getString(0));
//                    card.setEmail(json.getJSONArray("EMAIL").getString(0));
//                    card.setMobile_phone(json.getJSONArray("MOBILE").getString(0));

                    card.setName(name);
                    card.setTitle(title);
                    card.setAddress(address);
                    card.setCompany(company);
                    card.setEmail(email);
                    card.setMobile_phone(mobileePhone);
                    onDataReceived.callback(card);

                } catch (JSONException e) {
                    e.printStackTrace();
                    onDataReceived.callback(card);
                }

            }
        }).start();
    }

}
