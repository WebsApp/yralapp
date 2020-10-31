package com.webapps.viral.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserModel {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("post_id")
        @Expose
        private String postId;
        @SerializedName("src")
        @Expose
        private String src;
        @SerializedName("gender")
        @Expose
        private String gender;
    @SerializedName("city")
    @Expose
    private String city;
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPostId() {
            return postId;
        }

        public void setPostId(String postId) {
            this.postId = postId;
        }

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }



}
