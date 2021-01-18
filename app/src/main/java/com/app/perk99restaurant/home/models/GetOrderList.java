
package com.app.perk99restaurant.home.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetOrderList
{

    @SerializedName("id")
    @Expose
    private Integer id;

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    @SerializedName("total")
    @Expose
    private String total;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("order_date")
    @Expose
    private String order_date;

    @SerializedName("type")
    @Expose
    private String type;

    public String getDelivery_address() {
        return delivery_address;
    }

    public void setDelivery_address(String delivery_address) {
        this.delivery_address = delivery_address;
    }

    @SerializedName("delivery_address")
    @Expose
    private String delivery_address;

    @SerializedName("pickup_time")
    @Expose
    private String pickup_time;

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getPickup_time()
    {
        return pickup_time;
    }

    public void setPickup_time(String pickup_time)
    {
        this.pickup_time = pickup_time;
    }

    public String getMeal_type()
    {
        return meal_type;
    }

    public void setMeal_type(String meal_type)
    {
        this.meal_type = meal_type;
    }

    public String getOrder_type()
    {
        return order_type;
    }

    public void setOrder_type(String order_type)
    {
        this.order_type = order_type;
    }

    @SerializedName("meal_type")
    @Expose
    private String meal_type;

    @SerializedName("order_type")
    @Expose
    private String order_type;


   @SerializedName("instructions")
    @Expose
    private String instructions;


    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
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
