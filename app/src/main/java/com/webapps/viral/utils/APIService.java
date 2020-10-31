package com.webapps.viral.utils;


import com.webapps.viral.model.AddPost;
import com.webapps.viral.model.BannerModel;
import com.webapps.viral.model.CategoryModel;
import com.webapps.viral.model.CommentModel;
import com.webapps.viral.model.Follower;
import com.webapps.viral.model.InterestModel;
import com.webapps.viral.model.LanguageModel;
import com.webapps.viral.model.LoginModel;
import com.webapps.viral.model.NotificationModel;
import com.webapps.viral.model.OTPModel;
import com.webapps.viral.model.PhotoModel;
import com.webapps.viral.model.PostModel;
import com.webapps.viral.model.RegisterModel;
import com.webapps.viral.model.StoryModel;
import com.webapps.viral.model.TopUserModel;
import com.webapps.viral.model.UserModel;
import com.webapps.viral.model.UserStory;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
public interface APIService {


    @POST("API/api.php?login")
    Call<LoginModel> userLogin(
            @Query("email") String email,
            @Query("password") String password


    );

    @GET("API/api.php?register")
    Call<RegisterModel> Register(@Query("name") String  name,@Query("email") String email,
                                 @Query("phone") String phone,@Query("password") String password);
    @GET("API/api.php?fbregister")
    Call<RegisterModel> FBRegister(@Query("name") String  name,@Query("fname") String  fname,@Query("lname") String  lname,@Query("email") String email,
                                 @Query("fb_id") String fb_id);
    @GET("API/api.php?twregister")
    Call<RegisterModel> TWRegister(@Query("name") String  name,@Query("fname") String  fname,@Query("lname") String  lname,@Query("email") String email,
                                   @Query("t_id") String t_id);
    @GET("API/api.php?twlogin")
    Call<LoginModel> TWLogin(@Query("name") String  name,@Query("fname") String  fname,@Query("lname") String  lname,@Query("email") String email,
                                   @Query("t_id") String t_id);
    @GET("API/api.php?googleregister")
    Call<RegisterModel> GoogleRegister(@Query("name") String  name,@Query("phone") String  fname,@Query("email") String email,
                                   @Query("g_id") String g_id);

    @GET("API/api.php?googlelogin")
    Call<LoginModel> GoogleLogin(@Query("name") String  name,@Query("phone") String  fname,@Query("email") String email,
                                       @Query("g_id") String g_id);
    @GET("API/api.php?fblogin")
    Call<LoginModel> FBLogin(@Query("name") String  name,@Query("fname") String  fname,@Query("lname") String  lname,@Query("email") String email,
                                   @Query("fb_id") String fb_id);
    @GET("API/api.php?addcomment")
    Call<RegisterModel> AddComment(@Query("id") String  id,@Query("post_id") String post_id,
                                   @Query("time") String time,@Query("text") String text,
                                 @Query("node_type") String node_type,@Query("user_type") String user_type);
    @GET("API/api.php?update_user_details")
    Call<RegisterModel> UpdateUser(@Query("date") String  date,@Query("bio") String bio,
                                   @Query("city") String city,@Query("interest") String interest,
                                   @Query("language") String language,@Query("id") String id);
    @GET("API/api.php?addlike")
    Call<RegisterModel> AddLike(@Query("id") String  id,@Query("user_id") String  userid,@Query("time") String  time);
    @GET("API/api.php?save_post")
    Call<RegisterModel> SavePost(@Query("id") String  id,@Query("post_id") String  postid);
    @GET("API/api.php?addclaim")
    Call<RegisterModel> Claim(@Query("id") String  id,@Query("post_id") String  postid,@Query("mobile") String  mobile
            ,@Query("email") String  email,@Query("address") String  address);
    @GET("API/api.php?postdeleted")
    Call<RegisterModel> PostDelete(@Query("id") String user_id,@Query("post_id") String  postid);

    @GET("API/api.php?addstoryview")
    Call<RegisterModel> AddStoryview(@Query("id") String  id);

    @GET("API/api.php?share_post")
    Call<RegisterModel> sharepost(@Query("user_id") String  id,@Query("origin_id") String  post_id,@Query("time") String  time);

    @GET("API/api.php?get_comment")
    Call<ArrayList<CommentModel>> GetComment(@Query("id") String  id);

    @GET("API/api.php?notification")
    Call<ArrayList<NotificationModel>> Getnotification(@Query("id") String  id);

