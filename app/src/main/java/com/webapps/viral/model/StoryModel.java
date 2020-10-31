package com.webapps.viral.model;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class StoryModel implements Parcelable {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("src")
        @Expose
        private String src;
        @SerializedName("link")
        @Expose
        private String link;
        @SerializedName("linkText")
        @Expose
        private String linkText;
        @SerializedName("time")
        @Expose
        private Integer time;
        @SerializedName("story_view")
        @Expose
        private Integer story_view;
        public final static Parcelable.Creator<StoryModel> CREATOR = new Creator<StoryModel>() {


            @SuppressWarnings({
                    "unchecked"
            })
            public StoryModel createFromParcel(Parcel in) {
                return new StoryModel(in);
            }

            public StoryModel[] newArray(int size) {
                return (new StoryModel[size]);
            }

        }
                ;

        protected StoryModel(Parcel in) {
            this.id = ((String) in.readValue((String.class.getClassLoader())));
            this.type = ((String) in.readValue((String.class.getClassLoader())));
            this.src = ((String) in.readValue((String.class.getClassLoader())));
            this.link = ((String) in.readValue((String.class.getClassLoader())));
            this.linkText = ((String) in.readValue((String.class.getClassLoader())));
            this.time = ((Integer) in.readValue((Integer.class.getClassLoader())));
            this.story_view = ((Integer) in.readValue((Integer.class.getClassLoader())));

        }

        public StoryModel() {
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getLinkText() {
            return linkText;
        }

        public void setLinkText(String linkText) {
            this.linkText = linkText;
        }

        public Integer getTime() {
            return time;
        }

        public void setTime(Integer time) {
            this.time = time;
        }
    public Integer getStory_view() {
        return story_view;
    }

    public void setStory_view(Integer story_view) {
        this.story_view = story_view;
    }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(id);
            dest.writeValue(type);
            dest.writeValue(src);
            dest.writeValue(link);
            dest.writeValue(linkText);
            dest.writeValue(time);
            dest.writeValue(story_view);
        }

        public int describeContents() {
            return 0;
        }

    }


