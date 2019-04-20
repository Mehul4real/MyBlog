package com.example.myblog;

public class Blog {
    private  String title;
    private String image;
    private String desc;
   // private  boolean permission;

    public Blog() {
    }

    public Blog(String title, String image, String desc/*, boolean permission*/) {
        this.title = title;
        this.image = image;
        this.desc = desc;
      //  this.permission = permission;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

   /* public boolean isPermission() {
        return permission;
    }

    public void setPermission(boolean permission) {
        this.permission = permission;
    }*/


}