    @GET("API/api.php?get_user")
    Call<ArrayList<TopUserModel>> GetUser();
    @GET("API/api.php?get_follower")
    Call<ArrayList<Follower>> GetFollower(@Query("id") String  id);
    @GET("API/api.php?get_following")
    Call<ArrayList<Follower>> GetFollowing(@Query("id") String  id);
    @GET("API/api.php?get_liker")
    Call<ArrayList<Follower>> GetLiker(@Query("id") String  id);
    @GET("API/api.php?get_photo")
    Call<ArrayList<PhotoModel>> GetPhoto();
    @GET("API/api.php?get_search_photo")
    Call<ArrayList<PhotoModel>> GetPhoto(@Query("s") String  s);
    @GET("API/api.php?get_photo_user")
    Call<ArrayList<PhotoModel>> GetPhoto_user(@Query("id") String  id);
    @GET("API/api.php?get_video")
    Call<ArrayList<PhotoModel>> GetVideo();
    @GET("API/api.php?get_search_video")
    Call<ArrayList<PhotoModel>> GetVideo(@Query("s") String  id);
    @GET("API/api.php?get_video_user")
    Call<ArrayList<PhotoModel>> Get_Video_user(@Query("id") String  id);
    @POST("/index.php/api/AddUser")
    Call<RegisterModel> AddUser(@Body RequestBody requestBody);
    @POST("/index.php/api/Update_profile")
    Call<RegisterModel> Update_profile(@Body RequestBody requestBody);
    @POST("/index.php/api/add_bank")
    Call<RegisterModel> AddBank(@Body RequestBody requestBody);
    @POST("/index.php/api/change_password")
    Call<RegisterModel> ChangePassword(@Body RequestBody requestBody);

    @Multipart
    @POST("/index.php/api/update_kyc")
    Call<RegisterModel> KYCUpdate(@Part("user_id") RequestBody user_id,
                                  @Part("aadhar") RequestBody aadhar,
                                  @Part("pancard") RequestBody pancard,
                                  @Part MultipartBody.Part file, @Part MultipartBody.Part file2);
    @GET("API/api.php?phone_check")
    Call<RegisterModel> PhoneCheck(@Query("phone") String phone);
    @GET("API/api.php?email_check")
    Call<RegisterModel> EmailCheck(@Query("email") String email);

    @GET("API/api.php?get_logo")
    Call<BannerModel> BANNER_MODEL();
    @GET("API/api.php?get_category")
    Call<CategoryModel> GetCategory();
    @GET("API/api.php?get_challenge_category")
    Call<CategoryModel> GetCCategory();
    @GET("API/api.php?get_save_post")
    Call<ArrayList<PostModel>> GetSavePost(@Query("id") String id);
    @GET("API/api.php?get_post")
    Call<ArrayList<PostModel>> GetPost(@Query("user_id") String id);
    @GET("API/api.php?get_post_id")
    Call<ArrayList<PostModel>> GetPost_id(@Query("id") String category);
    @GET("API/api.php?get_cpost")
    Call<ArrayList<PostModel>> GetCPost(@Query("c") String category,@Query("user_id") String id );
    @GET("API/api.php?get_challenge")
    Call<ArrayList<PostModel>> GetChPost(@Query("c") String category,@Query("id") String id );
    @GET("API/api.php?trending_post")
    Call<ArrayList<PostModel>> TrendingPost(@Query("c") String category,@Query("user_id") String id);
    @GET("API/api.php?get_my_post")
    Call<ArrayList<PostModel>> GetMyPost(@Query("id") String  id,@Query("user_id") String user_id);
    @GET("API/api.php?get_my_all_post")
    Call<ArrayList<PostModel>> GetMyAllPost(@Query("id") String  id,@Query("user_id") String user_id);
    @GET("API/api.php?get_sug_user")
    Call<ArrayList<UserModel>> Get_Sug_User();
    @GET("API/api.php?forget_password")
    Call<RegisterModel> ForgetPassword(@Query("phone") String phone);
    @GET("API/api.php?otp_login")
    Call<OTPModel> get_otp_user(@Query("phone") String phone);
    @GET("API/api.php?email_update")
    Call<OTPModel> EmailUpdate(@Query("id") String id,@Query("email") String email,@Query("new_email") String new_email);

    @GET("API/api.php?email_change_otp")
    Call<OTPModel> email_change_otp(@Query("id") String id,@Query("email") String email);
    @GET("API/api.php?phone_update")
    Call<OTPModel> PhoneUpdate(@Query("id") String id,@Query("email") String email,@Query("new_email") String new_email);

    @GET("API/api.php?phone_change_otp")
    Call<OTPModel> phone_change_otp(@Query("id") String id,@Query("email") String email);
    //delete
    @GET("API/api.php?accountdeleted")
    Call<RegisterModel> AccountDelete(@Query("id") String id);
    @GET("API/api.php?update_password")
    Call<RegisterModel> UpdatePassword(@Query("password") String  password,
                                       @Query("id") String id);
    @GET("API/api.php?get_story")
    Call<ArrayList<UserStory>> get_story(@Query("id") String id);
    @GET("API/api.php?get_story_user")
    Call<ArrayList<StoryModel>> get_story_user(@Query("id") String id);

    @GET("API/api.php?get_interest")
    Call<InterestModel> get_interest();

