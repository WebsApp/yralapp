package com.webapps.viral.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PostModel {

    public class Datum {

        @SerializedName("photo_id")
        @Expose
        private String photoId;

        @SerializedName("post_id")
        @Expose
        private String postId;
        @SerializedName("album_id")
        @Expose
        private String albumId;
        @SerializedName("source")
        @Expose
        private String source;
        @SerializedName("blur")
        @Expose
        private String blur;
        @SerializedName("reaction_like_count")
        @Expose
        private String reactionLikeCount;
        @SerializedName("reaction_love_count")
        @Expose
        private String reactionLoveCount;
        @SerializedName("reaction_haha_count")
        @Expose
        private String reactionHahaCount;
        @SerializedName("reaction_yay_count")
        @Expose
        private String reactionYayCount;
        @SerializedName("reaction_wow_count")
        @Expose
        private String reactionWowCount;
        @SerializedName("reaction_sad_count")
        @Expose
        private String reactionSadCount;
        @SerializedName("reaction_angry_count")
        @Expose
        private String reactionAngryCount;
        @SerializedName("comments")
        @Expose
        private String comments;
        @SerializedName("video_id")
        @Expose
        private String videoId;
        @SerializedName("thumbnail")
        @Expose
        private String thumbnail;
        @SerializedName("views")
        @Expose
        private String views;

        public String getPhotoId() {
            return photoId;
        }

        public void setPhotoId(String photoId) {
            this.photoId = photoId;
        }

        public String getPostId() {
            return postId;
        }

        public void setPostId(String postId) {
            this.postId = postId;
        }

        public String getAlbumId() {
            return albumId;
        }

        public void setAlbumId(String albumId) {
            this.albumId = albumId;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getBlur() {
            return blur;
        }

        public void setBlur(String blur) {
            this.blur = blur;
        }

        public String getReactionLikeCount() {
            return reactionLikeCount;
        }

        public void setReactionLikeCount(String reactionLikeCount) {
            this.reactionLikeCount = reactionLikeCount;
        }

        public String getReactionLoveCount() {
            return reactionLoveCount;
        }

        public void setReactionLoveCount(String reactionLoveCount) {
            this.reactionLoveCount = reactionLoveCount;
        }

        public String getReactionHahaCount() {
            return reactionHahaCount;
        }

        public void setReactionHahaCount(String reactionHahaCount) {
            this.reactionHahaCount = reactionHahaCount;
        }

        public String getReactionYayCount() {
            return reactionYayCount;
        }

        public void setReactionYayCount(String reactionYayCount) {
            this.reactionYayCount = reactionYayCount;
        }

        public String getReactionWowCount() {
            return reactionWowCount;
        }

        public void setReactionWowCount(String reactionWowCount) {
            this.reactionWowCount = reactionWowCount;
        }

        public String getReactionSadCount() {
            return reactionSadCount;
        }

        public void setReactionSadCount(String reactionSadCount) {
            this.reactionSadCount = reactionSadCount;
        }

        public String getReactionAngryCount() {
            return reactionAngryCount;
        }

        public void setReactionAngryCount(String reactionAngryCount) {
            this.reactionAngryCount = reactionAngryCount;
        }

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }

        public String getVideoId() {
            return videoId;
        }

        public void setVideoId(String videoId) {
            this.videoId = videoId;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getViews() {
            return views;
        }

        public void setViews(String views) {
            this.views = views;
        }

    }

    @SerializedName("feeling")
    @Expose
    private String feeling;
        @SerializedName("username")
        @Expose
        private String username;
    @SerializedName("location")
    @Expose
    private String location;
        @SerializedName("text")
        @Expose
        private String text;

        @SerializedName("time")
        @Expose
        private String time;
        @SerializedName("pic")
        @Expose
        private Object pic;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("user_id")
        @Expose
        private String user_id;
        @SerializedName("reaction_like_count")
        @Expose
        private String reactionLikeCount;
        @SerializedName("comments")
        @Expose
        private String comments;
        @SerializedName("share")
        @Expose
        private String share;
    @SerializedName("susername")
    @Expose
    private String susername;
    @SerializedName("stime")
    @Expose
    private String stime;
    @SerializedName("spic")
    @Expose
    private Object spic;
    @SerializedName("stype")
    @Expose
    private String stype;
    @SerializedName("suser_id")
    @Expose
    private String suser_id;
    @SerializedName("post_id")
    @Expose
    private String post_id;
    @SerializedName("islike")
    @Expose
    private int islike;
    @SerializedName("saved")
    @Expose
    private int saved;

    @SerializedName("comments_disabled")
    @Expose
    private String comments_disabled;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("data")
        @Expose
        private ArrayList<Datum> data = null;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setIslike(int islike) {
        this.islike = islike;
    }
    public int getIslike() {
        return islike;
    }

    public void setSaved(int saved) {
        this.saved = saved;
    }
    public int getSaved() {
        return saved;
    }

    public  void  setFeeling(String feeling)
    {
        this.feeling=feeling;
    }
    public  String getFeeling()
    {
        return  feeling;
    }
    public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    public String getPost_id() {
        return post_id;
    }
    public void setComments_disabled(String comments_disabled) {
        this.comments_disabled = comments_disabled;
    }
    public String getComments_disabled() {
        return comments_disabled;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSusername() {
        return susername;
    }

    public void setSusername(String susername) {
        this.susername = susername;
    }
    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }
    public String getSuser_id() {
        return suser_id;
    }

    public void setSuser_id(String suser_id) {
        this.suser_id = suser_id;
    }
    public Object getSpic() {
        return spic;
    }

    public void setSpic(Object spic) {
        this.spic = spic;
    }

    public String getStype() {
        return stype;
    }

    public void setStype(String stype) {
        this.stype = stype;
    }
    public String getText() {
        return text;
    }
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
    public String getReactionLikeCount() {
        return reactionLikeCount;
    }

    public void setReactionLikeCount(String reactionLikeCount) {
        this.reactionLikeCount = reactionLikeCount;
    }
    public void setText(String text) {
        this.username = text;
    }
        public Object getPic() {
            return pic;
        }

        public void setPic(Object pic) {
            this.pic = pic;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
    }

        public ArrayList<Datum> getData() {
            return data;
        }

        public void setData(ArrayList<Datum> data) {
            this.data = data;
        }

    }

