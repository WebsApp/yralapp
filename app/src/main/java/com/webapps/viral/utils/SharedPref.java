package com.webapps.viral.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.webapps.viral.R;


public class SharedPref {

    private Context ctx;
    private SharedPreferences default_prefence;

    public SharedPref(Context context) {
        this.ctx = context;
        default_prefence = PreferenceManager.getDefaultSharedPreferences(context);
    }

    private String str(int string_id) {
        return ctx.getString(string_id);
    }


public  void clear()
{
    SharedPreferences.Editor editor = default_prefence.edit();
    editor.clear();
    editor.apply();

}
    public void setPic(String pic) {
        default_prefence.edit().putString(str(R.string.pic), pic).apply();
    }
    public String getPic() {
        return default_prefence.getString(str(R.string.pic), str(R.string.pic));
    }
    public void setID(String id) {
        default_prefence.edit().putString(str(R.string.login_id), id).apply();
    }

    public String getID() {
        return default_prefence.getString(str(R.string.login_id), str(R.string.login_id));
    }
    public void setDate(String date) {
        default_prefence.edit().putString(str(R.string.date), date).apply();
    }

    public String getDate() {
        return default_prefence.getString(str(R.string.date), "");
    }
    public void setStatus(String status) {
        default_prefence.edit().putString(str(R.string.login_status), status).apply();
    }

    public String getStatus() {
        return default_prefence.getString(str(R.string.login_status), str(R.string.login_status));
    }
    public void setYourName(String name) {
        default_prefence.edit().putString(str(R.string.pref_title_name), name).apply();
        default_prefence.edit().commit();
    }

    public String getYourName() {
        return default_prefence.getString(str(R.string.pref_title_name), "");
    }

    public void setYourEmail(String name) {
        default_prefence.edit().putString(str(R.string.pref_title_email), name).apply();
    }

    public String getYourEmail() {
        return default_prefence.getString(str(R.string.pref_title_email), str(R.string.default_your_email));
    }

    public void setYourPhone(String name) {
        default_prefence.edit().putString(str(R.string.pref_title_phone), name).apply();
    }

    public String getYourPhone() {
        return default_prefence.getString(str(R.string.pref_title_phone), str(R.string.default_your_phone));
    }

    public void setYourAddress(String name) {
        default_prefence.edit().putString(str(R.string.pref_title_address), name).apply();
    }

    public String getYourAddress() {
        return default_prefence.getString(str(R.string.pref_title_address), str(R.string.default_your_address));
    }
    public void setGender(String gender) {
        default_prefence.edit().putString(str(R.string.pref_title_gender), gender).apply();
    }

    public String getGender() {
        return default_prefence.getString(str(R.string.pref_title_gender), str(R.string.pref_title_gender));
    }

}
