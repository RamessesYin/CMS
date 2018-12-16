package com.alexvasilkov.foldablelayout.sample.data;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class Tag {
    public long id;
    public String count;
    public String valid;
    public String text;
    public List<User> taggedto;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<User> getTaggedto() {
        return taggedto;
    }

    public void setTaggedto(List<User> taggedto) {
        this.taggedto = taggedto;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJsonTree(this).toString();
    }
}