    @GET("API/api.php?get_language")
    Call<LanguageModel> get_language();
    //profile

    @GET("API/api.php?profile")
    Call<LoginModel> GetProfile(@Query("id") String id,@Query("user_id") String user_id);
    @GET("API/api.php?addfollow")
    Call<RegisterModel> AddFollow(@Query("id") String id,@Query("user_id") String user_id);

    @Multipart
    @POST("/index.php/api/Package_purchase")
    Call<RegisterModel> Purchasepackage(@Part("user_id") RequestBody user_id,
                                        @Part("package_id") RequestBody pack,
                                        @Part("type") RequestBody type,
                                        @Part("amount") RequestBody amount,
                                        @Part MultipartBody.Part file);
    @Multipart
    @POST("/index.php/api/update_profile_pic")
    Call<RegisterModel> uploadprofile(@Part("user_id") RequestBody requestBody, @Part MultipartBody.Part file);
    @Multipart
    @POST("API/api.php?post_story")
    Call<RegisterModel> AddStory(@Part("user_id") RequestBody userid,@Part("type")RequestBody type,
                            @Part MultipartBody.Part file);
    @Multipart
    @POST("API/api.php?addphoto")
    Call<AddPost> AddPhoto(@Part("origin_id") RequestBody origin_id,@Part("expired_on") RequestBody expired_on,
                           @Part("user_id") RequestBody userid,
                          @Part("user_picture") RequestBody picture,
                          @Part("user_name") RequestBody name,
                          @Part("user_firstname") RequestBody user_firstname,
                          @Part("user_lastname") RequestBody user_lastname,
                          @Part("user_verified") RequestBody user_verified,
                          @Part("category") RequestBody category,
                          @Part("message") RequestBody message,
                          @Part("privacy") RequestBody privacy,
                          @Part("post_type") RequestBody post_type,
                          @Part("date") RequestBody date,
                           @Part("comment") RequestBody comment,
                           @Part("feeling") RequestBody feeling,

                           @Part("location") RequestBody loc,
                           @Part("c_category") RequestBody c_category,
                           @Part("title") RequestBody title,

                           @Part List<MultipartBody.Part> file);
    @Multipart
    @POST("API/api.php?addpost")
    Call<AddPost> AddPost(@Part("expired_on") RequestBody expired_on,
                          @Part("user_id") RequestBody userid,
                          @Part("user_picture") RequestBody picture,
                          @Part("user_name") RequestBody name,
                          @Part("user_firstname") RequestBody user_firstname,
                          @Part("user_lastname") RequestBody user_lastname,
                          @Part("user_verified") RequestBody user_verified,
                          @Part("category") RequestBody category,
                          @Part("message") RequestBody message,
                          @Part("privacy") RequestBody privacy,
                          @Part("post_type") RequestBody post_type,
                          @Part("comment") RequestBody comment,
                          @Part("feeling") RequestBody feeling,
                          @Part("date") RequestBody date,
                          @Part("c_category") RequestBody c_category,
                          @Part("title") RequestBody title,
                           @Part("location") RequestBody loc
                         );
    @Multipart
    @POST("API/api.php?addVideo")
    Call<AddPost> addVideo(@Part("origin_id") RequestBody origin_id,
            @Part("expired_on") RequestBody expired_on,
                           @Part("user_id") RequestBody userid,
                           @Part("user_picture") RequestBody picture,
                           @Part("user_name") RequestBody name,
                           @Part("user_firstname") RequestBody user_firstname,
                           @Part("user_lastname") RequestBody user_lastname,
                           @Part("user_verified") RequestBody user_verified,
                           @Part("category") RequestBody category,
                           @Part("message") RequestBody message,
                           @Part("privacy") RequestBody privacy,
                           @Part("post_type") RequestBody post_type,
                           @Part("date") RequestBody date,
                           @Part("comment") RequestBody comment,
                           @Part("feeling") RequestBody feeling,
                           @Part("location") RequestBody loc,
                           @Part("c_category") RequestBody c_category,
                           @Part("title") RequestBody title,
                           @Part List<MultipartBody.Part> file);
    @Multipart
    @POST("API/api.php?editprofile")
    Call<RegisterModel> EditProfile(@Part("user_id") RequestBody userid,
                           @Part("user_firstname") RequestBody user_firstname,
                           @Part("user_lastname") RequestBody user_lastname,
                           @Part("user_current_city") RequestBody city,
                           @Part("bio") RequestBody bio,
                           @Part("user_birthdate") RequestBody birthdate,
                           @Part("gender") RequestBody gender,
                           @Part MultipartBody.Part file);
    @Multipart
    @POST("API/api.php?updatecover")
    Call<RegisterModel> CoverUpdate(@Part("user_id") RequestBody userid,
                                    @Part MultipartBody.Part file);

    @POST("index.php/Api/VerifyOTP")
    Call<RegisterModel> VerifyOTP(@Body RequestBody requestBody);
   }
