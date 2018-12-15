package com.alexvasilkov.foldablelayout.sample.data;

import net.sf.json.JSONObject;

import java.util.List;

public class Tag {
    private long id;
    private String count;
    private String valid;
    private String text;
    private List<User> taggedto;

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
        return JSONObject.fromObject(this).toString(2);
    }
}
