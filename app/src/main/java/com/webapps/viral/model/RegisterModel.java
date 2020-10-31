package com.webapps.viral.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterModel {






        @SerializedName("error")
        @Expose
        private String error;
        @SerializedName("status")
        @Expose
        private Integer status;
    @SerializedName("otp")
    @Expose
    private Integer otp;
    @SerializedName("id")
    @Expose
    private Integer id;
        @SerializedName("message")
        @Expose
        private String message;

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }
    public Integer getOtp() {
        return otp;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }


}
