package com.webapps.viral.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhotoModel {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("post_id")
        @Expose
        private String postId;
        @SerializedName("src")
        @Expose
        private String src;

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

    }

