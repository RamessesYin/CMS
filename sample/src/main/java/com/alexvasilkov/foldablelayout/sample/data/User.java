package com.alexvasilkov.foldablelayout.sample.data;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class User {

    public long id;
    public String user_name;
    public String mobile_phone;
    public String email;
    public String address;
    public String company;
    public String title;
    public String birthday;
    public long authority;
    public long card_id;
    public Card self_card;
    public List<Tag> tags;
    public List<Card> cards;

    public User() {
        this.id = 0;
        this.user_name = "";
        this.mobile_phone = "";
        this.email = "";
        this.address = "";
        this.company = "";
        this.title = "";
        this.birthday = "";
        this.authority = 0;
        this.card_id = 0;
        this.self_card = new Card();
        this.tags = new ArrayList<>();
        this.cards = new ArrayList<>();
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public long getAuthority() {
        return authority;
    }

    public void setAuthority(long authority) {
        this.authority = authority;
    }

    public long getCard_id() {
        return card_id;
    }

    public void setCard_id(long card_id) {
        this.card_id = card_id;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public Card getSelf_card() {
        return self_card;
    }

    public void setSelf_card(Card self_card) {
        this.self_card = self_card;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJsonTree(this).toString();
    }
}
