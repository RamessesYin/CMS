package com.alexvasilkov.foldablelayout.sample.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;


import java.util.List;

public class Card {
    public long id;
    public String name;
    public String mobile_phone;
    public String email;
    public String address;
    public String company;
    public String title;
    public String source;
    public int image;
    public long valid;
    private String users;

    @Expose(serialize = false, deserialize = false)
    private transient List<Long> sharedto;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile_phone() {
        return mobile_phone;
    }

    public void setMobile_phone(String mobile_phone) {
        this.mobile_phone = mobile_phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public long getValid() {
        return valid;
    }

    public void setValid(long valid) {
        this.valid = valid;
    }

    public List<Long> getSharedto() {
        if (sharedto == null)
            sharedto = new Gson().fromJson(users, List.class);
        return sharedto;
    }

    public void setSharedto(List<Long> sharedto) {
        this.sharedto = sharedto;
        if(sharedto !=null)
            users = sharedto.toString();
    }

    @Override
    public String toString() {
        if (sharedto != null)
            users = sharedto.toString();
        return new GsonBuilder().setPrettyPrinting().create().toJsonTree(this).toString();
    }

}
