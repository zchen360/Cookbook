package com.demo.cookbook.bean;

import java.util.List;

public class CookDetailBean {

    private List<String> Nutritional_Information;
    private Integer id;
    private String img;
    private List<String> ingredients;;
    private Boolean like;
    private List<String> method;
    private String name;
    private Integer rate;
    private Integer typeid;
    private Integer userrate;

    public List<String> getNutritional_Information() {
        return Nutritional_Information;
    }

    public void setNutritional_Information(List<String> nutritional_Information) {
        this.Nutritional_Information = nutritional_Information;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public Boolean getLike() {
        return like;
    }

    public void setLike(Boolean like) {
        this.like = like;
    }

    public List<String> getMethod() {
        return method;
    }

    public void setMethod(List<String> method) {
        this.method = method;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Integer getTypeid() {
        return typeid;
    }

    public void setTypeid(Integer typeid) {
        this.typeid = typeid;
    }

    public Integer getUserrate() {
        return userrate;
    }

    public void setUserrate(Integer userrate) {
        this.userrate = userrate;
    }
}
