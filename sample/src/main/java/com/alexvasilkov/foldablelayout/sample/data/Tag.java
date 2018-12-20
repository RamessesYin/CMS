package com.alexvasilkov.foldablelayout.sample.data;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.Expose;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Tag {
    public long id;
    public int count;
    public String valid;
    public String text;
    private String users;

    @Expose(serialize = false, deserialize = false)
    private transient List<Long> taggedto;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
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


    public List<Long> getTaggedto() {
        if (taggedto == null){
            taggedto = new ArrayList<>();
            if(users!=null){
                String[] ids = users.split("[\\[\\],]");
                for(String id : ids)
                    if(!id.isEmpty())
                        taggedto.add(Long.parseLong(id));
            }
        }
        return taggedto;
    }

    public void setTaggedto(List<Long> taggedto) {
        this.taggedto = taggedto;
        if (taggedto != null)
            users = taggedto.toString();
    }

    @Override
    public String toString() {
        if (taggedto != null)
            users = taggedto.toString();
        return new Gson().toJson(this);
    }


}

