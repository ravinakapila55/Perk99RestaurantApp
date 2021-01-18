package com.app.perk99restaurant.home.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NearByDriverModel
{
//[{"id":24,"name":"new driver","image":null,"description":null,"ratings":null,"eta":0}]}

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("ratings")
    @Expose
    private String ratings;

    @SerializedName("eta")
    @Expose
    private String eta;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public String getEta() {
        return eta;
    }

    public void setEta(String eta) {
        this.eta = eta;
    }
}
