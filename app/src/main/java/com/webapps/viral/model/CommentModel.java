package com.webapps.viral.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommentModel {


        @SerializedName("comment_id")
        @Expose
        private String commentId;
        @SerializedName("node_id")
        @Expose
        private String nodeId;
        @SerializedName("node_type")
        @Expose
        private String nodeType;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("user_type")
        @Expose
        private String userType;
        @SerializedName("text")
        @Expose
        private String text;
        @SerializedName("image")
        @Expose
        private Object image;
        @SerializedName("time")
        @Expose
        private String time;
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
        @SerializedName("replies")
        @Expose
        private String replies;
        @SerializedName("user_name")
        @Expose
        private String userName;
        @SerializedName("user_picture")
        @Expose
        private Object userPicture;

        public String getCommentId() {
            return commentId;
        }

        public void setCommentId(String commentId) {
            this.commentId = commentId;
        }

        public String getNodeId() {
            return nodeId;
        }

        public void setNodeId(String nodeId) {
            this.nodeId = nodeId;
        }

        public String getNodeType() {
            return nodeType;
        }

        public void setNodeType(String nodeType) {
            this.nodeType = nodeType;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Object getImage() {
            return image;
        }

        public void setImage(Object image) {
            this.image = image;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
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

        public String getReplies() {
            return replies;
        }

        public void setReplies(String replies) {
            this.replies = replies;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public Object getUserPicture() {
            return userPicture;
        }

        public void setUserPicture(Object userPicture) {
            this.userPicture = userPicture;
        }

    }
