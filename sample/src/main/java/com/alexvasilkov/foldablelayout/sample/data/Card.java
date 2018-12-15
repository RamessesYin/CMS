package com.alexvasilkov.foldablelayout.sample.data;

import net.sf.json.JSONObject;

import java.util.List;

public class Card {
    private long id;
    private String name;
    private String mobile_phone;
    private String email;
    private String address;
    private String company;
    private String title;
    private String source;
    private String image;
    private long valid;
    private List<User> sharedto;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getValid() {
        return valid;
    }

    public void setValid(long valid) {
        this.valid = valid;
    }

    public List<User> getSharedto() {
        return sharedto;
    }

    public void setSharedto(List<User> sharedto) {
        this.sharedto = sharedto;
    }

    @Override
    public String toString() {
        return JSONObject.fromObject(this).toString(2);
    }
}
