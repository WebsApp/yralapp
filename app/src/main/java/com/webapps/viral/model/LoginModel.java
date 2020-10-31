package com.webapps.viral.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginModel {


    public class Datum {

        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("user_group")
        @Expose
        private String userGroup;
        @SerializedName("user_name")
        @Expose
        private String userName;
        @SerializedName("user_email")
        @Expose
        private String userEmail;
        @SerializedName("user_email_verified")
        @Expose
        private String userEmailVerified;
        @SerializedName("user_email_verification_code")
        @Expose
        private Object userEmailVerificationCode;
        @SerializedName("user_phone")
        @Expose
        private String userPhone;
        @SerializedName("user_phone_verified")
        @Expose
        private String userPhoneVerified;
        @SerializedName("user_phone_verification_code")
        @Expose
        private Object userPhoneVerificationCode;
        @SerializedName("user_password")
        @Expose
        private String userPassword;
        @SerializedName("user_two_factor_enabled")
        @Expose
        private String userTwoFactorEnabled;
        @SerializedName("user_two_factor_type")
        @Expose
        private Object userTwoFactorType;
        @SerializedName("user_two_factor_key")
        @Expose
        private Object userTwoFactorKey;
        @SerializedName("user_two_factor_gsecret")
        @Expose
        private Object userTwoFactorGsecret;
        @SerializedName("user_activated")
        @Expose
        private String userActivated;
        @SerializedName("user_reseted")
        @Expose
        private String userReseted;
        @SerializedName("user_reset_key")
        @Expose
        private Object userResetKey;
        @SerializedName("user_subscribed")
        @Expose
        private String userSubscribed;
        @SerializedName("user_package")
        @Expose
        private Object userPackage;
        @SerializedName("user_subscription_date")
        @Expose
        private Object userSubscriptionDate;
        @SerializedName("user_boosted_posts")
        @Expose
        private String userBoostedPosts;
        @SerializedName("user_boosted_pages")
        @Expose
        private String userBoostedPages;
        @SerializedName("user_started")
        @Expose
        private String userStarted;
        @SerializedName("user_verified")
        @Expose
        private String userVerified;
        @SerializedName("user_banned")
        @Expose
        private String userBanned;
        @SerializedName("user_live_requests_counter")
        @Expose
        private String userLiveRequestsCounter;
        @SerializedName("user_live_requests_lastid")
        @Expose
        private String userLiveRequestsLastid;
        @SerializedName("user_live_messages_counter")
        @Expose
        private String userLiveMessagesCounter;
        @SerializedName("user_live_messages_lastid")
        @Expose
        private String userLiveMessagesLastid;
        @SerializedName("user_live_notifications_counter")
        @Expose
        private String userLiveNotificationsCounter;
        @SerializedName("user_live_notifications_lastid")
        @Expose
        private String userLiveNotificationsLastid;
        @SerializedName("user_latitude")
        @Expose
        private String userLatitude;
        @SerializedName("user_longitude")
        @Expose
        private String userLongitude;
        @SerializedName("user_location_updated")
        @Expose
        private Object userLocationUpdated;
        @SerializedName("user_firstname")
        @Expose
        private String userFirstname;
        @SerializedName("user_lastname")
        @Expose
        private String userLastname;
        @SerializedName("user_gender")
        @Expose
        private String userGender;
        @SerializedName("user_picture")
        @Expose
        private Object userPicture;
        @SerializedName("user_picture_id")
        @Expose
        private Object userPictureId;
        @SerializedName("user_cover")
        @Expose
        private Object userCover;
        @SerializedName("user_cover_id")
        @Expose
        private Object userCoverId;
        @SerializedName("user_cover_position")
        @Expose
        private Object userCoverPosition;
        @SerializedName("user_album_pictures")
        @Expose
        private Object userAlbumPictures;
        @SerializedName("user_album_covers")
        @Expose
        private Object userAlbumCovers;
        @SerializedName("user_album_timeline")
        @Expose
        private Object userAlbumTimeline;
        @SerializedName("user_pinned_post")
        @Expose
        private Object userPinnedPost;
        @SerializedName("user_registered")
        @Expose
        private String userRegistered;
        @SerializedName("user_last_seen")
        @Expose
        private String userLastSeen;
        @SerializedName("user_first_failed_login")
        @Expose
        private String userFirstFailedLogin;
        @SerializedName("user_failed_login_ip")
        @Expose
        private String userFailedLoginIp;
        @SerializedName("user_failed_login_count")
        @Expose
        private String userFailedLoginCount;
        @SerializedName("user_country")
        @Expose
        private Object userCountry;
        @SerializedName("user_birthdate")
        @Expose
        private Object userBirthdate;
        @SerializedName("user_relationship")
        @Expose
        private Object userRelationship;
        @SerializedName("user_biography")
        @Expose
        private String userBiography;
        @SerializedName("user_website")
        @Expose
        private Object userWebsite;
        @SerializedName("user_work_title")
        @Expose
        private String userWorkTitle;
        @SerializedName("user_work_place")
        @Expose
        private String userWorkPlace;
        @SerializedName("user_work_url")
        @Expose
        private Object userWorkUrl;
        @SerializedName("user_current_city")
        @Expose
        private String userCurrentCity;
        @SerializedName("user_hometown")
        @Expose
        private String userHometown;
        @SerializedName("user_edu_major")
        @Expose
        private String userEduMajor;
        @SerializedName("user_edu_school")
        @Expose
        private String userEduSchool;
        @SerializedName("user_edu_class")
        @Expose
        private String userEduClass;
        @SerializedName("user_social_facebook")
        @Expose
        private String userSocialFacebook;
        @SerializedName("user_social_twitter")
        @Expose
        private String userSocialTwitter;
        @SerializedName("user_social_youtube")
        @Expose
        private String userSocialYoutube;
        @SerializedName("user_social_instagram")
        @Expose
        private String userSocialInstagram;
        @SerializedName("user_social_linkedin")
        @Expose
        private String userSocialLinkedin;
        @SerializedName("user_social_vkontakte")
        @Expose
        private String userSocialVkontakte;
        @SerializedName("user_profile_background")
        @Expose
        private Object userProfileBackground;
        @SerializedName("user_chat_enabled")
        @Expose
        private String userChatEnabled;
        @SerializedName("user_privacy_newsletter")
        @Expose
        private String userPrivacyNewsletter;
        @SerializedName("user_privacy_poke")
        @Expose
        private String userPrivacyPoke;
        @SerializedName("user_privacy_gifts")
        @Expose
        private String userPrivacyGifts;
        @SerializedName("user_privacy_wall")
        @Expose
        private String userPrivacyWall;
        @SerializedName("user_privacy_birthdate")
        @Expose
        private String userPrivacyBirthdate;
        @SerializedName("user_privacy_relationship")
        @Expose
        private String userPrivacyRelationship;
        @SerializedName("user_privacy_basic")
        @Expose
        private String userPrivacyBasic;
        @SerializedName("user_privacy_work")
        @Expose
        private String userPrivacyWork;
        @SerializedName("user_privacy_location")
        @Expose
        private String userPrivacyLocation;
        @SerializedName("user_privacy_education")
        @Expose
        private String userPrivacyEducation;
        @SerializedName("user_privacy_other")
        @Expose
        private String userPrivacyOther;
        @SerializedName("user_privacy_friends")
        @Expose
        private String userPrivacyFriends;
        @SerializedName("user_privacy_photos")
        @Expose
        private String userPrivacyPhotos;
        @SerializedName("user_privacy_pages")
        @Expose
        private String userPrivacyPages;
        @SerializedName("user_privacy_groups")
        @Expose
        private String userPrivacyGroups;
        @SerializedName("user_privacy_events")
        @Expose
        private String userPrivacyEvents;
        @SerializedName("email_post_likes")
        @Expose
        private String emailPostLikes;
        @SerializedName("email_post_comments")
        @Expose
        private String emailPostComments;
        @SerializedName("email_post_shares")
        @Expose
        private String emailPostShares;
        @SerializedName("email_wall_posts")
        @Expose
        private String emailWallPosts;
        @SerializedName("email_mentions")
        @Expose
        private String emailMentions;
        @SerializedName("email_profile_visits")
        @Expose
        private String emailProfileVisits;
        @SerializedName("email_friend_requests")
        @Expose
        private String emailFriendRequests;
        @SerializedName("facebook_connected")
        @Expose
        private String facebookConnected;
        @SerializedName("facebook_id")
        @Expose
        private Object facebookId;
        @SerializedName("google_connected")
        @Expose
        private String googleConnected;
        @SerializedName("google_id")
        @Expose
        private Object googleId;
        @SerializedName("twitter_connected")
        @Expose
        private String twitterConnected;
        @SerializedName("twitter_id")
        @Expose
        private Object twitterId;
        @SerializedName("instagram_connected")
        @Expose
        private String instagramConnected;
        @SerializedName("instagram_id")
        @Expose
        private Object instagramId;
        @SerializedName("linkedin_connected")
        @Expose
        private String linkedinConnected;
        @SerializedName("linkedin_id")
        @Expose
        private Object linkedinId;
        @SerializedName("vkontakte_connected")
        @Expose
        private String vkontakteConnected;
        @SerializedName("vkontakte_id")
        @Expose
        private Object vkontakteId;
        @SerializedName("user_referrer_id")
        @Expose
        private Object userReferrerId;
        @SerializedName("user_affiliate_balance")
        @Expose
        private String userAffiliateBalance;
        @SerializedName("user_wallet_balance")
        @Expose
        private String userWalletBalance;
        @SerializedName("user_points")
        @Expose
        private String userPoints;
        @SerializedName("chat_sound")
        @Expose
        private String chatSound;
        @SerializedName("notifications_sound")
        @Expose
        private String notificationsSound;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserGroup() {
            return userGroup;
        }

        public void setUserGroup(String userGroup) {
            this.userGroup = userGroup;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserEmail() {
            return userEmail;
        }

        public void setUserEmail(String userEmail) {
            this.userEmail = userEmail;
        }

        public String getUserEmailVerified() {
            return userEmailVerified;
        }

        public void setUserEmailVerified(String userEmailVerified) {
            this.userEmailVerified = userEmailVerified;
        }

        public Object getUserEmailVerificationCode() {
            return userEmailVerificationCode;
        }

        public void setUserEmailVerificationCode(Object userEmailVerificationCode) {
            this.userEmailVerificationCode = userEmailVerificationCode;
        }

        public String getUserPhone() {
            return userPhone;
        }

        public void setUserPhone(String userPhone) {
            this.userPhone = userPhone;
        }

        public String getUserPhoneVerified() {
            return userPhoneVerified;
        }

        public void setUserPhoneVerified(String userPhoneVerified) {
            this.userPhoneVerified = userPhoneVerified;
        }

        public Object getUserPhoneVerificationCode() {
            return userPhoneVerificationCode;
        }

        public void setUserPhoneVerificationCode(Object userPhoneVerificationCode) {
            this.userPhoneVerificationCode = userPhoneVerificationCode;
        }

        public String getUserPassword() {
            return userPassword;
        }

        public void setUserPassword(String userPassword) {
            this.userPassword = userPassword;
        }

        public String getUserTwoFactorEnabled() {
            return userTwoFactorEnabled;
        }

        public void setUserTwoFactorEnabled(String userTwoFactorEnabled) {
            this.userTwoFactorEnabled = userTwoFactorEnabled;
        }

        public Object getUserTwoFactorType() {
            return userTwoFactorType;
        }

        public void setUserTwoFactorType(Object userTwoFactorType) {
            this.userTwoFactorType = userTwoFactorType;
        }

        public Object getUserTwoFactorKey() {
            return userTwoFactorKey;
        }

        public void setUserTwoFactorKey(Object userTwoFactorKey) {
            this.userTwoFactorKey = userTwoFactorKey;
        }

        public Object getUserTwoFactorGsecret() {
            return userTwoFactorGsecret;
        }

        public void setUserTwoFactorGsecret(Object userTwoFactorGsecret) {
            this.userTwoFactorGsecret = userTwoFactorGsecret;
        }

        public String getUserActivated() {
            return userActivated;
        }

        public void setUserActivated(String userActivated) {
            this.userActivated = userActivated;
        }

        public String getUserReseted() {
            return userReseted;
        }

        public void setUserReseted(String userReseted) {
            this.userReseted = userReseted;
        }

        public Object getUserResetKey() {
            return userResetKey;
        }

        public void setUserResetKey(Object userResetKey) {
            this.userResetKey = userResetKey;
        }

        public String getUserSubscribed() {
            return userSubscribed;
        }

        public void setUserSubscribed(String userSubscribed) {
            this.userSubscribed = userSubscribed;
        }

        public Object getUserPackage() {
            return userPackage;
        }

        public void setUserPackage(Object userPackage) {
            this.userPackage = userPackage;
        }

        public Object getUserSubscriptionDate() {
            return userSubscriptionDate;
        }

        public void setUserSubscriptionDate(Object userSubscriptionDate) {
            this.userSubscriptionDate = userSubscriptionDate;
        }

        public String getUserBoostedPosts() {
            return userBoostedPosts;
        }

        public void setUserBoostedPosts(String userBoostedPosts) {
            this.userBoostedPosts = userBoostedPosts;
        }

        public String getUserBoostedPages() {
            return userBoostedPages;
        }

        public void setUserBoostedPages(String userBoostedPages) {
            this.userBoostedPages = userBoostedPages;
        }

        public String getUserStarted() {
            return userStarted;
        }

        public void setUserStarted(String userStarted) {
            this.userStarted = userStarted;
        }

        public String getUserVerified() {
            return userVerified;
        }

        public void setUserVerified(String userVerified) {
            this.userVerified = userVerified;
        }

        public String getUserBanned() {
            return userBanned;
        }

        public void setUserBanned(String userBanned) {
            this.userBanned = userBanned;
        }

        public String getUserLiveRequestsCounter() {
            return userLiveRequestsCounter;
        }

        public void setUserLiveRequestsCounter(String userLiveRequestsCounter) {
            this.userLiveRequestsCounter = userLiveRequestsCounter;
        }

        public String getUserLiveRequestsLastid() {
            return userLiveRequestsLastid;
        }

        public void setUserLiveRequestsLastid(String userLiveRequestsLastid) {
            this.userLiveRequestsLastid = userLiveRequestsLastid;
        }

        public String getUserLiveMessagesCounter() {
            return userLiveMessagesCounter;
        }

        public void setUserLiveMessagesCounter(String userLiveMessagesCounter) {
            this.userLiveMessagesCounter = userLiveMessagesCounter;
        }

        public String getUserLiveMessagesLastid() {
            return userLiveMessagesLastid;
        }

        public void setUserLiveMessagesLastid(String userLiveMessagesLastid) {
            this.userLiveMessagesLastid = userLiveMessagesLastid;
        }

        public String getUserLiveNotificationsCounter() {
            return userLiveNotificationsCounter;
        }

        public void setUserLiveNotificationsCounter(String userLiveNotificationsCounter) {
            this.userLiveNotificationsCounter = userLiveNotificationsCounter;
        }

        public String getUserLiveNotificationsLastid() {
            return userLiveNotificationsLastid;
        }

        public void setUserLiveNotificationsLastid(String userLiveNotificationsLastid) {
            this.userLiveNotificationsLastid = userLiveNotificationsLastid;
        }

        public String getUserLatitude() {
            return userLatitude;
        }

        public void setUserLatitude(String userLatitude) {
            this.userLatitude = userLatitude;
        }

        public String getUserLongitude() {
            return userLongitude;
        }

        public void setUserLongitude(String userLongitude) {
            this.userLongitude = userLongitude;
        }

        public Object getUserLocationUpdated() {
            return userLocationUpdated;
        }

        public void setUserLocationUpdated(Object userLocationUpdated) {
            this.userLocationUpdated = userLocationUpdated;
        }

        public String getUserFirstname() {
            return userFirstname;
        }

        public void setUserFirstname(String userFirstname) {
            this.userFirstname = userFirstname;
        }

        public String getUserLastname() {
            return userLastname;
        }

        public void setUserLastname(String userLastname) {
            this.userLastname = userLastname;
        }

        public String getUserGender() {
            return userGender;
        }

        public void setUserGender(String userGender) {
            this.userGender = userGender;
        }

        public Object getUserPicture() {
            return userPicture;
        }

        public void setUserPicture(Object userPicture) {
            this.userPicture = userPicture;
        }

        public Object getUserPictureId() {
            return userPictureId;
        }

        public void setUserPictureId(Object userPictureId) {
            this.userPictureId = userPictureId;
        }

        public Object getUserCover() {
            return userCover;
        }

        public void setUserCover(Object userCover) {
            this.userCover = userCover;
        }

        public Object getUserCoverId() {
            return userCoverId;
        }

        public void setUserCoverId(Object userCoverId) {
            this.userCoverId = userCoverId;
        }

        public Object getUserCoverPosition() {
            return userCoverPosition;
        }

        public void setUserCoverPosition(Object userCoverPosition) {
            this.userCoverPosition = userCoverPosition;
        }

        public Object getUserAlbumPictures() {
            return userAlbumPictures;
        }

        public void setUserAlbumPictures(Object userAlbumPictures) {
            this.userAlbumPictures = userAlbumPictures;
        }

        public Object getUserAlbumCovers() {
            return userAlbumCovers;
        }

        public void setUserAlbumCovers(Object userAlbumCovers) {
            this.userAlbumCovers = userAlbumCovers;
        }

        public Object getUserAlbumTimeline() {
            return userAlbumTimeline;
        }

        public void setUserAlbumTimeline(Object userAlbumTimeline) {
            this.userAlbumTimeline = userAlbumTimeline;
        }

        public Object getUserPinnedPost() {
            return userPinnedPost;
        }

        public void setUserPinnedPost(Object userPinnedPost) {
            this.userPinnedPost = userPinnedPost;
        }

        public String getUserRegistered() {
            return userRegistered;
        }

        public void setUserRegistered(String userRegistered) {
            this.userRegistered = userRegistered;
        }

        public String getUserLastSeen() {
            return userLastSeen;
        }

        public void setUserLastSeen(String userLastSeen) {
            this.userLastSeen = userLastSeen;
        }

        public String getUserFirstFailedLogin() {
            return userFirstFailedLogin;
        }

        public void setUserFirstFailedLogin(String userFirstFailedLogin) {
            this.userFirstFailedLogin = userFirstFailedLogin;
        }

        public String getUserFailedLoginIp() {
            return userFailedLoginIp;
        }

        public void setUserFailedLoginIp(String userFailedLoginIp) {
            this.userFailedLoginIp = userFailedLoginIp;
        }

        public String getUserFailedLoginCount() {
            return userFailedLoginCount;
        }

        public void setUserFailedLoginCount(String userFailedLoginCount) {
            this.userFailedLoginCount = userFailedLoginCount;
        }

        public Object getUserCountry() {
            return userCountry;
        }

        public void setUserCountry(Object userCountry) {
            this.userCountry = userCountry;
        }

        public Object getUserBirthdate() {
            return userBirthdate;
        }

        public void setUserBirthdate(Object userBirthdate) {
            this.userBirthdate = userBirthdate;
        }

        public Object getUserRelationship() {
            return userRelationship;
        }

        public void setUserRelationship(Object userRelationship) {
            this.userRelationship = userRelationship;
        }

        public String getUserBiography() {
            return userBiography;
        }

        public void setUserBiography(String userBiography) {
            this.userBiography = userBiography;
        }

        public Object getUserWebsite() {
            return userWebsite;
        }

        public void setUserWebsite(Object userWebsite) {
            this.userWebsite = userWebsite;
        }

        public String getUserWorkTitle() {
            return userWorkTitle;
        }

        public void setUserWorkTitle(String userWorkTitle) {
            this.userWorkTitle = userWorkTitle;
        }

        public String getUserWorkPlace() {
            return userWorkPlace;
        }

        public void setUserWorkPlace(String userWorkPlace) {
            this.userWorkPlace = userWorkPlace;
        }

        public Object getUserWorkUrl() {
            return userWorkUrl;
        }

        public void setUserWorkUrl(Object userWorkUrl) {
            this.userWorkUrl = userWorkUrl;
        }

        public String getUserCurrentCity() {
            return userCurrentCity;
        }

        public void setUserCurrentCity(String userCurrentCity) {
            this.userCurrentCity = userCurrentCity;
        }

        public String getUserHometown() {
            return userHometown;
        }

        public void setUserHometown(String userHometown) {
            this.userHometown = userHometown;
        }

        public String getUserEduMajor() {
            return userEduMajor;
        }

        public void setUserEduMajor(String userEduMajor) {
            this.userEduMajor = userEduMajor;
        }

        public String getUserEduSchool() {
            return userEduSchool;
        }

        public void setUserEduSchool(String userEduSchool) {
            this.userEduSchool = userEduSchool;
        }

        public String getUserEduClass() {
            return userEduClass;
        }

        public void setUserEduClass(String userEduClass) {
            this.userEduClass = userEduClass;
        }

        public String getUserSocialFacebook() {
            return userSocialFacebook;
        }

        public void setUserSocialFacebook(String userSocialFacebook) {
            this.userSocialFacebook = userSocialFacebook;
        }

        public String getUserSocialTwitter() {
            return userSocialTwitter;
        }

        public void setUserSocialTwitter(String userSocialTwitter) {
            this.userSocialTwitter = userSocialTwitter;
        }

        public String getUserSocialYoutube() {
            return userSocialYoutube;
        }

        public void setUserSocialYoutube(String userSocialYoutube) {
            this.userSocialYoutube = userSocialYoutube;
        }

        public String getUserSocialInstagram() {
            return userSocialInstagram;
        }

        public void setUserSocialInstagram(String userSocialInstagram) {
            this.userSocialInstagram = userSocialInstagram;
        }

        public String getUserSocialLinkedin() {
            return userSocialLinkedin;
        }

        public void setUserSocialLinkedin(String userSocialLinkedin) {
            this.userSocialLinkedin = userSocialLinkedin;
        }

        public String getUserSocialVkontakte() {
            return userSocialVkontakte;
        }

        public void setUserSocialVkontakte(String userSocialVkontakte) {
            this.userSocialVkontakte = userSocialVkontakte;
        }

        public Object getUserProfileBackground() {
            return userProfileBackground;
        }

        public void setUserProfileBackground(Object userProfileBackground) {
            this.userProfileBackground = userProfileBackground;
        }

        public String getUserChatEnabled() {
            return userChatEnabled;
        }

        public void setUserChatEnabled(String userChatEnabled) {
            this.userChatEnabled = userChatEnabled;
        }

        public String getUserPrivacyNewsletter() {
            return userPrivacyNewsletter;
        }

        public void setUserPrivacyNewsletter(String userPrivacyNewsletter) {
            this.userPrivacyNewsletter = userPrivacyNewsletter;
        }

        public String getUserPrivacyPoke() {
            return userPrivacyPoke;
        }

        public void setUserPrivacyPoke(String userPrivacyPoke) {
            this.userPrivacyPoke = userPrivacyPoke;
        }

        public String getUserPrivacyGifts() {
            return userPrivacyGifts;
        }

        public void setUserPrivacyGifts(String userPrivacyGifts) {
            this.userPrivacyGifts = userPrivacyGifts;
        }

        public String getUserPrivacyWall() {
            return userPrivacyWall;
        }

        public void setUserPrivacyWall(String userPrivacyWall) {
            this.userPrivacyWall = userPrivacyWall;
        }

        public String getUserPrivacyBirthdate() {
            return userPrivacyBirthdate;
        }

        public void setUserPrivacyBirthdate(String userPrivacyBirthdate) {
            this.userPrivacyBirthdate = userPrivacyBirthdate;
        }

        public String getUserPrivacyRelationship() {
            return userPrivacyRelationship;
        }

        public void setUserPrivacyRelationship(String userPrivacyRelationship) {
            this.userPrivacyRelationship = userPrivacyRelationship;
        }

        public String getUserPrivacyBasic() {
            return userPrivacyBasic;
        }

        public void setUserPrivacyBasic(String userPrivacyBasic) {
            this.userPrivacyBasic = userPrivacyBasic;
        }

        public String getUserPrivacyWork() {
            return userPrivacyWork;
        }

        public void setUserPrivacyWork(String userPrivacyWork) {
            this.userPrivacyWork = userPrivacyWork;
        }

        public String getUserPrivacyLocation() {
            return userPrivacyLocation;
        }

        public void setUserPrivacyLocation(String userPrivacyLocation) {
            this.userPrivacyLocation = userPrivacyLocation;
        }

        public String getUserPrivacyEducation() {
            return userPrivacyEducation;
        }

        public void setUserPrivacyEducation(String userPrivacyEducation) {
            this.userPrivacyEducation = userPrivacyEducation;
        }

        public String getUserPrivacyOther() {
            return userPrivacyOther;
        }

        public void setUserPrivacyOther(String userPrivacyOther) {
            this.userPrivacyOther = userPrivacyOther;
        }

        public String getUserPrivacyFriends() {
            return userPrivacyFriends;
        }

        public void setUserPrivacyFriends(String userPrivacyFriends) {
            this.userPrivacyFriends = userPrivacyFriends;
        }

        public String getUserPrivacyPhotos() {
            return userPrivacyPhotos;
        }

        public void setUserPrivacyPhotos(String userPrivacyPhotos) {
            this.userPrivacyPhotos = userPrivacyPhotos;
        }

        public String getUserPrivacyPages() {
            return userPrivacyPages;
        }

        public void setUserPrivacyPages(String userPrivacyPages) {
            this.userPrivacyPages = userPrivacyPages;
        }

        public String getUserPrivacyGroups() {
            return userPrivacyGroups;
        }

        public void setUserPrivacyGroups(String userPrivacyGroups) {
            this.userPrivacyGroups = userPrivacyGroups;
        }

        public String getUserPrivacyEvents() {
            return userPrivacyEvents;
        }

        public void setUserPrivacyEvents(String userPrivacyEvents) {
            this.userPrivacyEvents = userPrivacyEvents;
        }

        public String getEmailPostLikes() {
            return emailPostLikes;
        }

        public void setEmailPostLikes(String emailPostLikes) {
            this.emailPostLikes = emailPostLikes;
        }

        public String getEmailPostComments() {
            return emailPostComments;
        }

        public void setEmailPostComments(String emailPostComments) {
            this.emailPostComments = emailPostComments;
        }

        public String getEmailPostShares() {
            return emailPostShares;
        }

        public void setEmailPostShares(String emailPostShares) {
            this.emailPostShares = emailPostShares;
        }

        public String getEmailWallPosts() {
            return emailWallPosts;
        }

        public void setEmailWallPosts(String emailWallPosts) {
            this.emailWallPosts = emailWallPosts;
        }

        public String getEmailMentions() {
            return emailMentions;
        }

        public void setEmailMentions(String emailMentions) {
            this.emailMentions = emailMentions;
        }

        public String getEmailProfileVisits() {
            return emailProfileVisits;
        }

        public void setEmailProfileVisits(String emailProfileVisits) {
            this.emailProfileVisits = emailProfileVisits;
        }

        public String getEmailFriendRequests() {
            return emailFriendRequests;
        }

        public void setEmailFriendRequests(String emailFriendRequests) {
            this.emailFriendRequests = emailFriendRequests;
        }

        public String getFacebookConnected() {
            return facebookConnected;
        }

        public void setFacebookConnected(String facebookConnected) {
            this.facebookConnected = facebookConnected;
        }

        public Object getFacebookId() {
            return facebookId;
        }

        public void setFacebookId(Object facebookId) {
            this.facebookId = facebookId;
        }

        public String getGoogleConnected() {
            return googleConnected;
        }

        public void setGoogleConnected(String googleConnected) {
            this.googleConnected = googleConnected;
        }

        public Object getGoogleId() {
            return googleId;
        }

        public void setGoogleId(Object googleId) {
            this.googleId = googleId;
        }

        public String getTwitterConnected() {
            return twitterConnected;
        }

        public void setTwitterConnected(String twitterConnected) {
            this.twitterConnected = twitterConnected;
        }

        public Object getTwitterId() {
            return twitterId;
        }

        public void setTwitterId(Object twitterId) {
            this.twitterId = twitterId;
        }

        public String getInstagramConnected() {
            return instagramConnected;
        }

        public void setInstagramConnected(String instagramConnected) {
            this.instagramConnected = instagramConnected;
        }

        public Object getInstagramId() {
            return instagramId;
        }

        public void setInstagramId(Object instagramId) {
            this.instagramId = instagramId;
        }

        public String getLinkedinConnected() {
            return linkedinConnected;
        }

        public void setLinkedinConnected(String linkedinConnected) {
            this.linkedinConnected = linkedinConnected;
        }

        public Object getLinkedinId() {
            return linkedinId;
        }

        public void setLinkedinId(Object linkedinId) {
            this.linkedinId = linkedinId;
        }

        public String getVkontakteConnected() {
            return vkontakteConnected;
        }

        public void setVkontakteConnected(String vkontakteConnected) {
            this.vkontakteConnected = vkontakteConnected;
        }

        public Object getVkontakteId() {
            return vkontakteId;
        }

        public void setVkontakteId(Object vkontakteId) {
            this.vkontakteId = vkontakteId;
        }

        public Object getUserReferrerId() {
            return userReferrerId;
        }

        public void setUserReferrerId(Object userReferrerId) {
            this.userReferrerId = userReferrerId;
        }

        public String getUserAffiliateBalance() {
            return userAffiliateBalance;
        }

        public void setUserAffiliateBalance(String userAffiliateBalance) {
            this.userAffiliateBalance = userAffiliateBalance;
        }

        public String getUserWalletBalance() {
            return userWalletBalance;
        }

        public void setUserWalletBalance(String userWalletBalance) {
            this.userWalletBalance = userWalletBalance;
        }

        public String getUserPoints() {
            return userPoints;
        }

        public void setUserPoints(String userPoints) {
            this.userPoints = userPoints;
        }

        public String getChatSound() {
            return chatSound;
        }

        public void setChatSound(String chatSound) {
            this.chatSound = chatSound;
        }

        public String getNotificationsSound() {
            return notificationsSound;
        }

        public void setNotificationsSound(String notificationsSound) {
            this.notificationsSound = notificationsSound;
        }

    }

    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("Likes")
    @Expose
    private String likes;
    @SerializedName("post")
    @Expose
    private String post;
    @SerializedName("Follow")
    @Expose
    private String follow;
    @SerializedName("Followers")
    @Expose
    private String followers;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("Fstatus")
    @Expose
    private Integer fstatus;
    @SerializedName("msg")
    @Expose
    private String msg;

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getFollow() {
        return follow;
    }

    public void setFollow(String follow) {
        this.follow = follow;
    }

    public String getFollowers() {
        return followers;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    public Integer getFstatus() {
        return fstatus;
    }

    public void setFstatus(Integer fstatus) {
        this.fstatus = fstatus;
    }
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    }

