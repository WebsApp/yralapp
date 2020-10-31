package com.webapps.viral.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopUserModel {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("user_name")
        @Expose
        private String userName;
        @SerializedName("src")
        @Expose
        private String src;
        @SerializedName("user_bio")
        @Expose
        private Object userBio;
    private boolean isChecked = false;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public Object getUserBio() {
            return userBio;
        }

        public void setUserBio(Object userBio) {
            this.userBio = userBio;
        }

    }

