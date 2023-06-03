package com.demo.cookbook.ui.home;

public class CookBookBean {
    private String img;
    private String name;

    public CookBookBean(String img, String name) {
        this.img = img;
        this.name = name;
    }

    public String getImgUrl() {
        return img;
    }

    public void setImgUrl(String imgUrl) {
        this.img = imgUrl;
    }

    public String getContent() {
        return name;
    }

    public void setContent(String content) {
        this.name = content;
    }
}
