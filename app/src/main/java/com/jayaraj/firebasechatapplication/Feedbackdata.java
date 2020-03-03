package com.jayaraj.firebasechatapplication;

public class Feedbackdata {
    String id,name,email,mobile,feedback,rating;

    public Feedbackdata(){

    }

    public Feedbackdata(String id, String name, String email, String mobile, String feedback, String rating) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.feedback = feedback;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
