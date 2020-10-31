package com.webapps.viral.model;

public class ChildModel {

    private int image;
    private String title;
    private String CategoryImageUrl;

    private String id;
    private String CategoryId;
    private String Name;
    private String VideoUrl;
    private String VideoId;
    private String VideoName;
    private String Duration;
    private String Description;
    private String ImageUrl;
    private String Type;
    private String ViewC;
    private  String Subcatid;
 public ChildModel(int image ,String title)
 {
     this.image=image;
     this.title=title;
 }
    public ChildModel()
    {

    }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getImage() { return image; }
    public void setImage(int image) { this.image = image; }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getSubcatid() {
        return Subcatid;
    }

    public void setSubcatid(String Subcatid) {
        this.Subcatid = Subcatid;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryid) {
        this.CategoryId = categoryid;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getVideoUrl() {
        return VideoUrl;
    }

    public void setVideoUrl(String videourl) {
        this.VideoUrl = videourl;
    }


    public String getVideoId() {
        return VideoId;
    }

    public void setVideoId(String videoid) {
        this.VideoId = videoid;
    }

    public String getVideoName() {
        return VideoName;
    }

    public void setVideoName(String videoname) {
        this.VideoName = videoname;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        this.Duration = duration;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String desc) {
        this.Description = desc;
    }

    public String getImageUrl() {
        return ImageUrl;
    }
    public void setImageUrl(String imageurl) {
        this.ImageUrl = imageurl;
    }

    public String getType() {
        return Type;
    }
    public void setType(String Type) {
        this.Type = Type;
    }

    public String getViewC() {
        return ViewC;
    }
    public void setViewC(String ViewC) {
        this.ViewC = ViewC;
    }

}
