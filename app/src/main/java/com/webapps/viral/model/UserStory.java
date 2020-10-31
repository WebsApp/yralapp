package com.webapps.viral.model;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
public  class UserStory implements Parcelable {


        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("photo")
        @Expose
        private Object photo;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("lastUpdated")
        @Expose
        private Integer lastUpdated;
        public final static Parcelable.Creator<UserStory> CREATOR = new Creator<UserStory>() {


            @SuppressWarnings({
                    "unchecked"
            })
            public UserStory createFromParcel(Parcel in) {
                return new UserStory(in);
            }

            public UserStory[] newArray(int size) {
                return (new UserStory[size]);
            }

        };

        protected UserStory(Parcel in) {
            this.id = ((String) in.readValue((String.class.getClassLoader())));
            this.photo = ((Object) in.readValue((Object.class.getClassLoader())));
            this.name = ((String) in.readValue((String.class.getClassLoader())));
            this.lastUpdated = ((Integer) in.readValue((Integer.class.getClassLoader())));
        }

        public UserStory() {
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Object getPhoto() {
            return photo;
        }

        public void setPhoto(Object photo) {
            this.photo = photo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getLastUpdated() {
            return lastUpdated;
        }

        public void setLastUpdated(Integer lastUpdated) {
            this.lastUpdated = lastUpdated;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(id);
            dest.writeValue(photo);
            dest.writeValue(name);
            dest.writeValue(lastUpdated);
        }

        public int describeContents() {
            return 0;
        }

    }
