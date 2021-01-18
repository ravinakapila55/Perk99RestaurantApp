package com.app.perk99restaurant.home.models;

import java.io.Serializable;
import java.util.ArrayList;

public class ModelAcceptedOrders implements Serializable
{
    String id,total,is_assigned,status,user_name,driver_id,driver_name,
  driver_phone,driver_email,driver_image,instructions,meal_type,order_type,type,pickup_time;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPickup_time() {
        return pickup_time;
    }

    public void setPickup_time(String pickup_time) {
        this.pickup_time = pickup_time;
    }

    public String getMeal_type() {
        return meal_type;
    }

    public void setMeal_type(String meal_type) {
        this.meal_type = meal_type;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getDriver_image() {
        return driver_image;
    }

    public void setDriver_image(String driver_image) {
        this.driver_image = driver_image;
    }

    public String getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(String driver_id) {
        this.driver_id = driver_id;
    }

    public String getDriver_name() {
        return driver_name;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }

    public String getDriver_phone() {
        return driver_phone;
    }

    public void setDriver_phone(String driver_phone) {
        this.driver_phone = driver_phone;
    }

    public String getDriver_email() {
        return driver_email;
    }

    public void setDriver_email(String driver_email) {
        this.driver_email = driver_email;
    }

    ArrayList<MenuItemsModel> list;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getIs_assigned() {
        return is_assigned;
    }

    public void setIs_assigned(String is_assigned) {
        this.is_assigned = is_assigned;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public ArrayList<MenuItemsModel> getList() {
        return list;
    }

    public void setList(ArrayList<MenuItemsModel> list) {
        this.list = list;
    }
}
