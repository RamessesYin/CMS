package com.alexvasilkov.foldablelayout.sample.data;


import android.app.Application;
import android.widget.Toast;

import com.google.gson.Gson;

import okhttp3.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HttpClient {

    public static User user;

    public interface OnDataReceived {
        void callback(Object data);
    }


    public static void main (String[] args){



        HttpClient.getUser(1544704862401l,(data)->{
            if(data==null)
                return;
           User user = (User) data;
//            System.out.println(user);
//            System.out.println(user.getTags());

            Tag tag=user.tags.get(0);

            ArrayList<Long> tags = new ArrayList<Long>();
            tags.add(user.id);
            tag.setTaggedto(tags);

            System.out.println("toString: "+ tag);
            System.out.println(tag.getTaggedto());
//            updateTag(tag,(d)->{
//                Tag t = (Tag) d;
//                System.out.println(t);
//            });
        });



    }

    public static void getUser(long id, OnDataReceived onDataReceived){

        String url = "http://119.23.241.119:8080/Entity/U61e825a37daf4/CMS/User/" + String.valueOf(id);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(2000,TimeUnit.MILLISECONDS).build();
        final Request request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println( "failed when get user "+String.valueOf(id));
                System.out.println(e);
                onDataReceived.callback(null);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                System.out.println( "onResponse: " + res);

                User user = (User) new Gson().fromJson(res,User.class);
                onDataReceived.callback(user);
            }
        });
    }

    public static void addUser(User user,OnDataReceived onDataReceived){

        String url = "http://119.23.241.119:8080/Entity/U61e825a37daf4/CMS/User/";
        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        final Request request = new Request.Builder()
                .url(url)
                .header("Content-Type","application/json")
                .post(RequestBody.create(mediaType,user.toString()))
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println( "failed when add user.");
                onDataReceived.callback(null);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                System.out.println( "onResponse: " + res);
                User user = (User) new Gson().fromJson(res,User.class);
                onDataReceived.callback(user);
            }
        });
    }

    public static void updateUser(User user,OnDataReceived onDataReceived){
        if(user==null || user.getId()==0){
            onDataReceived.callback(null);
            return;
        }

        String url = "http://119.23.241.119:8080/Entity/U61e825a37daf4/CMS/User/"+String.valueOf(user.getId());
        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        final Request request = new Request.Builder()
                .url(url)
                .header("Content-Type","application/json")
                .put(RequestBody.create(mediaType,user.toString()))
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println( "failed when update user.");
                onDataReceived.callback(null);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                System.out.println( "onResponse: " + res);
                User user = (User) new Gson().fromJson(res,User.class);
                onDataReceived.callback(user);
            }
        });
    }

    public static void getTag(long id,OnDataReceived onDataReceived){

        String url = "http://119.23.241.119:8080/Entity/U61e825a37daf4/CMS/Tag/" + String.valueOf(id);
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println( "failed when get tag "+String.valueOf(id));
                onDataReceived.callback(null);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                System.out.println( "onResponse: " + res);

                Tag tag = (Tag) new Gson().fromJson(res,Tag.class);
                onDataReceived.callback(tag);
            }
        });
    }

    public static void addTag(Tag tag,OnDataReceived onDataReceived){

        String url = "http://119.23.241.119:8080/Entity/U61e825a37daf4/CMS/Tag/";
        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        final Request request = new Request.Builder()
                .url(url)
                .header("Content-Type","application/json")
                .post(RequestBody.create(mediaType,tag.toString()))
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println( "failed when add tag. ");
                onDataReceived.callback(null);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                System.out.println( "onResponse: " + res);
                Tag tag = (Tag) new Gson().fromJson(res,Tag.class);
                onDataReceived.callback(tag);
            }
        });
    }

    public static void updateTag(Tag tag,OnDataReceived onDataReceived){

        String url = "http://119.23.241.119:8080/Entity/U61e825a37daf4/CMS/Tag/"+ String.valueOf(tag.getId());;
        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        final Request request = new Request.Builder()
                .url(url)
                .header("Content-Type","application/json")
                .put(RequestBody.create(mediaType,tag.toString()))
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println( "failed when add tag. ");
                onDataReceived.callback(null);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                System.out.println( "onResponse: " + res);
                Tag tag = (Tag) new Gson().fromJson(res,Tag.class);
                onDataReceived.callback(tag);
            }
        });
    }

    public static void getCard(long id,OnDataReceived onDataReceived){

        String url = "http://119.23.241.119:8080/Entity/U61e825a37daf4/CMS/Card/" + String.valueOf(id);
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println( "failed when get card "+String.valueOf(id));
                onDataReceived.callback(null);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                System.out.println( "onResponse: " + res);
                Card card = (Card) new Gson().fromJson(res,Card.class);
                onDataReceived.callback(card);
            }
        });
    }

    public static void addCard(Card card,OnDataReceived onDataReceived){

        String url = "http://119.23.241.119:8080/Entity/U61e825a37daf4/CMS/Card/";
        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        final Request request = new Request.Builder()
                .url(url)
                .header("Content-Type","application/json")
                .post(RequestBody.create(mediaType,card.toString()))
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println( "failed when add card. ");
                onDataReceived.callback(null);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                System.out.println( "onResponse: " + res);
                Card card = (Card) new Gson().fromJson(res,Card.class);
                onDataReceived.callback(card);
            }
        });
    }

    public static void updateCard(Card card,OnDataReceived onDataReceived){

        String url = "http://119.23.241.119:8080/Entity/U61e825a37daf4/CMS/Card/"+String.valueOf(card.getId());
        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        final Request request = new Request.Builder()
                .url(url)
                .header("Content-Type","application/json")
                .put(RequestBody.create(mediaType,card.toString()))
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println( "failed when update card. ");
                onDataReceived.callback(null);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                System.out.println( "onResponse: " + res);
                Card card = (Card) new Gson().fromJson(res,Card.class);
                onDataReceived.callback(card);
            }
        });
    }

}

