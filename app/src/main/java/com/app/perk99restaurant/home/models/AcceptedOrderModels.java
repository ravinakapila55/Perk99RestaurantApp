package com.app.perk99restaurant.home.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AcceptedOrderModels  {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("total")
    @Expose
    private String total;

    @SerializedName("is_assigned")
    @Expose
    private Integer is_assigned;



    @SerializedName("status")
    @Expose
    private String status;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    @SerializedName("user_name")
    @Expose
    private String user_name;

    public Integer getIs_assigned() {
        return is_assigned;
    }

    public void setIs_assigned(Integer is_assigned) {
        this.is_assigned = is_assigned;
    }

    @SerializedName("detail")
    @Expose

    private List<Detail> detail = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Detail> getDetail() {
        return detail;
    }

    public void setDetail(List<Detail> detail) {
        this.detail = detail;
    }

}

