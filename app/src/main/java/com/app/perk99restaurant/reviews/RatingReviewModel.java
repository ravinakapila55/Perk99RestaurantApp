package com.app.perk99restaurant.reviews;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class RatingReviewModel implements Serializable {

    @SerializedName("order_id")
    @Expose
    private String order_id;

    public String getRating_id() {
        return rating_id;
    }

    public void setRating_id(String rating_id) {
        this.rating_id = rating_id;
    }

    @SerializedName("rating_id")
    @Expose
    private String rating_id;

    @SerializedName("user_message")
    @Expose
    private String user_message;

    @SerializedName("restaurant_msg")
    @Expose
    private String restaurant_msg;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getUser_message() {
        return user_message;
    }

    public void setUser_message(String user_message) {
        this.user_message = user_message;
    }

    public ArrayList<RatingMenusModel> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<RatingMenusModel> details) {
        this.details = details;
    }

    public String getRestaurant_msg() {
        return restaurant_msg;
    }

    public void setRestaurant_msg(String restaurant_msg) {
        this.restaurant_msg = restaurant_msg;
    }

    @SerializedName("details")
    @Expose
    private ArrayList<RatingMenusModel> details;
}
