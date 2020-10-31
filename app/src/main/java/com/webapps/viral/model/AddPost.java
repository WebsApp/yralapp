package com.webapps.viral.model;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddPost {


    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("user_type")
    @Expose
    private String userType;
    @SerializedName("in_wall")
    @Expose
    private Integer inWall;
    @SerializedName("wall_id")
    @Expose
    private String wallId;
    @SerializedName("in_group")
    @Expose
    private Integer inGroup;
    @SerializedName("group_id")
    @Expose
    private Object groupId;
    @SerializedName("group_approved")
    @Expose
    private Integer groupApproved;
    @SerializedName("in_event")
    @Expose
    private Integer inEvent;
    @SerializedName("event_id")
    @Expose
    private Object eventId;
    @SerializedName("event_approved")
    @Expose
    private Integer eventApproved;
    @SerializedName("author_id")
    @Expose
    private String authorId;
    @SerializedName("post_author_picture")
    @Expose
    private Object postAuthorPicture;
    @SerializedName("post_author_url")
    @Expose
    private String postAuthorUrl;
    @SerializedName("post_author_name")
    @Expose
    private String postAuthorName;
    @SerializedName("post_author_verified")
    @Expose
    private String postAuthorVerified;
    @SerializedName("wall_username")
    @Expose
    private Object wallUsername;
    @SerializedName("wall_fullname")
    @Expose
    private String wallFullname;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("time")
    @Expose
    private Object time;
    @SerializedName("location")
    @Expose
    private Object location;
    @SerializedName("privacy")
    @Expose
    private String privacy;
    @SerializedName("reaction_like_count")
    @Expose
    private Integer reactionLikeCount;
    @SerializedName("reaction_love_count")
    @Expose
    private Integer reactionLoveCount;
    @SerializedName("reaction_haha_count")
    @Expose
    private Integer reactionHahaCount;
    @SerializedName("reaction_yay_count")
    @Expose
    private Integer reactionYayCount;
    @SerializedName("reaction_wow_count")
    @Expose
    private Integer reactionWowCount;
    @SerializedName("reaction_sad_count")
    @Expose
    private Integer reactionSadCount;
    @SerializedName("reaction_angry_count")
    @Expose
    private Integer reactionAngryCount;
    @SerializedName("reactions_total_count")
    @Expose
    private Integer reactionsTotalCount;
    @SerializedName("comments")
    @Expose
    private Integer comments;
    @SerializedName("shares")
    @Expose
    private Integer shares;
    @SerializedName("feeling_action")
    @Expose
    private String feelingAction;
    @SerializedName("feeling_value")
    @Expose
    private String feelingValue;
    @SerializedName("feeling_icon")
    @Expose
    private String feelingIcon;
    @SerializedName("post_type")
    @Expose
    private String postType;
    @SerializedName("post_id")
    @Expose
    private Integer postId;
    @SerializedName("photos")
    @Expose
    private List<Photo> photos = null;
    @SerializedName("photos_num")
    @Expose
    private Integer photosNum;
    @SerializedName("text_plain")
    @Expose
    private String textPlain;
    @SerializedName("manage_post")
    @Expose
    private Boolean managePost;

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

    public Integer getInWall() {
        return inWall;
    }

    public void setInWall(Integer inWall) {
        this.inWall = inWall;
    }

    public String getWallId() {
        return wallId;
    }

    public void setWallId(String wallId) {
        this.wallId = wallId;
    }

    public Integer getInGroup() {
        return inGroup;
    }

    public void setInGroup(Integer inGroup) {
        this.inGroup = inGroup;
    }

    public Object getGroupId() {
        return groupId;
    }

    public void setGroupId(Object groupId) {
        this.groupId = groupId;
    }

    public Integer getGroupApproved() {
        return groupApproved;
    }

    public void setGroupApproved(Integer groupApproved) {
        this.groupApproved = groupApproved;
    }

    public Integer getInEvent() {
        return inEvent;
    }

    public void setInEvent(Integer inEvent) {
        this.inEvent = inEvent;
    }

    public Object getEventId() {
        return eventId;
    }

    public void setEventId(Object eventId) {
        this.eventId = eventId;
    }

    public Integer getEventApproved() {
        return eventApproved;
    }

    public void setEventApproved(Integer eventApproved) {
        this.eventApproved = eventApproved;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public Object getPostAuthorPicture() {
        return postAuthorPicture;
    }

    public void setPostAuthorPicture(Object postAuthorPicture) {
        this.postAuthorPicture = postAuthorPicture;
    }

    public String getPostAuthorUrl() {
        return postAuthorUrl;
    }

    public void setPostAuthorUrl(String postAuthorUrl) {
        this.postAuthorUrl = postAuthorUrl;
    }

    public String getPostAuthorName() {
        return postAuthorName;
    }

    public void setPostAuthorName(String postAuthorName) {
        this.postAuthorName = postAuthorName;
    }

    public String getPostAuthorVerified() {
        return postAuthorVerified;
    }

    public void setPostAuthorVerified(String postAuthorVerified) {
        this.postAuthorVerified = postAuthorVerified;
    }

    public Object getWallUsername() {
        return wallUsername;
    }

    public void setWallUsername(Object wallUsername) {
        this.wallUsername = wallUsername;
    }

    public String getWallFullname() {
        return wallFullname;
    }

    public void setWallFullname(String wallFullname) {
        this.wallFullname = wallFullname;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Object getTime() {
        return time;
    }

    public void setTime(Object time) {
        this.time = time;
    }

    public Object getLocation() {
        return location;
    }

    public void setLocation(Object location) {
        this.location = location;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public Integer getReactionLikeCount() {
        return reactionLikeCount;
    }

    public void setReactionLikeCount(Integer reactionLikeCount) {
        this.reactionLikeCount = reactionLikeCount;
    }

    public Integer getReactionLoveCount() {
        return reactionLoveCount;
    }

    public void setReactionLoveCount(Integer reactionLoveCount) {
        this.reactionLoveCount = reactionLoveCount;
    }

    public Integer getReactionHahaCount() {
        return reactionHahaCount;
    }

    public void setReactionHahaCount(Integer reactionHahaCount) {
        this.reactionHahaCount = reactionHahaCount;
    }

    public Integer getReactionYayCount() {
        return reactionYayCount;
    }

    public void setReactionYayCount(Integer reactionYayCount) {
        this.reactionYayCount = reactionYayCount;
    }

    public Integer getReactionWowCount() {
        return reactionWowCount;
    }

    public void setReactionWowCount(Integer reactionWowCount) {
        this.reactionWowCount = reactionWowCount;
    }

    public Integer getReactionSadCount() {
        return reactionSadCount;
    }

    public void setReactionSadCount(Integer reactionSadCount) {
        this.reactionSadCount = reactionSadCount;
    }

    public Integer getReactionAngryCount() {
        return reactionAngryCount;
    }

    public void setReactionAngryCount(Integer reactionAngryCount) {
        this.reactionAngryCount = reactionAngryCount;
    }

    public Integer getReactionsTotalCount() {
        return reactionsTotalCount;
    }

    public void setReactionsTotalCount(Integer reactionsTotalCount) {
        this.reactionsTotalCount = reactionsTotalCount;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public Integer getShares() {
        return shares;
    }

    public void setShares(Integer shares) {
        this.shares = shares;
    }

    public String getFeelingAction() {
        return feelingAction;
    }

    public void setFeelingAction(String feelingAction) {
        this.feelingAction = feelingAction;
    }

    public String getFeelingValue() {
        return feelingValue;
    }

    public void setFeelingValue(String feelingValue) {
        this.feelingValue = feelingValue;
    }

    public String getFeelingIcon() {
        return feelingIcon;
    }

    public void setFeelingIcon(String feelingIcon) {
        this.feelingIcon = feelingIcon;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public Integer getPhotosNum() {
        return photosNum;
    }

    public void setPhotosNum(Integer photosNum) {
        this.photosNum = photosNum;
    }

    public String getTextPlain() {
        return textPlain;
    }

    public void setTextPlain(String textPlain) {
        this.textPlain = textPlain;
    }

    public Boolean getManagePost() {
        return managePost;
    }

    public void setManagePost(Boolean managePost) {
        this.managePost = managePost;
    }


    public class Photo {

        @SerializedName("photo_id")
        @Expose
        private Object photoId;
        @SerializedName("post_id")
        @Expose
        private Integer postId;
        @SerializedName("source")
        @Expose
        private String source;
        @SerializedName("blur")
        @Expose
        private Integer blur;
        @SerializedName("reaction_like_count")
        @Expose
        private Integer reactionLikeCount;
        @SerializedName("reaction_love_count")
        @Expose
        private Integer reactionLoveCount;
        @SerializedName("reaction_haha_count")
        @Expose
        private Integer reactionHahaCount;
        @SerializedName("reaction_yay_count")
        @Expose
        private Integer reactionYayCount;
        @SerializedName("reaction_wow_count")
        @Expose
        private Integer reactionWowCount;
        @SerializedName("reaction_sad_count")
        @Expose
        private Integer reactionSadCount;
        @SerializedName("reaction_angry_count")
        @Expose
        private Integer reactionAngryCount;
        @SerializedName("reactions_total_count")
        @Expose
        private Integer reactionsTotalCount;
        @SerializedName("comments")
        @Expose
        private Integer comments;

        public Object getPhotoId() {
            return photoId;
        }

        public void setPhotoId(Object photoId) {
            this.photoId = photoId;
        }

        public Integer getPostId() {
            return postId;
        }

        public void setPostId(Integer postId) {
            this.postId = postId;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public Integer getBlur() {
            return blur;
        }

        public void setBlur(Integer blur) {
            this.blur = blur;
        }

        public Integer getReactionLikeCount() {
            return reactionLikeCount;
        }

        public void setReactionLikeCount(Integer reactionLikeCount) {
            this.reactionLikeCount = reactionLikeCount;
        }

        public Integer getReactionLoveCount() {
            return reactionLoveCount;
        }

        public void setReactionLoveCount(Integer reactionLoveCount) {
            this.reactionLoveCount = reactionLoveCount;
        }

        public Integer getReactionHahaCount() {
            return reactionHahaCount;
        }

        public void setReactionHahaCount(Integer reactionHahaCount) {
            this.reactionHahaCount = reactionHahaCount;
        }

        public Integer getReactionYayCount() {
            return reactionYayCount;
        }

        public void setReactionYayCount(Integer reactionYayCount) {
            this.reactionYayCount = reactionYayCount;
        }

        public Integer getReactionWowCount() {
            return reactionWowCount;
        }

        public void setReactionWowCount(Integer reactionWowCount) {
            this.reactionWowCount = reactionWowCount;
        }

        public Integer getReactionSadCount() {
            return reactionSadCount;
        }

        public void setReactionSadCount(Integer reactionSadCount) {
            this.reactionSadCount = reactionSadCount;
        }

        public Integer getReactionAngryCount() {
            return reactionAngryCount;
        }

        public void setReactionAngryCount(Integer reactionAngryCount) {
            this.reactionAngryCount = reactionAngryCount;
        }

        public Integer getReactionsTotalCount() {
            return reactionsTotalCount;
        }

        public void setReactionsTotalCount(Integer reactionsTotalCount) {
            this.reactionsTotalCount = reactionsTotalCount;
        }

        public Integer getComments() {
            return comments;
        }

        public void setComments(Integer comments) {
            this.comments = comments;
        }

    }

}