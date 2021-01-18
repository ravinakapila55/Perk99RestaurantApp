package com.app.perk99restaurant.reviews;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RatingMenusModel implements Serializable
{
    @SerializedName("menu_id")
    @Expose
    private String menu_id;

    public String getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(String menu_id) {
        this.menu_id = menu_id;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @SerializedName("rating")
    @Expose
    private String rating;
}
